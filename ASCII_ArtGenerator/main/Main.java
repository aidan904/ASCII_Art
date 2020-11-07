package main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Main {

    private static final String ASCIIGrayScale = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	String fileName = scanner.nextLine();
	Image img;
	try {
	    img = ImageIO.read(new File(fileName));
	    System.out.println("Image loaded");
	} catch (IOException e) {
	    System.out.println("Lol fuck you didnt work");
	}
    }

}
