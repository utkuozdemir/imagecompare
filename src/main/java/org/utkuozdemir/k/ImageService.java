package org.utkuozdemir.k;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public abstract class ImageService {
    public static ArrayList<Point> getDifferentPoints(BufferedImage img1, BufferedImage img2){
        ArrayList<Point> listOfPoints = new ArrayList<>();
        int width = img1.getWidth();
        int height = img1.getHeight();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color color1 = new Color(img1.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i,j));
                if (!colorsAreSimilar(color1,color2)){
                    listOfPoints.add(new Point(i,j));
                }
            }
        }

        return listOfPoints;
    }

    public static BufferedImage getImageWithMarkedDifferences(BufferedImage img, ArrayList<Point> listOfPoints){
        BufferedImage resultImage = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
        Graphics g = resultImage.getGraphics();
        g.drawImage(img,0,0,null);

        Map<Integer,HashSet<Point>> mapOfAreas = getAreasToMark(listOfPoints);

        for (HashSet<Point> area: mapOfAreas.values()){
            markTheArea(resultImage,area);
        }

        return resultImage;
    }

    private static boolean colorsAreSimilar(Color color1, Color color2){
        int red1 = color1.getRed();
        int red2 = color2.getRed();
        int green1 = color1.getGreen();
        int green2 = color2.getGreen();
        int blue1 = color1.getBlue();
        int blue2 = color2.getBlue();
        double dif1 = Math.abs(((double)(red1 - red2)/255));
        double dif2 = Math.abs(((double) (green1 - green2) / 255));
        double dif3 = Math.abs(((double)(blue1 - blue2)/255));
        double difAverage = (dif1 + dif2 + dif3)/3;
        return difAverage <= 0.1;

    }

    private static Map<Integer,HashSet<Point>> getAreasToMark(ArrayList<Point> listOfPoints){
        Map<Integer,HashSet<Point>> mapOfAreas = new HashMap<>();
        int key = 1;
        for (Point point: listOfPoints){
            ArrayList<Integer> keys = new ArrayList<>();
            if (!mapOfAreas.isEmpty()) {
                for (Map.Entry<Integer,HashSet<Point>> entry : mapOfAreas.entrySet()) {
                    if (pointBelongsToArea(point, entry.getValue())) {
                        keys.add(entry.getKey());
                    }
                }
                if (keys.size() > 1) {
                    HashSet<Point> origin = mapOfAreas.get(keys.get(0));
                    for (Object key1 : keys) {
                        int current = (int) key1;
                        origin.addAll(mapOfAreas.get(current));
                        mapOfAreas.remove(current);
                    }
                    origin.add(point);
                    mapOfAreas.put(key,origin);
                    key++;
                }else if (keys.size() == 1){
                    mapOfAreas.get(keys.get(0)).add(point);
                }else {
                    HashSet<Point> hashSet = new HashSet<>();
                    hashSet.add(point);
                    mapOfAreas.put(key, hashSet);
                    key++;
                }
            }else {
                HashSet<Point> hashSet = new HashSet<>();
                hashSet.add(point);
                mapOfAreas.put(key, hashSet);
                key++;
            }
        }
        return mapOfAreas;
    }

    private static boolean pointBelongsToArea(Point point, HashSet<Point> listOfPoints){
      for (Point p: listOfPoints){
          if (point.distance(p) <= 5){
              return true;
          }
      }
        return false;
    }

    private static void markTheArea(BufferedImage image, HashSet<Point> area){
        Point minPointX = Collections.min(area, (o1, o2) -> (int) (o1.getX() - o2.getX()));
        int minX = (int) minPointX.getX();

        Point maxPointX = Collections.max(area, (o1, o2) -> (int) (o1.getX() - o2.getX()));
        int maxX = (int) maxPointX.getX();

        Point minPointY = Collections.min(area, (o1, o2) -> (int) (o1.getY() - o2.getY()));
        int minY = (int) minPointY.getY();

        Point maxPointY = Collections.max(area, (o1, o2) -> (int) (o1.getY() - o2.getY()));
        int maxY = (int) maxPointY.getY();

        Graphics g = image.getGraphics();
        g.setColor(Color.RED);
        g.drawRect(minX, minY, maxX - minX, maxY - minY);
    }

}