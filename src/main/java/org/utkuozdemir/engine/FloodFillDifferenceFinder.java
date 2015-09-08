package org.utkuozdemir.engine;

import org.utkuozdemir.engine.point.DiffPoint;
import org.utkuozdemir.engine.point.Point;
import org.utkuozdemir.engine.util.ColorUtil;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;

import static org.utkuozdemir.engine.Direction.*;

public class FloodFillDifferenceFinder extends DifferenceFinder {

	public FloodFillDifferenceFinder(BufferedImage originalImage, BufferedImage differenceImage) {
		super(originalImage, differenceImage);
	}

	@Override
	protected Map<Integer, Set<Point>> groupPoints() {
		Map<Integer, Set<Point>> diffMap = new HashMap<>();
		int index = 1;
		DiffPoint[][] diffMatrix = getDiffMatrix();
		for (int i = 0; i < originalImage.getWidth(); i++) {
			for (int j = 0; j < originalImage.getHeight(); j++) {
				DiffPoint diffPoint = diffMatrix[i][j];
				if (diffPoint.isDifferent()) {
					Set<Point> area = getArea(diffPoint);
					diffMap.put(index++, area);
				}
			}
		}
		return diffMap;
	}

	@SuppressWarnings("unused")
	private DiffPoint[][] getDiffMatrix() {
		long start = System.currentTimeMillis();
		DiffPoint[][] diffMatrix = new DiffPoint[originalImage.getWidth()][originalImage.getHeight()];

		// parallel
		IntStream.range(0, originalImage.getWidth()).parallel().forEach(i -> {
			IntStream.range(0, originalImage.getHeight()).parallel().forEach(j -> {
				int pixel1 = originalImage.getRGB(i, j);
				int pixel2 = differenceImage.getRGB(i, j);
				boolean different = ColorUtil.diffInPercentage(pixel1, pixel2) > 0.1;
				diffMatrix[i][j] = new DiffPoint(diffMatrix, i, j, different);
			});
		});

		// non-parallel
//		for (int i = 0; i < originalImage.getWidth(); i++) {
//			for (int j = 0; j < originalImage.getHeight(); j++) {
//				int pixel1 = originalImage.getRGB(i, j);
//				int pixel2 = differenceImage.getRGB(i, j);
//				boolean different = ColorUtil.diffInPercentage(pixel1, pixel2) > 0.1;
//				diffMatrix[i][j] = new DiffPoint(diffMatrix, i, j, different);
//			}
//		}

		long end = System.currentTimeMillis();
//		System.out.println("Diff matrix took " + (end - start) + " milliseconds...");
		return diffMatrix;
	}

	/**
	 * Uses non-recursive flood fill algorithm.
	 * Check https://en.wikipedia.org/wiki/Flood_fill#Alternative_implementations
	 * @param point Point to start flood fill
	 * @return The difference area
	 */
	private Set<Point> getArea(DiffPoint point) {
		if (point == null) throw new NullPointerException("point should not be null!");
		if (!point.isDifferent()) return Collections.emptySet();
		Set<Point> area = new HashSet<>();

		Queue<DiffPoint> queue = new LinkedList<>();
		queue.add(point);
		while (!queue.isEmpty()) {
			DiffPoint n = queue.poll();
			if (n.isDifferent()) {
				area.add(n);
				n.setDifferent(false);

				DiffPoint up = n.getNeighbor(UP);
				if (up != null && up.isDifferent()) queue.add(up);
				DiffPoint down = n.getNeighbor(DOWN);
				if (down != null && down.isDifferent()) queue.add(down);
				DiffPoint left = n.getNeighbor(LEFT);
				if (left != null && left.isDifferent()) queue.add(left);
				DiffPoint right = n.getNeighbor(RIGHT);
				if (right != null && right.isDifferent()) queue.add(right);
			}
		}
		return area;
	}
}
