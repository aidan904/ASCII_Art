package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Main {

    private static final String ASCIIGrayScale = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);

	// load image image file that will be converted to ASCII
	BufferedImage img = loadImage(scanner);

	// set proper dimensions of image
	img = setDimensions(img, scanner);

	scanner.close();
    }

    /**
     * @param scanner - user input
     * @return the image that has been opened
     */
    private static BufferedImage loadImage(Scanner scanner) {
	System.out.println("Please input a file name.");
	String fileName = scanner.nextLine();
	BufferedImage img = null;
	try {
	    img = ImageIO.read(new File(fileName));
	    System.out.println("Image loaded");
	    return img;
	} catch (IOException e) {
	    System.out.println("Lol fuck you didnt work");
	    return null;
	}
    }

    /**
     * @param img     - the image that will have dimensions changed
     * @param scanner - user input
     * @return the image with new dimensions
     */
    private static BufferedImage setDimensions(BufferedImage img, Scanner scanner) {
	// Get width and dimension of original image
	int oldWidth = img.getWidth();
	int oldHeight = img.getHeight();
	int newWidth = 0;

	// Get the desired width of the ASCII image
	do {
	    System.out.println("Please input the desired width of the ASCII image.");
	    try {
		newWidth = scanner.nextInt();
		if (newWidth > oldWidth && newWidth > 0) {
		    System.out.println("Invalid width");
		}
	    } catch (InputMismatchException e) {
		System.out.println("Lol fuck you didnt work");
	    }
	} while (newWidth > oldWidth && newWidth > 0);

	// Calculate the new height from the given width
	double scaleFactor = newWidth / oldWidth;
	int newHeight = (int) (oldHeight * scaleFactor);

	return img;
    }

}
