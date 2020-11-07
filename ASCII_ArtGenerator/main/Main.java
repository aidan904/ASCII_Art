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
	System.out.println("Please input a file name.");
	String fileName = scanner.nextLine();
	BufferedImage img = null;
	try {
	    img = ImageIO.read(new File(fileName));
	    System.out.println("Image loaded");
	} catch (IOException e) {
	    System.out.println("Lol fuck you didnt work");
	}
	
	// Get width and dimension of original image
	int oldWidth = img.getWidth();
	int oldHeight = img.getHeight();
	
	// Get the desired width of the ASCII image
	System.out.println("Please input the desired width of the ASCII image.");
	int newWidth = 0; 
	try {
	    newWidth = scanner.nextInt();
	}
	catch(InputMismatchException e) {
	    System.out.println("Lol fuck you didnt work");
	}
	
	// Check for valid size
	if (newWidth > oldWidth && newWidth > 0) {
	    System.out.println("Invalid width");
	}
	
	// Calculate the new height from the given width
	double scaleFactor = newWidth / oldWidth;
	int newHeight = (int)(oldHeight * scaleFactor);
	
	
	

	scanner.close();
    }

}
