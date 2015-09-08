package org.utkuozdemir.engine;

import org.utkuozdemir.engine.util.ColorUtil;

import java.awt.image.BufferedImage;
import java.util.*;

public class ImageAnalysisDifferenceFinder extends DifferenceFinder {

	public ImageAnalysisDifferenceFinder(BufferedImage originalImage, BufferedImage differenceImage) {
		super(originalImage, differenceImage);
	}

	protected Map<Integer, Set<org.utkuozdemir.engine.point.Point>> groupPoints() {
		Map<Integer, Set<org.utkuozdemir.engine.point.Point>> differenceGroups = new HashMap<>();
		for (org.utkuozdemir.engine.point.Point differentPoint : getDifferentPoints()) {
			Set<Integer> belongedGroupIds = new HashSet<>();
			for (Map.Entry<Integer, Set<org.utkuozdemir.engine.point.Point>> entry : differenceGroups.entrySet()) {
				Set<org.utkuozdemir.engine.point.Point> group = entry.getValue();
				for (org.utkuozdemir.engine.point.Point point : group) {
					if (differentPoint.distanceTo(point) <= 5) {
						belongedGroupIds.add(entry.getKey());
						break;
					}
				}
			}

			if (belongedGroupIds.size() > 1) {
				Set<org.utkuozdemir.engine.point.Point> mergedGroup = new HashSet<>();
				for (Integer id : belongedGroupIds) {
					mergedGroup.addAll(differenceGroups.get(id));
					differenceGroups.remove(id);
				}
				differenceGroups.put(belongedGroupIds.iterator().next(), mergedGroup);
			} else if (belongedGroupIds.size() == 1) {
				differenceGroups.get(belongedGroupIds.iterator().next()).add(differentPoint);
			} else {
				Set<org.utkuozdemir.engine.point.Point> newGroup = new HashSet<>();
				newGroup.add(differentPoint);
				if (differenceGroups.isEmpty()) {
					differenceGroups.put(1, newGroup);
				} else {
					differenceGroups.put(Collections.max(differenceGroups.keySet()) + 1, newGroup);
				}
			}
		}
		return differenceGroups;
	}


	private Set<org.utkuozdemir.engine.point.Point> getDifferentPoints() {
		Set<org.utkuozdemir.engine.point.Point> differentPoints = new HashSet<>();
		for (int i = 0; i < originalImage.getWidth(); i++) {
			for (int j = 0; j < originalImage.getHeight(); j++) {
				int pixel1 = originalImage.getRGB(i, j);
				int pixel2 = differenceImage.getRGB(i, j);

				if (ColorUtil.diffInPercentage(pixel1, pixel2) > 0.1) {
					differentPoints.add(new org.utkuozdemir.engine.point.Point(i, j));
				}
			}
		}
		return differentPoints;
	}
}
