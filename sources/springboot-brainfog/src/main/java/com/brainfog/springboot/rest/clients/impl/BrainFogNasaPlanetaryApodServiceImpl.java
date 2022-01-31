package com.brainfog.springboot.rest.clients.impl;

import java.util.HashMap;
import java.util.Map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class BrainFogNasaPlanetaryApodServiceImpl {
    
    @Value("${brainfog.rest.nasa.planetary.apod.token}")
    private String apiAccessToken;

    private RestTemplate restTemplate;

    BrainFogNasaPlanetaryApodServiceImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate=restTemplateBuilder.build();
    }

    public BufferedImage call(){
        Map<String,String> imageOfTheDayMetadata=restTemplate.getForObject("https://api.nasa.gov/planetary/apod?api_key={api_key}", HashMap.class,apiAccessToken);
        
       BufferedImage originalImage=null;
    try {
        URL imageUrl=new URL(imageOfTheDayMetadata.get("hdurl"));
        originalImage = ImageIO.read(imageUrl );
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 
       return originalImage;
    }
}
