package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Main - TODO Describe purpose of this user-defined type
 * 
 * @author Aidan and Matthew (2020)
 *
 */
public class Main {

    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);

	// load image image file that will be converted to ASCII
	BufferedImage img;
	try {
	    img = loadImage(scanner);

	    // set proper dimensions of image
	    try {
		img = setDimensions(img, scanner);
	    } catch (InputMismatchException e) {
		e.printStackTrace();
	    }

	    String ASCIIImage = getASCIIImage(img);

	    try {
		FileWriter myWriter = new FileWriter("filename.txt");
		myWriter.write(ASCIIImage);
		myWriter.close();
		System.out.println("File created");
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}

	scanner.close();
    }

    /**
     * @param scanner - user input
     * @return the image that has been opened
     */
    private static BufferedImage loadImage(Scanner scanner) throws IOException {
	System.out.println("Please input a file name.");
	String fileName = scanner.nextLine();
	BufferedImage img = null;
	img = ImageIO.read(new File(fileName));
	if (img == null) {
	    throw new IOException();
	}
	System.out.println("Image loaded");
	return img;
    }

    /**
     * @param img     - the image that will have dimensions changed
     * @param scanner - user input
     * @return the image with new dimensions
     */
    private static BufferedImage setDimensions(BufferedImage img, Scanner scanner) throws InputMismatchException {
	// Get width and dimension of original image
	int oldWidth = img.getWidth();
	int oldHeight = img.getHeight();
	int newWidth = 0;

	// Get the desired width of the ASCII image
	do {
	    System.out.println("Please input the desired width of the ASCII image.");
	    newWidth = scanner.nextInt();
	    if (newWidth > oldWidth && newWidth > 0) {
		System.out.println("Invalid width");
	    }
	} while (newWidth > oldWidth && newWidth > 0);

	// Calculate the new height from the given width
	double scaleFactor = (double) newWidth / oldWidth / 2;
	int newHeight = (int) (oldHeight * scaleFactor);

	// create new resized image
	Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = newImage.createGraphics();
	g2d.drawImage(tmp, 0, 0, newWidth, newHeight, null);
	g2d.dispose();

	return newImage;
    }

    /**
     * @param img
     * @return a 2D array of the image's luminance values
     */
    private static String getASCIIImage(BufferedImage img) {
	final String ASCIIGrayScale = "@%#*+=-:. ";
	
	// Create local variables
	int width = img.getWidth();
	int height = img.getHeight();
	
	// Create the 2D array
	float[][] lumArray = new float[height][width];
	
	// Keep track of the maximum and minimum luminance values
	float maxLum = -1;
	float minLum = 256;

	// Iterate through every pixel in the image and calculate its luminance value
	for (int row = 0; row < height; row++) {
	    for (int col = 0; col < width; col++) {
		// Get this pixel's RGB value
		Color color = new Color(img.getRGB(col, row));

		// Get individual red, green, and blue values
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();

		// Calculate luminance and add it to the array
		float curLum = (float) ((red * 0.299 + green * 0.587 + blue * 0.114) / 255);
		
		// Update maximum and minimum values
		if(curLum > maxLum) maxLum = curLum;
		if(curLum < minLum) minLum = curLum;
		
		lumArray[row][col] = curLum;
	    }
	}
	
	// generates the ascii image based on the gray scale
	String ASCIIImage = new String();
	float rangeOfLum = maxLum - minLum;
	for (int row = 0; row < height; row++) {
	    for (int col = 0; col < width; col++) {
		float curLum = lumArray[row][col] - minLum;
		curLum = curLum / rangeOfLum;
		ASCIIImage += ASCIIGrayScale.charAt((int)(curLum * (ASCIIGrayScale.length() - 1)));
	    }
	    ASCIIImage += '\n';
	}

	return ASCIIImage;
    }
}
