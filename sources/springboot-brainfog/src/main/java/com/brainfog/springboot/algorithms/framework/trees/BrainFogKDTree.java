package com.brainfog.springboot.algorithms.framework.trees;

import java.util.ArrayList;
import java.util.List;

import com.brainfog.springboot.algorithms.framework.BrainFogMathUtils;
import com.brainfog.springboot.algorithms.framework.models.BrainFogKDTreeNodeDto;
import com.brainfog.springboot.algorithms.framework.models.BrainFogPointDto;

public class BrainFogKDTree {
    /**
     * number of dimensions used by the algorithm.
     */
    private int numberOfDimensions;
    private BrainFogKDTreeNodeDto root;

    /**
     * build a new KD tree object
     * 
     * @param numberOfDimensions the number of dimension used by the tree
     */
    public BrainFogKDTree(int numberOfDimensions) {
        this.numberOfDimensions = numberOfDimensions;
    }

    public void addPoint(BrainFogPointDto point) {
        // (a) trivial scenario, the root is null so we simply save the value as the
        // root.
        if (root == null) {
            this.root = new BrainFogKDTreeNodeDto(point, BrainFogPointDto.MIN_COORDINATE);
            return;
        }
        // (b) the tree is not empty. find best parent
        BrainFogKDTreeNodeDto bestParent = findBestParentNode(point, root);
        // (c) create a new node
        createTreeNode(bestParent, point);
    }

    public BrainFogPointDto findNearestPoint(BrainFogPointDto point) {
        // (a) find best parent like the add node logic
        BrainFogKDTreeNodeDto bestNode = findBestParentNode(point, root);
        // (b) compute euclidean distance from best parent
        double distanceFromBestNode = BrainFogMathUtils.euclideanDistance(point.getCoordinates(),
                bestNode.getValue().getCoordinates());
        // (c) iterate all the tree to find out the real best node
        List<BrainFogKDTreeNodeDto> nodes = new ArrayList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            BrainFogKDTreeNodeDto currentNode = nodes.remove(0);
            // (c.1) update the best node if needed
            double distanceFromCurrentNode = BrainFogMathUtils.euclideanDistance(
                    point.getCoordinates(), currentNode.getValue().getCoordinates());
            if (distanceFromCurrentNode < distanceFromBestNode) {
                bestNode = currentNode;
                distanceFromBestNode = distanceFromCurrentNode;
            }
            // (c.2) compute direct perpendicular
            long relevantCoordinateIndex = currentNode.getRelevantCoordinateIndex();
            double directPerpendicular = BrainFogMathUtils.directPerpendicular(
                    point.getCoordinateValue(relevantCoordinateIndex),
                    currentNode.getValue().getCoordinateValue(relevantCoordinateIndex));
            // (c.3) save the next nodes to visit
            // check if we can skip some nodes(best case for performance)
            if (directPerpendicular > distanceFromBestNode) {
                boolean isLeftChild = isLeftChild(currentNode, point);
                if (isLeftChild) {
                    nodes.addAll(currentNode.getLeftChildNodes());
                } else {
                    nodes.addAll(currentNode.getRightChildNodes());
                }
            } else {
                nodes.addAll(currentNode.getLeftChildNodes());
                nodes.addAll(currentNode.getRightChildNodes());
            }

        }
        return bestNode.getValue();
    }

    public List<BrainFogPointDto> getAllPoints() {
        List<BrainFogPointDto> allPoints = new ArrayList<>();
        List<BrainFogKDTreeNodeDto> nodes = new ArrayList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            BrainFogKDTreeNodeDto currentNode = nodes.remove(0);
            allPoints.add(currentNode.getValue());
            nodes.addAll(currentNode.getLeftChildNodes());
            nodes.addAll(currentNode.getRightChildNodes());
        }
        return allPoints;
    }

    private BrainFogKDTreeNodeDto findBestParentNode(BrainFogPointDto point,
            BrainFogKDTreeNodeDto root) {
        BrainFogKDTreeNodeDto currentNode = root;
        while (currentNode != null) {
            boolean isLeftChild = isLeftChild(currentNode, point);
            // travel to the left if possible, no child -> best parent found
            if (isLeftChild) {
                if (currentNode.getLeftChildNodes().isEmpty()) {
                    return currentNode;
                }
                currentNode = currentNode.getLeftChildNodes().get(0);
            } else {
                // travel to the right if possible, no child -> best parent found
                if (currentNode.getRightChildNodes().isEmpty()) {
                    return currentNode;
                }
                currentNode = currentNode.getRightChildNodes().get(0);
            }
        }
        return null;
    }

    private boolean isLeftChild(BrainFogKDTreeNodeDto treeNode, BrainFogPointDto point) {
        Long relevantCoordinateIndex = treeNode.getRelevantCoordinateIndex();
        Long currentNodeCoordinateValue =
                treeNode.getValue().getCoordinateValue(relevantCoordinateIndex);
        Long pointCoordinateValue = point.getCoordinateValue(relevantCoordinateIndex);
        return currentNodeCoordinateValue <= pointCoordinateValue;
    }

    /**
     * Add a new node to the tree.
     * 
     * creational method that perform all the linking between the new tree node and the parent.
     * 
     * @param parentTreeNode the parent, can be null
     * @param point the point stored on the node
     * @return the new tree node
     */
    private BrainFogKDTreeNodeDto createTreeNode(BrainFogKDTreeNodeDto parentTreeNode,
            BrainFogPointDto point) {
        // root node
        if (parentTreeNode == null) {
            return new BrainFogKDTreeNodeDto(point, BrainFogPointDto.MIN_COORDINATE);
        }
        // all the other nodes
        Long coordinate =
                ((parentTreeNode.getRelevantCoordinateIndex() + 1) < this.numberOfDimensions)
                        ? parentTreeNode.getRelevantCoordinateIndex() + 1
                        : BrainFogPointDto.MIN_COORDINATE;
        BrainFogKDTreeNodeDto newTreeNode = new BrainFogKDTreeNodeDto(point, coordinate);
        List<BrainFogKDTreeNodeDto> newChildList = new ArrayList<>();
        newChildList.add(newTreeNode);
        boolean isLeftChild = isLeftChild(parentTreeNode, point);
        if (isLeftChild) {
            parentTreeNode.setLeftChildNodes(newChildList);
        } else {
            parentTreeNode.setRightChildNodes(newChildList);
        }
        return newTreeNode;
    }

}
