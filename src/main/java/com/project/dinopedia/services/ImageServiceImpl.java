package com.project.dinopedia.services;

import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.Image;
import com.project.dinopedia.exceptions.BadRequestException;
import com.project.dinopedia.repositories.DinosaurRepository;
import com.project.dinopedia.repositories.ImageRepository;
import com.project.dinopedia.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private static final int IMAGES_MAX_NUM = 2;

    private ImageRepository imageRepository;
    private DinosaurRepository dinosaurRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, DinosaurRepository dinosaurRepository) {
        this.imageRepository = imageRepository;
        this.dinosaurRepository = dinosaurRepository;
    }

    @Transactional
    @Override
    public void addImagesToDinosaur(Long dinoId, List<MultipartFile> files) {

        Dinosaur dinosaur = dinosaurRepository.findById(dinoId)
                .orElseThrow(() -> new BadRequestException("Dinosaur with id " + dinoId + " does not exist."));

        if (files.size() > IMAGES_MAX_NUM) throw new BadRequestException("Maximum number of files should be 2.");

        if (dinosaur.getImages().size() == IMAGES_MAX_NUM) throw new BadRequestException("This dinosaur has already " +
                "2 images.");

        if (dinosaur.getImages().size() + files.size() > IMAGES_MAX_NUM)
            throw new BadRequestException("This dinosaur has already 1 image and can accept only 1 more.");

        List<Image> imagesToAdd = Utils.buildImages(files);
        imageRepository.saveAll(imagesToAdd
                .stream()
                .peek(image -> image.setDinosaur(dinosaur))
                .toList());

        log.info("Images were successfully saved.");
    }

    @Override
    public void delete(Long id) {

        Image image = imageRepository.findById(id).orElseThrow(() -> new BadRequestException("Image was not found."));

        imageRepository.delete(image);
        log.info("Image with id " + id + " was deleted.");
    }
}
