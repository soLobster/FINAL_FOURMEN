package com.itwill.teamfourmen.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageService {
	
	public ByteArrayResource renderFromUrlImage(String url) throws IOException {
		
		log.info("renderFromUrlImage()");
		
        WebClient webClient = WebClient.create();

        byte[] imageData = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        ByteArrayResource imageResource = new ByteArrayResource(imageData);
        
        return imageResource;
	}
	
	
}
