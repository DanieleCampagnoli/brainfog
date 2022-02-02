package com.brainfog.springboot.rest.clients.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.brainfog.springboot.rest.clients.api.BrainFogNasaPlanetaryApodService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BrainFogNasaPlanetaryApodServiceImpl implements BrainFogNasaPlanetaryApodService {
    private Logger logger = LoggerFactory.getLogger(BrainFogNasaPlanetaryApodServiceImpl.class);

    private static final String MEDIA_TYPE = "media_type";
    private static final String MEDIA_TYPE_IMAGE = "image";
    private static final String HD_URL = "hdurl";
    private static final String FALLBACK_IMAGE_URL = "https://apod.nasa.gov/apod/image/2103/2021_03_02_Mars_Taurus_1800px.jpg";

    @Value("${brainfog.rest.nasa.planetary.apod.token}")
    private String apiAccessToken;
    @Value("${brainfog.rest.nasa.planetary.apod.retrycount}")
    private int retryCount;
    @Value("${brainfog.rest.nasa.planetary.apod.batchSize}")
    private int batchSize;

    private RestTemplate restTemplate;

    BrainFogNasaPlanetaryApodServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<BufferedImage> getRandomImage() {
        // (a) download an image from the nasa API
        // the image of the day can be also a video, but we want only image
        // at the moment the API does not offer a filter for images
        Optional<String> hdUrlOpt = Optional.empty();
        // (a.1)retry the operation the configured number of times before giving up
        for (int i = 0; i < retryCount && hdUrlOpt.isEmpty(); i++) {
            List<Map<String, String>> imageOfTheDayMetadata = restTemplate.getForObject(
                    "https://api.nasa.gov/planetary/apod?api_key={api_key}&count={count}", ArrayList.class,
                    apiAccessToken, batchSize);
            if (!imageOfTheDayMetadata.isEmpty()) {
                Map<String, String> firstResult = imageOfTheDayMetadata.get(0);
                if (MEDIA_TYPE_IMAGE.equals(firstResult.get(MEDIA_TYPE))) {
                    String url = firstResult.get(HD_URL);
                    hdUrlOpt = Optional.of(url);
                }
            }
        }
        // (a.2) if the url is still empty use a default url
        if (hdUrlOpt.isEmpty()) {
            hdUrlOpt = Optional.of(FALLBACK_IMAGE_URL);
        }
        // (b) download the image
        BufferedImage originalImage = null;
        try {
            URL imageUrl = new URL(hdUrlOpt.get());
            originalImage = ImageIO.read(imageUrl);
        } catch (IOException e) {
            logger.error("getRandomImage: failure {}", e);
        }
        return Optional.ofNullable(originalImage);
    }
}
