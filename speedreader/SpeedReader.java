package speedreader;

import java.awt.Font;
import java.awt.Graphics;
import java.io.FileNotFoundException;

public class SpeedReader {	
	
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
			Font myFont = new Font("Courier", Font.BOLD, fontSize);
			
			drawStaggered(panel, wordGen, myFont, wpm);
		} catch(IndexOutOfBoundsException e) {
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
		Graphics graphics = panel.getGraphics();
		graphics.setFont(font);
		while(wordGen.hasNext()) {
			graphics.drawString(wordGen.next(), panel.getWidth() / 3, panel.getHeight() / 2);
			Thread.sleep(wpm);
			panel.clear();
		}
	}
	
	/**
	 * Prints help message about how to use this SpeedReader program
	 */
	private static void printHelp() {
		System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
	}
}
