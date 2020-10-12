
import java.util.Arrays;

/**
* A helper class for storing a word and its counts
*/
public class Word implements Comparable, Cloneable {
   private String word;
   private int counts;
   
   public Word(String word, int counts) {
       this.word = word;
       this.counts = counts;
   }
   
   public void setWord(String word)  {
       this.word = word;
   }
   
   @Override
   public Word clone() {
       return new Word(word, counts);
   }
   
   @Override
   public String toString() {
       return word;
   }

   @Override
   public int compareTo(Object otherObj) {
       Word other = (Word)otherObj;
       // This will sort in descending order
       return other.counts-this.counts;
       /*int toReturn = this.word.compareTo(other.word); // Alphabetical order
       //return this.compareTo(other); // BAD: Infinite recursion
       if (toReturn == 0) {
            // Break the tie so that if they're the same word,
            // the one with most counts appear first
            toReturn = other.counts - this.counts;
       }
       return toReturn;*/
   }
   
   public static void main(String[] args) {
       Word[] words = new Word[4];
       words[0] = new Word("booyah!", 30);
       words[1] = new Word("alakazam", 80);
       words[2] = new Word("cloning", 50);
       words[3] = new Word("cloning", 60);
       Arrays.sort(words);
       for (int i = 0; i < words.length; i++) {
           System.out.println(words[i]);
       }
   }
}