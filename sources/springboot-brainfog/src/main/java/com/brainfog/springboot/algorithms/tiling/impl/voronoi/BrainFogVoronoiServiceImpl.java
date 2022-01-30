package com.brainfog.springboot.algorithms.tiling.impl.voronoi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.awt.geom.Ellipse2D;
import javax.imageio.ImageIO;
import com.brainfog.springboot.algorithms.framework.models.BrainFogPointDto;
import com.brainfog.springboot.algorithms.framework.trees.BrainFogKDTree;
import com.brainfog.springboot.algorithms.tiling.api.voronoi.BrainFogVoronoiService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implementation of the @link{BrainFogVoronoiService}.
 */
public class BrainFogVoronoiServiceImpl implements BrainFogVoronoiService {
    private static final String ITERATION_MODE_BRUTE_FORCE = "ITERATION_MODE_BRUTE_FORCE";
    private static final String ITERATION_MODE_KD_TREE = "ITERATION_MODE_KD_TREE";

    Logger logger = LoggerFactory.getLogger(BrainFogVoronoiServiceImpl.class);

    @Override
    public BufferedImage executeImageTilingWithBruteForceIteration(BufferedImage originalImage,
            int numberOfSites, boolean printTiles) {               
        return executeImageTilingImpl(originalImage, numberOfSites, ITERATION_MODE_BRUTE_FORCE,
                printTiles);
    }

    @Override
    public BufferedImage executeImageTilingWithKDTree(BufferedImage originalImage,
            int numberOfSites, boolean printTiles) {
        return executeImageTilingImpl(originalImage, numberOfSites, ITERATION_MODE_KD_TREE,
                printTiles);
    }


    private BufferedImage executeImageTilingImpl(BufferedImage originalImage, int numberOfSites,
            String iterationMode, boolean printTiles) {
        // (a) basic variable initialization
        // (a.1) initialize and empty copy of the original image
        BufferedImage imageTiling = new BufferedImage(originalImage.getWidth(),
                originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        // (b) compute the site position on the image and copy the color
        List<BrainFogVoronoiSite> sites = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numberOfSites; i++) {
                long newSiteX = rand.nextInt(originalImage.getWidth());
                long newSiteY = rand.nextInt(originalImage.getHeight());
                Integer color = originalImage.getRGB((int) newSiteX, (int) newSiteY);
                BrainFogVoronoiSite site = new BrainFogVoronoiSite(newSiteX, newSiteY, color);
                sites.add(site);        
        }
        logger.info("STEP B: COMPLETED. compute the site position on the image and copy the color");
        // (c) for every pixel of the image find the closest site and apply his color to the new
        // image
        if (iterationMode.equals(ITERATION_MODE_BRUTE_FORCE)) {
            bruteForceIterationStrategy(originalImage, imageTiling, sites);
        }

        if (iterationMode.equals(ITERATION_MODE_KD_TREE)) {
            kdTreeIterationStrategy(originalImage, imageTiling, sites);
        }

        logger.info(
                "STEP C: COMPLETED.  for every pixel of the image find the closest site and apply his color to the new image");
        // (d) print the tiles on the output image if needed
        if (printTiles) {
            Graphics2D g = imageTiling.createGraphics();
            g.setColor(Color.BLACK);
            for (int i = 0; i < numberOfSites; i++) {
                g.fill(new Ellipse2D.Double(sites.get(i).getX() - 2.5, sites.get(i).getY() - 2.5, 5,
                        5));
            }
        }
        logger.info("STEP D: COMPLETED.");
        // (e) return the image modified
        return imageTiling;
    }


    private double distance(long x1, long x2, long y1, long y2) {
        double d;
        d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return d;
    }

    /**
     * this iteration strategy evaluates all the possible sites in order to find the best one.
     * 
     * @param originalImage the original image
     * @param imageTiling the image modified, it will be modified by this method
     * @param sites the list of sites
     */
    private void bruteForceIterationStrategy(BufferedImage originalImage, BufferedImage imageTiling,
            List<BrainFogVoronoiSite> sites) {
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int bestSite = 0;
                for (int siteIndex = 0; siteIndex < sites.size(); siteIndex++) {
                    if (distance(sites.get(siteIndex).getX(), x, sites.get(siteIndex).getY(),
                            y) < distance(sites.get(bestSite).getX(), x, sites.get(bestSite).getY(),
                                    y)) {
                        bestSite = siteIndex;

                    }
                }
                // save the color of the best site on the image tiling
                imageTiling.setRGB(x, y, sites.get(bestSite).getColor());
            }
        }
    }

    private void kdTreeIterationStrategy(BufferedImage originalImage, BufferedImage imageTiling,
            List<BrainFogVoronoiSite> sites) {
        // (a) build the KD tree with the tiles
        BrainFogKDTree tree = new BrainFogKDTree(2);
        sites.stream().forEach(site -> tree.addPoint(site));
        // (b) iterate over the original image pixel and find the best site to create the image
        // tiling
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                BrainFogPointDto point = new BrainFogPointDto(
                        Map.ofEntries(Map.entry(0L, (long) x), Map.entry(1L, (long) y)));
                BrainFogVoronoiSite nearestSite =
                        (BrainFogVoronoiSite) tree.findNearestPoint(point);
                // save the color of the best site on the image tiling
                imageTiling.setRGB(x, y, nearestSite.getColor());
            }
        }
    }



}
