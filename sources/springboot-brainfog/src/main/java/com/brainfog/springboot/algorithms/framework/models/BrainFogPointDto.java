package com.brainfog.springboot.algorithms.framework.models;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

/**
 * this class represent a point in a multi-dimensional space.
 */
public class BrainFogPointDto {
    public static final Long MIN_COORDINATE = 0l;
    
    /**
     * key: coordinate index
     * value: coordinate value
     * 
     * Example x=1 y=3 z=5
     */
    NavigableMap<Long, Long> coordinateToValueMap = new TreeMap<>();

    /**
     * create a new point.
     * 
     * @param coordinateToValueMap the coordinate map
     */
    public BrainFogPointDto(Map<Long, Long> coordinateToValueMap) {
        this.coordinateToValueMap.putAll(coordinateToValueMap);
    }

    public Long getCoordinateValue(Long coordinate) {
        return this.coordinateToValueMap.get(coordinate);
    }

    public NavigableMap<Long, Long> getCoordinates(){
        return new TreeMap<>(coordinateToValueMap); 
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BrainFogPointDto)) {
            return false;
        }
        BrainFogPointDto brainFogPointDto = (BrainFogPointDto) o;
        return Objects.equals(coordinateToValueMap, brainFogPointDto.coordinateToValueMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinateToValueMap);
    }


    @Override
    public String toString() {
        return "BrainFogPointDto[coordinateToValueMap:"+coordinateToValueMap+"]";
    }
  

}
