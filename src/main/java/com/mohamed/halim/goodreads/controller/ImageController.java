package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.io.IOException;

@RequestMapping("api/v1/img")
@RestController
@AllArgsConstructor
@Slf4j
public class ImageController {
    private ImageService imageService;


    @GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<Resource> getImage(@PathVariable String name) throws IOException {
        return  imageService.loadImage(name);
    }
}