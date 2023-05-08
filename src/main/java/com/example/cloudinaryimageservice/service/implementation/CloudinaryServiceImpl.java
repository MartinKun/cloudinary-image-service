package com.example.cloudinaryimageservice.service.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.cloudinaryimageservice.config.CloudinaryConfig;
import com.example.cloudinaryimageservice.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final CloudinaryConfig cloudinaryConfig;

    @Override
    public String uploadImage(MultipartFile file, String key) {
        String path = "";
        Cloudinary cloudinary = cloudinaryConfig.cloudinary();
        Map params = ObjectUtils.asMap(
                "public_id", key,
                "overwrite", true,
                "resource_type", "image"
        );
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
            path = (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Error occurred while processing the file: %s", e.getMessage())
            );
        }
        return path;
    }

    @Override
    public void deleteImage(String key) {
        Cloudinary cloudinary = cloudinaryConfig.cloudinary();
        Map params = ObjectUtils.asMap("invalidate", true,
                "resource_type", "image");

        try {
            cloudinary.uploader().destroy(key, params);
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Error occurred while processing the file: %s", e.getMessage())
            );
        }
    }
}
