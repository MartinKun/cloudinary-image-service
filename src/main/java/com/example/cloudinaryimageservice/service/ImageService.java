package com.example.cloudinaryimageservice.service;

import com.example.cloudinaryimageservice.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Image getImage(Long id);

    Image saveImage(MultipartFile file) throws IOException;

    void deleteImage(Long id);
}
