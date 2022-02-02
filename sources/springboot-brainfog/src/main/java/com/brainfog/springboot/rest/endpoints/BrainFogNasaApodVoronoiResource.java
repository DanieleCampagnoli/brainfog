package com.brainfog.springboot.rest.endpoints;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.brainfog.springboot.algorithms.tiling.api.voronoi.BrainFogVoronoiService;
import com.brainfog.springboot.rest.clients.api.BrainFogNasaPlanetaryApodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * creates a jpg image automatically with the voronoi algorithm.
 */
@RestController
public class BrainFogNasaApodVoronoiResource {
    @Autowired
    BrainFogNasaPlanetaryApodService brainFogNasaPlanetaryApodService;
    @Autowired
    BrainFogVoronoiService brainFogVoronoiService;
	
    @RequestMapping(value="/nasa/apod/voronoi",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] home() throws IOException {
      BufferedImage bufferedImage=brainFogNasaPlanetaryApodService.getRandomImage().get();
      BufferedImage imageTiling=brainFogVoronoiService.executeImageTilingWithKDTreeWindow(bufferedImage, 1000, false);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(imageTiling, "jpg", baos);
      byte[] imageData = baos.toByteArray();
	  return imageData;
	}
}
