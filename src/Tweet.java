import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Tweet implements Comparable {
    private final String date;
    private final String time;
    private final String text;
    private final int retweets;
    private final int likes;
    
    /**
     * 
     * @param date A YYYY/MM/DD format of the date
     * @param time The time in HH:MM:SS format
     * @param text The text of the tweet
     * @param retweets How many retweets the tweet has
     * @param likes How many likes the tweet has
     */
    public Tweet(String date, String time, String text, int retweets, int likes) {
        this.date = date;
        this.time = time;
        this.text = text;
        this.retweets = retweets;
        this.likes = likes;
    }
    
    @Override
    public String toString() {
        String ret = date + ", " + time + "\n";
        ret += "retweets: " + retweets + "\n";
        ret += "likes: " + likes + "\n";
        ret += text;
        return ret;
    }

    
    
    
    
    ////////////////////////////////////////////////////
    //    STATIC METHODS FOR TWEET COLLECTIONS        //
    ////////////////////////////////////////////////////
    /**
     * Load in all of the Nintendo America tweets from the
     * text file provided with the assignment
     * @return An array of all tweets
     */
    public static Tweet[] getAllTweets() {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        File file = new File("Data/tweets.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String date = br.readLine();
            while (date != null) {
                String time = br.readLine();
                String text = br.readLine();
                int retweets = Integer.parseInt(br.readLine());
                int likes = Integer.parseInt(br.readLine());
                Tweet tweet = new Tweet(date, time, text, retweets, likes);
                tweets.add(tweet);
                date = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Tweet[] ret = new Tweet[tweets.size()];
        for (int i = 0; i < tweets.size(); i++) {
            ret[i] = tweets.get(i);
        }
        return ret;
    }
    
    /**
     * Print out the 200 words that are used the most
     * @param tweets An array of tweets
     */
    public static void printMostUsedWords(Tweet[] tweets) {
        HashMap<String, Integer> wordCounts = new HashMap();
        for (int i = 0; i < tweets.length; i++) {
            // Split each tweet into its individual words, separated by a space
            String text = tweets[i].text;
            String[] words = text.split(" ");
            for (int j = 0; j < words.length; j++) {
                // For each word, put it in lowercase and update its
                // count in the HashMap
                String word = words[j];
                word = word.toLowerCase();
                if (word.length() > 0) {
                    if (!wordCounts.containsKey(word)) {
                        wordCounts.put(word, 0);
                    }
                    wordCounts.put(word, wordCounts.get(word)+1);
                }
            }
        }
        // Pull out all of the words, and copy each over with its
        // counts to a word object.  Put all of the word objects in an
        // array and sort them by their count using the comparator in Word
        ArrayList<String> allWords = new ArrayList<>(wordCounts.keySet());
        Word[] words = new Word[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            String word = allWords.get(i);
            words[i] = new Word(word, wordCounts.get(word));
        }
        Arrays.sort(words);
        for (int i = 0; i < 50; i++) {
            System.out.println(words[i]);
        }
    }
    
    public static void main(String[] args) {
        Tweet[] tweets = Tweet.getAllTweets();
        //printMostUsedWords(tweets);
        Arrays.sort(tweets);
        for (int i = 0; i < 10; i++) {
            int[] arr = tweets[i].getDateComponents();
            System.out.println(tweets[i]);
        }
    }
    
    

    
    public int compareTo(Object otherObj) {
        Tweet other = (Tweet)otherObj;
        // date, YYYY/MM/DD
        // time: HH:MM:SS
        // Ex) 2020/01/03
        //     2020/11/01
        int toReturn = date.compareTo(other.date);
        if (toReturn == 0) {
            toReturn = time.compareTo(other.time);
        }
        return toReturn;
    }
    
    
    public int[] getDateComponents() {
        int[] dateParts = new int[6];
        String[] stringParts = date.split("/");
        // Year/Month/Day
        for (int i = 0; i < 3; i++) {
            dateParts[i] = Integer.parseInt(stringParts[i]);
        }
        // Hour:Minute:Second
        stringParts = time.split(":");
        for (int i = 0; i < 3; i++) {
            dateParts[i+3] = Integer.parseInt(stringParts[i]);
        }
        return dateParts;
    }
    
    public int compareToDateParts(Object otherObj) {
        Tweet other = (Tweet)otherObj;
        // Each tweet has date string and time string
        int[] d1 = this.getDateComponents();
        int[] d2 = other.getDateComponents();
        int toReturn = 0;
        boolean brokenTie = false;
        for (int i = 0; i < d1.length && !brokenTie; i++) {
            int diff = d1[i] - d2[i];
            if (Math.abs(diff) != 0) {
                toReturn = diff;
                brokenTie = true;
            }
        }
        return toReturn;
    }
    
    public int compareToRetweets(Object otherObj) {
        Tweet other = (Tweet)otherObj;
        return other.retweets - this.retweets;
    }
    
    

    /**
     * Demo of the split method
     */
    public static void splitTest() {
        String s = "Hello everyone, and congrats to Brenden on his job";
        String[] components = s.split(" ");
        for (int i = 0; i < components.length; i++) {
            System.out.println(components[i]);
        }
    }
}
