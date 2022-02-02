package com.brainfog.springboot.rest.clients.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.brainfog.springboot.algorithms.tiling.impl.voronoi.BrainFogVoronoiServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class BrainFogNasaPlanetaryApodServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrainFogNasaPlanetaryApodServiceImplTest.class);
    @Autowired
    BrainFogNasaPlanetaryApodServiceImpl testee;
    /**
     * 
     * FIXME: move this code on another test.
     */
    @Test
    public void test() throws IOException{
       BufferedImage response=testee.getRandomImage().get();
       LOGGER.info("response {}",response);
       BrainFogVoronoiServiceImpl voronoiService= new BrainFogVoronoiServiceImpl();
       BufferedImage imageTiling=voronoiService.executeImageTilingWithKDTree(response,5000,false);
       ImageIO.write(imageTiling, "png", new File("./test-output/BrainFogNasaPlanetaryApodServiceImplTest.png"));
    }
}
