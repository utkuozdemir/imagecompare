package org.utkuozdemir;

import org.junit.Test;
import org.utkuozdemir.engine.FloodFillDifferenceFinder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageSavingTest {
	@Test
	public void testImageSaving1() throws IOException {
		InputStream image1stream = App.class.getClassLoader().getResourceAsStream("image1.png");
		InputStream image2stream = App.class.getClassLoader().getResourceAsStream("image2.png");

		BufferedImage originalImage = ImageIO.read(image1stream);
		BufferedImage differenceImage = ImageIO.read(image2stream);

		BufferedImage resultImage = new FloodFillDifferenceFinder(originalImage, differenceImage).getResultImage();
		ImageIO.write(resultImage, "png", new File("result_1-2.png"));
	}

	@Test
	public void testImageSaving2() throws IOException {
		InputStream image1stream = App.class.getClassLoader().getResourceAsStream("image1.png");
		InputStream image2stream = App.class.getClassLoader().getResourceAsStream("image3.png");

		BufferedImage originalImage = ImageIO.read(image1stream);
		BufferedImage differenceImage = ImageIO.read(image2stream);

		BufferedImage resultImage = new FloodFillDifferenceFinder(originalImage, differenceImage).getResultImage();
		ImageIO.write(resultImage, "png", new File("result_1-3.png"));
	}

	@Test
	public void testImageSaving3() throws IOException {
		InputStream image1stream = App.class.getClassLoader().getResourceAsStream("image1.png");
		InputStream image2stream = App.class.getClassLoader().getResourceAsStream("image4.png");

		BufferedImage originalImage = ImageIO.read(image1stream);
		BufferedImage differenceImage = ImageIO.read(image2stream);

		BufferedImage resultImage = new FloodFillDifferenceFinder(originalImage, differenceImage).getResultImage();
		ImageIO.write(resultImage, "png", new File("result_1-4.png"));
	}

	@Test
	public void testImageSaving4() throws IOException {
		InputStream image1stream = App.class.getClassLoader().getResourceAsStream("image1.png");
		InputStream image2stream = App.class.getClassLoader().getResourceAsStream("image5.png");

		BufferedImage originalImage = ImageIO.read(image1stream);
		BufferedImage differenceImage = ImageIO.read(image2stream);

		BufferedImage resultImage = new FloodFillDifferenceFinder(originalImage, differenceImage).getResultImage();
		ImageIO.write(resultImage, "png", new File("result_1-5.png"));
	}
}
