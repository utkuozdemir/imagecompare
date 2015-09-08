package org.utkuozdemir.engine.point;

import org.utkuozdemir.engine.Direction;

public class DiffPoint extends Point {
	private DiffPoint[][] diffMatrix;
	private boolean different;

	public DiffPoint(DiffPoint[][] diffMatrix, int x, int y, boolean different) {
		super(x, y);
		this.diffMatrix = diffMatrix;
		this.different = different;
	}

	public boolean isDifferent() {
		return different;
	}

	public void setDifferent(boolean different) {
		this.different = different;
	}

	public DiffPoint getNeighbor(Direction direction) {
		int x = this.x, y = this.y;
		switch (direction) {
			case UP: {
				x--;
				break;
			}
			case DOWN: {
				x++;
				break;
			}
			case LEFT: {
				y--;
				break;
			}
			case RIGHT: {
				y++;
				break;
			}
		}
		if (x < 0 || x >= diffMatrix.length || y < 0 || y >= diffMatrix[0].length) return null;
		return diffMatrix[x][y];
	}
}
