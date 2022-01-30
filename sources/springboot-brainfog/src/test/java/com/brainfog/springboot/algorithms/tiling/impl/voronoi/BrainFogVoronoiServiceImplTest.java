package com.brainfog.springboot.algorithms.tiling.impl.voronoi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

public class BrainFogVoronoiServiceImplTest {
    
    private BrainFogVoronoiServiceImpl testee= new BrainFogVoronoiServiceImpl();

    @Test  
    public void testBruteForceSiteIteration() throws IOException{
        URL originalImageUrl=this.getClass().getClassLoader().getResource("20200709_111353.jpg");
        BufferedImage originalImage= ImageIO.read(originalImageUrl);
        BufferedImage imageTiling=testee.executeImageTilingWithBruteForceIteration(originalImage,10,false);
        ImageIO.write(imageTiling, "png", new File("./test-output/20200709_111353_testBruteForceSiteIteration.png"));
    }

    @Test
    public void testWindowSiteIteration() throws IOException{
        URL originalImageUrl=this.getClass().getClassLoader().getResource("20200709_111353.jpg");
        BufferedImage originalImage= ImageIO.read(originalImageUrl);
        BufferedImage imageTiling=testee.executeImageTilingWithKDTree(originalImage,10000,false);
        ImageIO.write(imageTiling, "png", new File("./test-output/20200709_111353_testWindowSiteIteration.png"));
    }
}
