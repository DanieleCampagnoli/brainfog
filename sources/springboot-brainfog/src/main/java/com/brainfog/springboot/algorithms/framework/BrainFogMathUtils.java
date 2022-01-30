package com.brainfog.springboot.algorithms.framework;

import java.util.List;
import java.util.NavigableMap;

public class BrainFogMathUtils {
    /**
     * compute the euclidean distrance between 2 points.
     * 
     * @param P1Coordinates the coordinates of the first point
     * @param P2Coordinates the coordinates of the first point
     * @return the distance
     */
    public static double euclideanDistance(NavigableMap<Long, Long> p1Coordinates, NavigableMap<Long, Long> p2Coordinates) {
        double sumOfSquaredDifferences = 0;
        for (Long coordinateIndex:p1Coordinates.keySet()) {
           Long difference=p1Coordinates.get(coordinateIndex) - p2Coordinates.get(coordinateIndex);
           sumOfSquaredDifferences += Math.pow(difference, 2);
        }
        return Math.sqrt(sumOfSquaredDifferences);
    }
    /**
     * compute the direct perpendicular of 2 points
     * @param p1Coordinate first coodinate
     * @param p2Coordinate second coordinate
     * @return the direct perpendicular
     */ 
    public static double directPerpendicular(long p1Coordinate,long p2Coordinate){
        return Math.abs(p1Coordinate-p2Coordinate);
    }
}
