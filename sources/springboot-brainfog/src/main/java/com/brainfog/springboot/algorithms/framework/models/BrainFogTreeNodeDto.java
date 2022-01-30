package com.brainfog.springboot.algorithms.framework.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class represent a node in the tree data structure.
 */
public abstract class BrainFogTreeNodeDto<T, P> {
    boolean rootNode=false;
    /**
     * the value that this tree node holds. The client needs to cast the object to the correct class.
     */
    T value;
    List<P> leftChildNodes= new ArrayList<>();
    List<P> rightChildNodes= new ArrayList<>();
    
    /**
     * create a new node.
     * @param value the value that the node holds.
     */
    public BrainFogTreeNodeDto(T value) {
        this.value = value;
    }

   
    public boolean isRootNode() {
        return this.rootNode;
    }

    public boolean getRootNode() {
        return this.rootNode;
    }

    public void setRootNode(boolean rootNode) {
        this.rootNode = rootNode;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<P> getLeftChildNodes() {
        return Collections.unmodifiableList(this.leftChildNodes);
    }

    public void setLeftChildNodes(List<P> leftChildNodes) {
        this.leftChildNodes = leftChildNodes;
    }

    public List<P> getRightChildNodes() {
        return Collections.unmodifiableList(this.rightChildNodes);
    }

    public void setRightChildNodes(List<P> rightChildNodes) {
        this.rightChildNodes = rightChildNodes;
    }


}
