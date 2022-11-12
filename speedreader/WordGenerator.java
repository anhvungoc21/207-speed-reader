package speedreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	 * Private field for keeping track of current index in `words` List
	 */
	private int curIndex;
	
	/**
	 * Private field for storing words read in by the constructor
	 */
	private List<String> words;
	
	/**
	 * Constructor for a WordGenerator object
	 * @param filename Name of the file to read words from
	 * @throws FileNotFoundException Thrown when a file named `filename` is not found
	 */
	public WordGenerator(String filename) throws FileNotFoundException {
		this.wordCount = 0;
		this.sentenceCount = 0;
		this.curIndex = 0;
		this.words = new ArrayList<>();
		
		Scanner fscan = new Scanner(new File(filename));
		while (fscan.hasNext()) {
			String word = fscan.next();

			// See if sentence contains sentence periods
			if (isEndingWord(word)) {
				word = word.substring(0, word.length() - 1);
				sentenceCount++;
			}
			
			this.words.add(word);
			wordCount++;
		}
		
		fscan.close();
	}
	
	/**
	 * Checks if there is text left to process
	 * @return a boolean value of whether this WordGenerator has text left to process
	 */
	public boolean hasNext() {
		return this.curIndex < this.words.size();
	}
	
	/**
	 * Gets the next word in this WordGenerator
	 * @return A String of the next word in this WordGenerator
	 */
	public String next() {
		return this.words.get(curIndex++);
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
	private boolean isEndingWord(String word) {
		char end = word.charAt(word.length() - 1);
		if (end == '.' || end == '!' || end == '?') {
			return true;
		}
		return false;
	}
	
	// TESTING
	public static void main (String[] args) {
		try {
			WordGenerator wg = new WordGenerator("test.txt");
			System.out.println(wg.getWordCount());
			System.out.println(wg.getSentenceCount());
			
			while (wg.hasNext()) {
				System.out.println(wg.next());
			}
	 	} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}
}
