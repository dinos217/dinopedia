package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.Image;
import com.project.dinopedia.exceptions.BadRequestException;
import com.project.dinopedia.mappers.DinosaurMapper;
import com.project.dinopedia.repositories.DinosaurRepository;
import com.project.dinopedia.repositories.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DinosaurServiceImpl implements DinosaurService {

    public static final int IMAGES_MAX_NUM = 2;
    private DinosaurRepository dinosaurRepository;
    private ImageRepository imageRepository;
    private DinosaurMapper dinosaurMapper = Mappers.getMapper(DinosaurMapper.class);

    @Autowired
    public DinosaurServiceImpl(DinosaurRepository dinosaurRepository, ImageRepository imageRepository) {
        this.dinosaurRepository = dinosaurRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    @Override
    public DinosaurDto save(DinosaurRequestDto dinosaurRequestDto) {

        Dinosaur dinosaur = dinosaurMapper.dinosaurRequestDtoToDinosaur(dinosaurRequestDto);

        if (dinosaurRequestDto.getImages().size() > IMAGES_MAX_NUM)
            throw new BadRequestException("Maximum number of files is 2");

        dinosaur.setImages(buildImages(dinosaurRequestDto.getImages()));

        log.info("New Dinosaur " + dinosaur.getName() + " was saved successfully");
        return dinosaurMapper.dinosaurToDinosaurDto(dinosaurRepository.save(dinosaur));
    }


    @Transactional
    @Override
    public DinosaurDto update(DinosaurDto dinosaurDto) {

        Dinosaur dinosaur = dinosaurRepository.findById(dinosaurDto.getId())
                .orElseThrow(() -> new BadRequestException("Dinosaur not found"));

        dinosaur.setName(dinosaurDto.getName());
        dinosaur.setSize(dinosaur.getSize());
        dinosaur.setColour(dinosaur.getColour());
        dinosaur.setPeriod(dinosaur.getPeriod());
        dinosaur.setEatingClass(dinosaur.getEatingClass());

        return dinosaurMapper.dinosaurToDinosaurDto(dinosaurRepository.save(dinosaur));
    }

    @Override
    public void delete(Long id) {

        Dinosaur dinosaur = dinosaurRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Dinosaur not found"));
        dinosaurRepository.delete(dinosaur);
        log.info("Dinosaur " + dinosaur.getName() + " was deleted successfully.");
    }

    @Override
    public Page<DinosaurDto> findAll(Pageable pageable) {

        Page<Dinosaur> allDinosaurs = dinosaurRepository.findAll(pageable);

        return buildResponseListPaged(pageable, allDinosaurs);
    }

    @Transactional
    @Override
    public void addImageToDinosaur(Long dinoId, List<MultipartFile> files) {

        Dinosaur dinosaur = dinosaurRepository.findById(dinoId)
                .orElseThrow(() -> new BadRequestException("Dinosaur with id " + dinoId + " does not exist."));

        if (files.size() > IMAGES_MAX_NUM) throw new BadRequestException("Maximum number of files is 2");

        if (dinosaur.getImages().size() == IMAGES_MAX_NUM) throw new BadRequestException("This dinosaur has already " +
                "2 images.");

        if (dinosaur.getImages().size() + files.size() > IMAGES_MAX_NUM)
            throw new BadRequestException("This dinosaur has already 1 image and can accept only 1 more.");

        List<Image> imagesToAdd = buildImages(files);
        imageRepository.saveAll(imagesToAdd
                .stream()
                .peek(image -> image.setDinosaur(dinosaur))
                .toList());
    }

    @Override
    public void removeImage(Long id) {
        //todo: move this to image service etc..
    }

    @Override
    public void removeAllImagesOfDinosaur(Long dinosaurId) {

    }

    private List<Image> buildImages(List<MultipartFile> files) {

        if (!CollectionUtils.isEmpty(files)) {
            List<Image> dinoImages = new ArrayList<>();
            for (MultipartFile file : files) {
                Image image = new Image();
//                image.setDinosaur(dinosaur);
                image.setName(file.getName());
                image.setType(file.getContentType());
                try {
                    image.setImageData(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                dinoImages.add(image);
            }
            return dinoImages;
        } else
            throw new BadRequestException("List of images is empty.");
    }

    private Page<DinosaurDto> buildResponseListPaged(Pageable pageable, Page<Dinosaur> dinosaursFromDb) {

        long total = 0L;
        if (!ObjectUtils.isEmpty(dinosaursFromDb)) total = dinosaursFromDb.stream().count();

        List<DinosaurDto> dinosaurs = dinosaursFromDb.stream()
                .map(dinosaur -> dinosaurMapper.dinosaurToDinosaurDto(dinosaur))
                .toList();

        log.info("Found all dinosaurs successfully.");
        return new PageImpl<>(dinosaurs, pageable, total);
    }
}
