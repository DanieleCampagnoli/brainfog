package com.brainfog.springboot.algorithms.framework.models;

import java.util.Map;

/**
 * this class represent a site (center) of the diagram.
 */
public class BrainFogImagePixel extends BrainFogPointDto {    
    /**
     * rgb color.
     */
    Integer color;
     
    public BrainFogImagePixel(Long x,Long y,Integer color){
        super(Map.ofEntries(
            Map.entry(0L, x),
            Map.entry(1L, y)
        ));
        this.color=color;
    }

    public Long getX(){
        return this.getCoordinateValue(0L);
    } 
    public Long getY(){
        return this.getCoordinateValue(1L);
    }

    public Integer getColor(){
        return this.color;
    }
    
    
}
