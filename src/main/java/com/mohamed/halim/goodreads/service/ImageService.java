package com.mohamed.halim.goodreads.service;


import com.google.common.io.Files;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    public String saveImage(MultipartFile img) throws IllegalStateException, IOException {
        String name = String.format("%s.%s",
                UUID.randomUUID().toString(),
                Files.getFileExtension(img.getOriginalFilename()));
        File file = new File("img" + File.separator + name);
        Files.createParentDirs(file);
        img.transferTo(file.getAbsoluteFile());
        return file.getName();
    }

    public Mono<Resource> loadImage(String name) throws FileNotFoundException {
        File file = new File("img" , name);
        return Mono.just(new InputStreamResource(new FileInputStream(file)));
    }

}