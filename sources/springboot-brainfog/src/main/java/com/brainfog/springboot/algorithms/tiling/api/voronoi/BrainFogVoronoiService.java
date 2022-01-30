package com.brainfog.springboot.algorithms.tiling.api.voronoi;
import java.awt.image.BufferedImage;
/**
 * API to run voronoi tiling on a image.
 */
public interface BrainFogVoronoiService {
    /**
     * execute the image tiling on the input image.
     * The pixels will be evaluated one by one with the tiles.
     * 
     * @param originalImage 
     *         the original image
     * @param numberOfSites 
     *         the number of sites on the image(center with a color)
     * @param printTiles 
     *         print the tiles on the output image 
     * @return the modified image
     */ 
    BufferedImage executeImageTilingWithBruteForceIteration(BufferedImage originalImage, int numberOfSites,boolean printTiles);

    /**
     * execute the image tiling on the input image.
     * The closes tile will be found using a KD tree to speedup the operation.
     * 
     * @param originalImage
     *    the original image
     * @param numberOfSites
     *    the number of sistes
     *  @param printTiles 
     *    print the tiles on the output image
     * 
     * @return the modified image
     */
    BufferedImage executeImageTilingWithKDTree(BufferedImage originalImage, int numberOfSites,boolean printTiles);
}
