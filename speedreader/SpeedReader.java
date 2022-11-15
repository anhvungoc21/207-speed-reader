package speedreader;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.FileNotFoundException;

/*
 * Name of author: Anh Vu
 * Assignment name: Assignment 5 - Speed Reader
 * Assignment due date: November 14, 2022
 * Written/online sources used: https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
 * Help obtained: None
 * I confirm that the above list of sources is complete AND that I have not talked to anyone else 
 * (e.g., CSC 207 students) about the solution to this problem.
 */

/**
 * The SpeedReader class functions as a speed reader, a program that allows users
 * to read texts quickly at different speeds.
 */
public class SpeedReader {	
	/**
	 * Constants for the time conversion and fonts used in every SpeedReader
	 */
	private static final int msPerMin = 60000;
	private static final int defaultFontStyle = Font.BOLD;
	private static final String defaultFontName = "Courier";

	/**
	 * Main function to run a SpeedReader
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		try {
			String filename = args[0];
			int width = Integer.parseInt(args[1]);
			int height = Integer.parseInt(args[2]);
			int fontSize = Integer.parseInt(args[3]);
			int wpm = Integer.parseInt(args[4]);

			DrawingPanel panel = new DrawingPanel(width, height);
			WordGenerator wordGen = new WordGenerator(filename);
			Font myFont = new Font(defaultFontName, defaultFontStyle, fontSize);

			drawStaggered(panel, wordGen, myFont, wpm);
		} catch(IndexOutOfBoundsException | NumberFormatException e) {
			System.out.println("Invalid program arguments!");
			printHelp();
		} catch(FileNotFoundException e) {
			System.out.println("File not found!");
		} catch(InterruptedException e) {
			System.out.println("Program interrupted!");
		} 
	}

	/**
	 * Draws words from `wordGen` onto `panel` with font `font` at a frequency `wpm`
	 * @param panel A DrawingPanel to draw on
	 * @param wordGen A WordGenerator to generate words
	 * @param font A Font that specifies the font of the words to be drawn
	 * @param wpm Integer representing words per minute
	 * @throws InterruptedException 
	 */
	public static void drawStaggered(DrawingPanel panel, WordGenerator wordGen, Font font, int wpm) throws InterruptedException {
		// Get graphics, font, and font metrics
		Graphics graphics = panel.getGraphics();
		graphics.setFont(font);
		FontMetrics fontMetrics = graphics.getFontMetrics(font);

		// Set coordinate information. Avoid repeated computations in while loop
		int panelWidth = panel.getWidth();
		int yCoord = getCenterCoord(panel.getHeight(), fontMetrics.getHeight());

		while (wordGen.hasNext()) {
			// Get next word and x-coordinate based on string width
			String word = wordGen.next();
			int xCoord = getCenterCoord(panelWidth, fontMetrics.stringWidth(word));

			// Draw string, sleep, and clear
			graphics.drawString(word, xCoord, yCoord);
			Thread.sleep(msPerMin / wpm);
			panel.clear();
		}

		// Draw final report of word and sentence count
		String reportStr = String.format("You've finished reading %d words and %d sentences!", 
										wordGen.getWordCount(), 
										wordGen.getSentenceCount());
		int xCoordReport = getCenterCoord(panelWidth, fontMetrics.stringWidth(reportStr));
		graphics.drawString(reportStr, xCoordReport, yCoord);
	}

	/**
	 * Get the center coordinate of a dimension (width/height) based the dimension of the element
	 * @param panelDim Dimension of a DrawingPanel
	 * @param elementDim Dimension of an element to be drawn
	 * @return Integer of the coordinate to center an element on a DrawingPanel
	 */
	private static int getCenterCoord(int panelDim, int elementDim) {
		return (panelDim / 2) - (elementDim / 2);
	}


	/**
	 * Prints help message about how to use this SpeedReader program
	 */
	private static void printHelp() {
		System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
	}
}
