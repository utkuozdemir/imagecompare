package org.utkuozdemir.engine;

import org.utkuozdemir.engine.point.Point;

import java.util.HashSet;
import java.util.Set;

public class Rectangle {
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	public Rectangle(int minX, int maxX, int minY, int maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public Set<Point> getPixelXYs() {
		Set<Point> result = new HashSet<>();
		for (int i = minX; i <= maxX; i++) {
			result.add(new Point(i, minY));
			result.add(new Point(i, maxY));
		}

		for (int i = minY; i <= maxY; i++) {
			result.add(new Point(minX, i));
			result.add(new Point(maxX, i));
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Rectangle that = (Rectangle) o;
		return minX == that.minX && maxX == that.maxX && minY == that.minY && maxY == that.maxY;
	}

	@Override
	public int hashCode() {
		int result = minX;
		result = 31 * result + maxX;
		result = 31 * result + minY;
		result = 31 * result + maxY;
		return result;
	}
}
