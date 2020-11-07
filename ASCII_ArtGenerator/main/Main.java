package main;

import java.awt.Graphics2D;
import java.awt.Image;
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
	BufferedImage img;
	try {
	    img = loadImage(scanner);

	    // set proper dimensions of image
	    try {
		img = setDimensions(img, scanner);
	    } catch (InputMismatchException e) {
		e.printStackTrace();
	    }

	    // save image
	    try {
		// retrieve image
		BufferedImage bi = img;
		File outputfile = new File("saved.png");
		ImageIO.write(bi, "png", outputfile);
	    } catch (IOException e) {
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
	    newWidth = scanner.nextInt();
	    System.out.println("Please input the desired width of the ASCII image.");
	    newWidth = scanner.nextInt();
	    if (newWidth > oldWidth && newWidth > 0) {
		System.out.println("Invalid width");
	    }
	} while (newWidth > oldWidth && newWidth > 0);

	// Calculate the new height from the given width
	double scaleFactor = (double) newWidth / oldWidth;
	int newHeight = (int) (oldHeight * scaleFactor);
	System.out.println(newHeight + " " + newWidth);

	// create new resized image
	Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = newImage.createGraphics();
	g2d.drawImage(tmp, 0, 0, newWidth, newHeight, null);
	g2d.dispose();

	return newImage;
    }

}
