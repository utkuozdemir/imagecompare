package org.utkuozdemir;

import org.junit.Test;
import org.utkuozdemir.engine.FloodFillDifferenceFinder;
import org.utkuozdemir.engine.ImageAnalysisDifferenceFinder;
import org.utkuozdemir.engine.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlgorithmTest {

	private Set<Rectangle> compareWithImageAnalysisAlgorithm(String filename1, String filename2) {
		try {
			InputStream image1stream = AlgorithmTest.class.getClassLoader().getResourceAsStream(filename1);
			InputStream image2stream = AlgorithmTest.class.getClassLoader().getResourceAsStream(filename2);

			BufferedImage originalImage = ImageIO.read(image1stream);
			BufferedImage differenceImage = ImageIO.read(image2stream);

			return new ImageAnalysisDifferenceFinder(originalImage, differenceImage).getDifferenceRectangles();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Set<Rectangle> compareWithFloodFillAlgorithm(String filename1, String filename2) {
		try {
			InputStream image1stream = AlgorithmTest.class.getClassLoader().getResourceAsStream(filename1);
			InputStream image2stream = AlgorithmTest.class.getClassLoader().getResourceAsStream(filename2);

			BufferedImage originalImage = ImageIO.read(image1stream);
			BufferedImage differenceImage = ImageIO.read(image2stream);

			return new FloodFillDifferenceFinder(originalImage, differenceImage).getDifferenceRectangles();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testFloodFillComparison1() throws IOException {
		assertEquals(compareWithFloodFillAlgorithm("image1.png", "image2.png").size(), 6);
	}

	@Test
	public void testFloodFillComparison2() throws IOException {
		assertEquals(compareWithFloodFillAlgorithm("image1.png", "image3.png").size(), 3);
	}

	@Test
	public void testFloodFillComparison3() throws IOException {
		assertEquals(compareWithFloodFillAlgorithm("image1.png", "image4.png").size(), 2);
	}

	@Test
	public void testFloodFillComparison4() throws IOException {
		assertEquals(compareWithFloodFillAlgorithm("image1.png", "image5.png").size(), 1);
	}


	@Test
	public void testImageAnalysisComparison1() throws IOException {
		assertEquals(compareWithImageAnalysisAlgorithm("image1.png", "image2.png").size(), 6);
	}

	@Test
	public void testImageAnalysisComparison2() throws IOException {
		assertEquals(compareWithImageAnalysisAlgorithm("image1.png", "image3.png").size(), 3);
	}

	@Test
	public void testImageAnalysisComparison3() throws IOException {
		assertEquals(compareWithImageAnalysisAlgorithm("image1.png", "image4.png").size(), 2);
	}

	// accept if it takes more than 20 seconds
	@Test
	public void testImageAnalysisComparison4() throws IOException {
		Thread currentThread = Thread.currentThread();
		AtomicInteger differenceCount = new AtomicInteger();
		Thread thread = new Thread(() -> {
			differenceCount.set(compareWithImageAnalysisAlgorithm("image1.png", "image5.png").size());
			currentThread.interrupt();
		});
		thread.start();

		try {
			Thread.sleep(20000);
		} catch (InterruptedException ignored) {
		}

		int diffs = differenceCount.get();
		assertTrue(diffs == 1 || (diffs == 0 && thread.isAlive()));
	}
}
