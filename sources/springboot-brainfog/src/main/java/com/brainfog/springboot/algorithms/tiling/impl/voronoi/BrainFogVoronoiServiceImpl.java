package com.brainfog.springboot.algorithms.tiling.impl.voronoi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.stream.Collectors;
import java.awt.geom.Ellipse2D;
import javax.imageio.ImageIO;

import com.brainfog.springboot.algorithms.framework.BrainFogMathUtils;
import com.brainfog.springboot.algorithms.framework.models.BrainFogImagePixel;
import com.brainfog.springboot.algorithms.framework.models.BrainFogImageWindow;
import com.brainfog.springboot.algorithms.framework.models.BrainFogPointDto;
import com.brainfog.springboot.algorithms.framework.models.BrainFogPointDtoComparatorByOrigin;
import com.brainfog.springboot.algorithms.framework.trees.BrainFogKDTree;
import com.brainfog.springboot.algorithms.tiling.api.voronoi.BrainFogVoronoiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * implementation of the @link{BrainFogVoronoiService}.
 */
@Service
public class BrainFogVoronoiServiceImpl implements BrainFogVoronoiService {

    Logger logger = LoggerFactory.getLogger(BrainFogVoronoiServiceImpl.class);

    @Override
    public BufferedImage executeImageTilingWithBruteForceIteration(BufferedImage originalImage, int numberOfSites,
            boolean printTiles) {
        // (a) basic variable initialization
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // (a.1) initialize and empty copy of the original image
        BufferedImage imageTiling = createEmptyImageTiling(originalImage.getWidth(), originalImage.getHeight());
        // (b) compute the site position on the image and copy the color
        List<BrainFogImagePixel> sites = computeSites(originalImage, numberOfSites);
        stopWatch.stop();
        logger.info("executeImageTilingWithBruteForceIteration:STEP B: COMPLETED. compute the site position on the image and copy the color {} ms",
                stopWatch.getTotalTimeMillis());
        stopWatch.start();
        // (c) for every pixel of the image find the closest site and apply his color to
        // the new
        // image
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                BrainFogImagePixel currentPixel = new BrainFogImagePixel((long) x, (long) y, 0);
                BrainFogImagePixel bestSite = sites.get(0);
                for (int siteIndex = 0; siteIndex < sites.size(); siteIndex++) {

                    if (BrainFogMathUtils.euclideanDistance(currentPixel.getCoordinates(),
                            sites.get(siteIndex).getCoordinates()) < BrainFogMathUtils
                                    .euclideanDistance(currentPixel.getCoordinates(), bestSite.getCoordinates())) {
                        bestSite = sites.get(siteIndex);
                    }
                }
                // save the color of the best site on the image tiling
                imageTiling.setRGB(x, y, bestSite.getColor());
            }
        }
        stopWatch.stop();
        logger.info(
                "executeImageTilingWithBruteForceIteration:STEP C: COMPLETED.  for every pixel of the image find the closest site and apply his color to the new image {} ms",
                stopWatch.getTotalTimeMillis());
        // (d) print the tiles on the output image if needed
        if (printTiles) {
            printSites(sites, imageTiling);
        }
        logger.info("executeImageTilingWithBruteForceIteration:STEP D: COMPLETED.");
        // (e) return the image modified
        return imageTiling;
    }

    @Override
    public BufferedImage executeImageTilingWithKDTree(BufferedImage originalImage, int numberOfSites,
            boolean printTiles) {
        // (a) basic variable initialization
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // (a.1) initialize and empty copy of the original image
        BufferedImage imageTiling = createEmptyImageTiling(originalImage.getWidth(), originalImage.getHeight());
        // (b) compute the site position on the image and copy the color
        List<BrainFogImagePixel> sites = computeSites(originalImage, numberOfSites);
        stopWatch.stop();
        logger.info("executeImageTilingWithKDTree:STEP B: COMPLETED. compute the site position on the image and copy the color {} ms",
                stopWatch.getTotalTimeMillis());
        stopWatch.start();
        // (c) for every pixel of the image find the closest site and apply his color to
        // the new
        // image
        // (a) build the KD tree with the tiles
        BrainFogKDTree tree = new BrainFogKDTree(2);
        sites.stream().forEach(site -> tree.addPoint(site));
        // (b) iterate over the original image pixel and find the best site to create
        // the image
        // tiling
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                BrainFogPointDto point = new BrainFogPointDto(
                        Map.ofEntries(Map.entry(0L, (long) x), Map.entry(1L, (long) y)));
                BrainFogImagePixel nearestSite = (BrainFogImagePixel) tree.findNearestPoint(point);
                // save the color of the best site on the image tiling
                imageTiling.setRGB(x, y, nearestSite.getColor());
            }
        }
        stopWatch.stop();
        logger.info(
                "executeImageTilingWithKDTree:STEP C: COMPLETED.  for every pixel of the image find the closest site and apply his color to the new image {} ms",
                stopWatch.getTotalTimeMillis());
        // (d) print the tiles on the output image if needed
        if (printTiles) {
            printSites(sites, imageTiling);
        }
        logger.info("STEP D: COMPLETED.");
        // (e) return the image modified
        return imageTiling;
    }

    @Override
    public BufferedImage executeImageTilingWithKDTreeWindow(BufferedImage originalImage, int numberOfSites,
            boolean printTiles) {
        // (a) basic variable initialization
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // (a.1) initialize and empty copy of the original image
        BufferedImage imageTiling = createEmptyImageTiling(originalImage.getWidth(), originalImage.getHeight());
        // (b) compute the site position on the image and copy the color
        List<BrainFogImagePixel> sites = computeSites(originalImage, numberOfSites);
        stopWatch.stop();
        logger.info("executeImageTilingWithKDTreeWindow:STEP B: COMPLETED. compute the site position on the image and copy the color {} ms",
                stopWatch.getTotalTimeMillis());
        stopWatch.start();
        // (c) for every pixel of the image find the closest site and apply his color to
        // the new
        // image
        // (c.1) build the KD tree with the tiles
        BrainFogKDTree tree = new BrainFogKDTree(2);
        sites.stream().forEach(site -> tree.addPoint(site));
        // (c.2) iterate over the original image pixel and find the best site to create
        // the image
        // tiling
        BrainFogImageWindow window1 = new BrainFogImageWindow(0, originalImage.getWidth() / 2, 0,
                originalImage.getHeight() / 2);
        BrainFogImageWindow window2 = new BrainFogImageWindow(originalImage.getWidth() / 2, originalImage.getWidth(), 0,
                originalImage.getHeight() / 2);
        BrainFogImageWindow window3 = new BrainFogImageWindow(0, originalImage.getWidth() / 2,
                originalImage.getHeight() / 2, originalImage.getHeight());
        BrainFogImageWindow window4 = new BrainFogImageWindow(originalImage.getWidth() / 2, originalImage.getWidth(),
                originalImage.getHeight() / 2, originalImage.getHeight());

        List<BrainFogImageWindow> allWindows = Arrays.asList(window1, window2, window3, window4);
        // (c.3) start one thread for every window and compute the new image pixels
        allWindows.parallelStream()
                .forEach(window -> kdTreeIterationStrategyWithParallelStreamWindow(originalImage, tree, window));
        // (c.4) save the image
        for (BrainFogImageWindow window : allWindows) {
            for (BrainFogImagePixel pixel : window.getPixels()) {
                // save the color of the best site on the image tiling
                imageTiling.setRGB(pixel.getX().intValue(), pixel.getY().intValue(), pixel.getColor());
            }
        }
        stopWatch.stop();
        logger.info(
                "executeImageTilingWithKDTreeWindow:STEP C: COMPLETED.  for every pixel of the image find the closest site and apply his color to the new image {} ms",
                stopWatch.getTotalTimeMillis());
        // (d) print the tiles on the output image if needed
        if (printTiles) {
            printSites(sites, imageTiling);
        }
        logger.info("STEP D: COMPLETED.");
        // (e) return the image modified
        return imageTiling;
    }

    private BufferedImage createEmptyImageTiling(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    private List<BrainFogImagePixel> computeSites(BufferedImage originalImage, int numberOfSites) {
        List<BrainFogImagePixel> sites = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numberOfSites; i++) {
            long newSiteX = rand.nextInt(originalImage.getWidth());
            long newSiteY = rand.nextInt(originalImage.getHeight());
            Integer color = originalImage.getRGB((int) newSiteX, (int) newSiteY);
            BrainFogImagePixel site = new BrainFogImagePixel(newSiteX, newSiteY, color);
            sites.add(site);
        }
        return sites;
    }

    private void printSites(List<BrainFogImagePixel> sites, BufferedImage imageTiling) {
        Graphics2D g = imageTiling.createGraphics();
        g.setColor(Color.BLACK);
        for (BrainFogImagePixel site : sites) {
            g.fill(new Ellipse2D.Double(site.getX() - 2.5, site.getY() - 2.5, 5, 5));
        }
    }

    private void kdTreeIterationStrategyWithParallelStreamWindow(BufferedImage originalImage, BrainFogKDTree tree,
            BrainFogImageWindow window) {
        for (int x : window.getCoordinateRangeOfX()) {
            for (int y : window.getCoordinateRangeOfY()) {
                BrainFogPointDto point = new BrainFogPointDto(
                        Map.ofEntries(Map.entry(0L, (long) x), Map.entry(1L, (long) y)));
                BrainFogImagePixel nearestSite = (BrainFogImagePixel) tree.findNearestPoint(point);
                BrainFogImagePixel newPixel = new BrainFogImagePixel((long) x, (long) y, nearestSite.getColor());
                window.addPixel(newPixel);
            }
        }
    }

}
