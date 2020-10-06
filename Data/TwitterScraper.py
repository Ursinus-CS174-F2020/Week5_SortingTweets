import re
import numpy as np
import matplotlib.pyplot as plt
from twython import Twython
import json
import os
import time

SPONGEBOB_ID = 17088779

keys = ['created_at', 'full_text', 'retweet_count', 'favorite_count', 'lang', 'id']

def scrubText(s):
    chars = {"\n":" ", "&amp;":"and"}
    text = s
    for c in chars:
        text = text.replace(c, chars[c])
    return text

def removeURLs(s):
    urls = re.findall(r'https?://t.co/.{9,10}', s)
    ret = s
    for url in urls:
        ret = ret.replace(url, "")
    return ret

def getTwythonObj():
    fin = open("keys.txt")
    lines = [l.rstrip() for l in fin.readlines()]
    fin.close()    
    [CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET] = lines   
    api = Twython(app_key = CONSUMER_KEY, app_secret = CONSUMER_KEY_SECRET, oauth_token = ACCESS_TOKEN, oauth_token_secret = ACCESS_TOKEN_SECRET)
    return api


def load_tweets():
    tweets = []
    if os.path.exists("latesttweets.json"):
        tweets = json.load(open("latesttweets.json", "r"))
        tweets = [{key:tweet[key] for key in keys} for tweet in tweets]

    api = getTwythonObj()
    while len(tweets) < 10000:
        print(len(tweets))
        max_id = tweets[-1]['id']
        new_tweets = api.get_user_timeline(user_id = SPONGEBOB_ID, count=200, max_id=max_id, tweet_mode='extended', trim_user=True, exclude_replies=True, include_rts=False)
        new_tweets = [{key:tweet[key] for key in keys} for tweet in new_tweets]
        tweets = tweets + new_tweets
        json.dump(tweets, open("tweets.json", "w"))
        time.sleep(np.random.rand()*10)


MONTHS = {'Jan':'01', 'Feb':'02', 'Mar':'03', 'Apr':'04', 'May':'05', 'Jun':'06', 'Jul':'07', 'Aug':'08', 'Sep':'09', 'Oct':'10', 'Nov':'11', 'Dec':'12'}


def make_tweet_text():
    fout = open("tweets.txt", "w")
    tweets = json.load(open("latesttweets.json", "r"))
    tweets = tweets[0:2123]
    for idx in np.random.permutation(len(tweets)):
        tweet = tweets[idx]
        date = tweet['created_at']
        fields = date.split()
        time = fields[3]
        YYYYMMDD = "%s/%s/%.2i"%(fields[-1], MONTHS[fields[1]], int(fields[2]))
        text = scrubText(tweet['full_text'])
        text = removeURLs(text)
        fout.write("%s\n"%YYYYMMDD)
        fout.write("%s\n"%time)
        fout.write("%s\n"%text)
        fout.write("%i\n"%tweet['retweet_count'])
        fout.write("%i\n"%tweet['favorite_count'])
    fout.close()

#load_tweets()
make_tweet_text()
