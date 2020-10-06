/**
* A helper class for storing a word and its counts
*/
public class Word implements Comparable {
   private final String word;
   private final int counts;
   
   public Word(String word, int counts) {
       this.word = word;
       this.counts = counts;
   }
   
   @Override
   public String toString() {
       return word + ": " + counts;
   }

   @Override
   public int compareTo(Object otherObj) {
       Word other = (Word)otherObj;
       return other.counts-counts;
   }
}