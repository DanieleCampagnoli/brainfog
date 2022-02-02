package com.brainfog.springboot.rest.clients.api;

import java.util.Optional;
import java.awt.image.BufferedImage;
/**
 * rest client of the Nasa APOD API.
 */
public interface BrainFogNasaPlanetaryApodService {
    /**
     * get a random image from an external web service.
     * @return the image or null
     */
    Optional<BufferedImage> getRandomImage();
}
