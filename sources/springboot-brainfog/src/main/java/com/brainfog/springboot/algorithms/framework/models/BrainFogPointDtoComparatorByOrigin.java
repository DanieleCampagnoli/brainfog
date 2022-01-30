package com.brainfog.springboot.algorithms.framework.models;

import java.util.Comparator;

import com.brainfog.springboot.algorithms.framework.BrainFogMathUtils;

public class BrainFogPointDtoComparatorByOrigin implements Comparator<BrainFogPointDto> {
    /**
     * the point considere as the origin.
     */
    private BrainFogPointDto originPoint;

    /**
     * create the comparator.
     * @param originPoint the origin for the euclidean distance
     */
    public BrainFogPointDtoComparatorByOrigin(BrainFogPointDto originPoint) {
        this.originPoint = originPoint;
    }

    @Override
    public int compare(BrainFogPointDto o1, BrainFogPointDto o2) {
        double d1=BrainFogMathUtils.euclideanDistance(originPoint.getCoordinates(), o1.getCoordinates());
        double d2=BrainFogMathUtils.euclideanDistance(originPoint.getCoordinates(), o2.getCoordinates());
        return Double.compare(d1, d2);
    }
    
}
