package com.brainfog.springboot.algorithms.framework.models;

public class BrainFogKDTreeNodeDto extends BrainFogTreeNodeDto<BrainFogPointDto,BrainFogKDTreeNodeDto> {
    /**
     * coordinate index used for campare the value @link{BrainFogTreeNodeDto.value} with the other values.
     */
    Long relevantCoordinateIndex;

    public BrainFogKDTreeNodeDto(BrainFogPointDto value,Long relevantCoordinateIndex) {
        super(value);
        this.relevantCoordinateIndex=relevantCoordinateIndex;
    }

    public Long getRelevantCoordinateIndex() {
        return this.relevantCoordinateIndex;
    }

    
}
