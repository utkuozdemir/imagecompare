package org.utkuozdemir.engine.util;

import java.awt.*;

public class ColorUtil {
	public static double diffInPercentage(int color1rgb, int color2rgb) {
		Color c1 = new Color(color1rgb);
		Color c2 = new Color(color2rgb);

		int diffRed = Math.abs(c1.getRed() - c2.getRed());
		int diffGreen = Math.abs(c1.getGreen() - c2.getGreen());
		int diffBlue = Math.abs(c1.getBlue() - c2.getBlue());

		double pctDiffRed = (double) diffRed / 255;
		double pctDiffGreen = (double) diffGreen / 255;
		double pctDiffBlue = (double) diffBlue / 255;

		return (pctDiffRed + pctDiffGreen + pctDiffBlue) / 3 * 100;
	}
}
