package org.utkuozdemir;

import org.utkuozdemir.engine.FloodFillDifferenceFinder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
	public static void main(String[] args) {
		try {
			if (args.length < 2 || args.length > 3) {
				System.out.println("Invalid number of arguments. Usage:");
				System.out.println("  java -jar compare.jar <imagepath1> <imagepath2> <resultfilename (optional)>");
				return;
			}

			InputStream image1stream = Files.newInputStream(Paths.get(args[0]));
			InputStream image2stream = Files.newInputStream(Paths.get(args[1]));

			BufferedImage originalImage = ImageIO.read(image1stream);
			BufferedImage differenceImage = ImageIO.read(image2stream);

			BufferedImage resultImage = new FloodFillDifferenceFinder(originalImage, differenceImage).getResultImage();
			String resultFileName = args.length == 3 ? args[2] : "result.png";
			ImageIO.write(resultImage, "png", new File(resultFileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	private static String getExtension(String fileName) {
//		String extension = "";
//		int i = fileName.lastIndexOf('.');
//		if (i > 0) {
//			extension = fileName.substring(i + 1);
//		}
//		return extension;
//	}
}
