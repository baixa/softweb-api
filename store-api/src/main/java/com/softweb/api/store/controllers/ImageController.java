package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.image.ImageDto;
import com.softweb.api.store.model.entities.Image;
import com.softweb.api.store.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getImageById (@PathVariable(name = "id") String imageId) {
        Image image = imageService.getImageById(imageId);
        return ResponseEntity.ok(new ImageDto(image));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getImages () {
        List<Image> imageList = imageService.getImages();
        List<ImageDto> imageDtos = imageList.stream().map(ImageDto::new).toList();
        return ResponseEntity.ok(imageDtos);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postImage (@RequestBody Image image) {
        imageService.saveImage(image);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> putImage (@RequestBody Image image) {
        imageService.saveImage(image);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImage (@PathVariable(name = "id") String imageId) {
        imageService.deleteImageById(imageId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
