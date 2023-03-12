package com.mohamed.halim.goodreads.service;

import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ImageServiceTest {
    @InjectMocks
    private ImageService imageService;

    @Test
    void test_saveImage() throws IOException {
        MockMultipartFile file = new MockMultipartFile("testImage.jpg", new byte[200000]);
        System.out.println("FILE NAME: " + file.getName());
        String name = imageService.saveImage(file);
        Assertions.assertTrue(new File("img", name).exists());
    }

}