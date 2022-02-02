package com.brainfog.springboot.algorithms.framework.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * this class represents a squared window of coordinates on an image.
 */
public class BrainFogImageWindow {
    int xStart;
    int xEnd;
    int yStart;
    int yEnd;
    
    List<BrainFogImagePixel> pixels= new ArrayList<>();

    public BrainFogImageWindow(int xStart, int xEnd, int yStart, int yEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public int getXStart() {
        return this.xStart;
    }

    public void setXStart(int xStart) {
        this.xStart = xStart;
    }

    public int getXEnd() {
        return this.xEnd;
    }

    public void setXEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getYStart() {
        return this.yStart;
    }

    public void setYStart(int yStart) {
        this.yStart = yStart;
    }

    public int getYEnd() {
        return this.yEnd;
    }

    public void setYEnd(int yEnd) {
        this.yEnd = yEnd;
    }
    
    public List<Integer> getCoordinateRangeOfX(){
      return IntStream.range(getXStart(), getXEnd()).boxed().collect(Collectors.toList());
    }

    public List<Integer> getCoordinateRangeOfY(){
        return IntStream.range(getYStart(), getYEnd()).boxed().collect(Collectors.toList());
    }

    public void addPixel(BrainFogImagePixel pixel){
        this.pixels.add(pixel);
    }

    public List<BrainFogImagePixel> getPixels() {
        return Collections.unmodifiableList(this.pixels);
    }

}
