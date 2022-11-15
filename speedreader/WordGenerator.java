package speedreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The WordGenerator class acts as a wrapper for a Scanner object 
 * of a text file to read from. An instance of this class is used
 * in a SpeedReader to parse text from a text file.
 */
public class WordGenerator {

	/**
	 * Private field for keeping track of total number of words
	 */
	private int wordCount;
	
	/**
	 * Private field for keeping track of total number of sentences
	 */
	private int sentenceCount;
	
	/**
	 * Private field for keeping track of the inner scanner that this
	 * WordGenerator wraps around
	 */
	private Scanner wordsScanner;
	
	/**
	 * Constructor for a WordGenerator object
	 * @param filename Name of the file to read words from
	 * @throws FileNotFoundException Thrown when a file named `filename` is not found
	 */
	public WordGenerator(String filename) throws FileNotFoundException {
		this.wordCount = 0;
		this.sentenceCount = 0;		
		this.wordsScanner = new Scanner(new File(filename));
	}
	
	/**
	 * Checks if there is text left to process
	 * @return a boolean value of whether this WordGenerator has text left to process
	 */
	public boolean hasNext() {
		if (this.wordsScanner.hasNext()) {
			return true;
		} else {
			// Close scanner upon finding no words left
			this.wordsScanner.close();
			return false;
		}
	}
	
	/**
	 * Gets the next word in this WordGenerator
	 * @return A String of the next word in this WordGenerator
	 */
	public String next() {
		String word = this.wordsScanner.next();
		
		// If word is sentence-ending, add to sentence count
		if (isEndingWord(word)) this.sentenceCount++;	
		
		// Regardless, add to word count and return word
		this.wordCount++;
		return word;
	}
	
	/**
	 * Gets the number of words in this WordGenerator
	 * @return An integer value of the number of words in this WordGenerator
	 */
	public int getWordCount() {
		return this.wordCount;
	}
	
	/**
	 * Gets the number of sentences in this WordGenerator.
	 * This is defined by the number of sentence-ending punctuation marks found so far.
	 * @return An integer value of the number of sentences in this WordGenerator
	 */
	public int getSentenceCount() {
		return this.sentenceCount;
	}
	
	/**
	 * Checks whether a word is an ending of a sentence,
	 * which means having '.', '!', or '?' at the end of the word.
	 * Private helper function for the constructor.
	 * @param word The word to be checked
	 * @return Boolean value of whether a word ends a sentence
	 */
	private static boolean isEndingWord(String word) {
		char end = word.charAt(word.length() - 1);
		if (end == '.' || end == '!' || end == '?') {
			return true;
		}
		return false;
	}
}
