package com.example.cloudinaryimageservice.service.implementation;

import com.example.cloudinaryimageservice.entity.Image;
import com.example.cloudinaryimageservice.repository.ImageRepository;
import com.example.cloudinaryimageservice.service.CloudinaryService;
import com.example.cloudinaryimageservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Image with ID: %d not found", id)
                ));
    }

    @Override
    public Image saveImage(MultipartFile file) {
        String key = UUID.randomUUID().toString();
        String path = cloudinaryService.uploadImage(file, key);
        Image image = Image.builder()
                .path(path)
                .publicKey(key)
                .build();
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Image with ID: %d not found", id)
                ));
        cloudinaryService.deleteImage(image.getPublicKey());
        imageRepository.delete(image);
    }
}
