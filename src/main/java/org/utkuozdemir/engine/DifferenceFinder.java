package org.utkuozdemir.engine;

import org.utkuozdemir.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public abstract class DifferenceFinder {
	protected final BufferedImage originalImage;
	protected final BufferedImage differenceImage;

	public DifferenceFinder(BufferedImage originalImage, BufferedImage differenceImage) {
		this.originalImage = originalImage;
		this.differenceImage = differenceImage;

		if (originalImage.getWidth() != differenceImage.getWidth() ||
				originalImage.getHeight() != differenceImage.getHeight()) {
			throw new NotSameSizeException("Two images has different sizes! Cannot compare these two images!");
		}
	}

	protected abstract Map<Integer, Set<org.utkuozdemir.engine.point.Point>> groupPoints();

	@SuppressWarnings("unused")
	public BufferedImage getResultImage() {
		long start = System.currentTimeMillis();
		BufferedImage image = new BufferedImage(originalImage.getWidth(),
				originalImage.getHeight(), originalImage.getType());

		for (int i = 0; i < originalImage.getWidth(); i++) {
			for (int j = 0; j < originalImage.getHeight(); j++) {
				image.setRGB(i, j, differenceImage.getRGB(i, j));
			}
		}

		Set<Rectangle> rectangles = getDifferenceRectangles();

		for (Rectangle area : rectangles) {
			for (org.utkuozdemir.engine.point.Point point : area.getPixelXYs()) {
				image.setRGB(point.getX(), point.getY(), Color.RED.getRGB());
			}
		}

		long end = System.currentTimeMillis();
//		System.out.println("Took " + (end - start) + " milliseconds...");
		return image;
	}

	public Set<Rectangle> getDifferenceRectangles() {
		Set<Rectangle> rectangles = new HashSet<>();
		for (Map.Entry<Integer, Set<org.utkuozdemir.engine.point.Point>> entry : groupPoints().entrySet()) {
			int minX = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			int minY = Integer.MAX_VALUE;
			int maxY = Integer.MIN_VALUE;

			for (org.utkuozdemir.engine.point.Point point : entry.getValue()) {
				if (point.getX() < minX) minX = point.getX();
				if (point.getX() > maxX) maxX = point.getX();
				if (point.getY() < minY) minY = point.getY();
				if (point.getY() > maxY) maxY = point.getY();
			}

			Rectangle rectangle = new Rectangle(minX, maxX, minY, maxY);
			rectangles.add(rectangle);
		}
		return rectangles;
	}


}
