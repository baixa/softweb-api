package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Image;
import com.softweb.api.store.model.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image getImageById (String imageId) {
        return imageRepository.findById(Long.valueOf(imageId)).orElse(null);
    }

    public List<Image> getImages () {
        return imageRepository.findAll();
    }

    public void saveImage (Image image) {
        imageRepository.save(image);
    }

    public void deleteImage (Image image) {
        imageRepository.delete(image);
    }

    public void deleteImageById (String imageId) {
        imageRepository.deleteById(Long.valueOf(imageId));
    }
}
