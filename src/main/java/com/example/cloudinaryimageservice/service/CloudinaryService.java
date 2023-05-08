package com.example.cloudinaryimageservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String uploadImage(MultipartFile file, String key);

    void deleteImage(String key);
}
