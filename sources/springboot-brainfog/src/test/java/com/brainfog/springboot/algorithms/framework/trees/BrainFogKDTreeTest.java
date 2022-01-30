package com.brainfog.springboot.algorithms.framework.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.brainfog.springboot.algorithms.framework.BrainFogMathUtils;
import com.brainfog.springboot.algorithms.framework.models.BrainFogPointDto;
import com.brainfog.springboot.algorithms.framework.models.BrainFogPointDtoComparatorByOrigin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrainFogKDTreeTest {
    private static final int MEDIUM_POINT_TO_SEARCH_SIZE=1000000;
    private static final Logger LOGGER = LoggerFactory.getLogger(BrainFogKDTreeTest.class);

    @Test 
    public void testAddPoint(){
        BrainFogKDTree tree= new BrainFogKDTree(2);
        
        BrainFogPointDto point1=new BrainFogPointDto(Map.ofEntries(
            Map.entry(0L, 100L),
            Map.entry(1L, 100L)
        ));
        
        tree.addPoint(point1);

        BrainFogPointDto nearestPoint=tree.findNearestPoint(point1);

        Assertions.assertEquals(point1,nearestPoint);
    }


    @Test 
    public void testFindNearestPoint(){
        BrainFogKDTree tree= new BrainFogKDTree(2);
        
        BrainFogPointDto point1=new BrainFogPointDto(Map.ofEntries(
            Map.entry(0L, 100L),
            Map.entry(1L, 100L)
        ));

        BrainFogPointDto point2=new BrainFogPointDto(Map.ofEntries(
            Map.entry(0L, 50L),
            Map.entry(1L, 50L)
        ));
        

        BrainFogPointDto point3=new BrainFogPointDto(Map.ofEntries(
            Map.entry(0L, 150L),
            Map.entry(1L, 150L)
        ));

        BrainFogPointDto point4=new BrainFogPointDto(Map.ofEntries(
            Map.entry(0L, 750L),
            Map.entry(1L, 550L)
        ));

        tree.addPoint(point1);
        tree.addPoint(point2);
        tree.addPoint(point3);
        tree.addPoint(point4);

        BrainFogPointDto origin=new BrainFogPointDto(Map.ofEntries(
            Map.entry(0L, 0L),
            Map.entry(1L, 0L)
        ));

        BrainFogPointDto nearestPoint=tree.findNearestPoint(origin);

        Assertions.assertEquals(point2,nearestPoint);
    }

    @Test
    public void testFindNearestPointComplex(){
        //creates the point to add to the tree        
        List<BrainFogPointDto> points=createRandomPoints(10000);
        BrainFogKDTree tree= new BrainFogKDTree(2);
        points.stream().forEach(point -> tree.addPoint(point));
        //create the point to analyte on the find nearest point method
        BrainFogPointDto pointToAnalyze=new BrainFogPointDto(Map.ofEntries(
              Map.entry(0L, getNextRandomNumber()),
              Map.entry(1L, getNextRandomNumber())
           ));
        BrainFogPointDto nearestPoint=tree.findNearestPoint(pointToAnalyze);

        //assert that is the real nearest point
        BrainFogPointDtoComparatorByOrigin comparator= new BrainFogPointDtoComparatorByOrigin(pointToAnalyze);
        points.sort(comparator);

        double nearestPointDistance= BrainFogMathUtils.euclideanDistance(pointToAnalyze.getCoordinates(), nearestPoint.getCoordinates());
        double expectedDistance= BrainFogMathUtils.euclideanDistance(pointToAnalyze.getCoordinates(), points.get(0).getCoordinates());
        Assertions.assertEquals(expectedDistance,nearestPointDistance);
    }

    private List<BrainFogPointDto> createRandomPoints(int numberOfPoints){
        List<BrainFogPointDto> points= new ArrayList<>();        
        for(int i=0;i<numberOfPoints;i++){  
           BrainFogPointDto point=new BrainFogPointDto(Map.ofEntries(
              Map.entry(0L, getNextRandomNumber()),
              Map.entry(1L, getNextRandomNumber())
           )); 
           points.add(point);
        }
        return points;
    }
    /**
     * avoid overflow errors during the euclidean distrance computation.
     * @return the next random number
     */ 
    private Long getNextRandomNumber(){
        Random random= new Random();
        return (long) random.nextInt(3000);
    }

    @Test
    public void testAllPointsStored(){
        //creates the point to add to the tree        
        List<BrainFogPointDto> points=createRandomPoints(10000);
        BrainFogKDTree tree= new BrainFogKDTree(2);
        points.stream().forEach(point -> tree.addPoint(point));
        List<BrainFogPointDto> allPointsOfTheTree=tree.getAllPoints();
        Assertions.assertEquals(10000,allPointsOfTheTree.size());
        Assertions.assertTrue(allPointsOfTheTree.containsAll(points));
    }
    @Test
    public void testSearchSpeedMediumPointSet(){
        //(a)creates the point to add to the tree        
        List<BrainFogPointDto> points=createRandomPoints(10000);
        BrainFogKDTree tree= new BrainFogKDTree(2);
        points.stream().forEach(point -> tree.addPoint(point));
        //(b) creates some point to perform the search
        List<BrainFogPointDto> pointsToSearch=createRandomPoints(MEDIUM_POINT_TO_SEARCH_SIZE);
        //(c) perform the search 
        StopWatch stopWatch= new StopWatch();
        stopWatch.start();
        pointsToSearch.stream().forEach(point -> tree.findNearestPoint(point)); 
        stopWatch.stop();
        LOGGER.info("testSearchSpeedMediumPointSet: seached {} points in {} ms",pointsToSearch.size(),stopWatch.getTotalTimeMillis());
    }


}
