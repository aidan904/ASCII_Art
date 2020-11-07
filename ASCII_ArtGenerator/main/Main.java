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
 * @author Aidan Conley (2020)
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

	    float[][] lumArray = getLumArray(img);

	    String ASCIIImage = getASCIIImage(lumArray);

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
    private static float[][] getLumArray(BufferedImage img) {
	// Create local variables
	int width = img.getWidth();
	int height = img.getHeight();

	// Create the 2D array
	float[][] lumArray = new float[height][width];

	// Iterate through every pixel in the image and calculate its luminance value
	for (int col = 0; col < width; col++) {
	    for (int row = 0; row < height; row++) {
		// Get this pixel's RGB value
		Color color = new Color(img.getRGB(col, row));

		// Get individual red, green, and blue values
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();

		// Calculate luminance and add it to the array
		lumArray[row][col] = (float) ((red * 0.299 + green * 0.587 + blue * 0.114) / 255);
	    }
	}

	return lumArray;
    }

    /**
     * @param lumArray - the array of luminance for the colors
     * @return a string composing the ASCII image
     */
    private static String getASCIIImage(float[][] lumArray) {
	final String ASCIIGrayScale = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

	String ASCIIImage = new String();
	int width = lumArray.length;
	int height = lumArray[0].length;

	for (int col = 0; col < width; col++) {
	    for (int row = 0; row < height; row++) {
		ASCIIImage += ASCIIGrayScale.charAt((int) (lumArray[col][row] * 69));
	    }
	    ASCIIImage += '\n';
	}

	return ASCIIImage;
    }
}
