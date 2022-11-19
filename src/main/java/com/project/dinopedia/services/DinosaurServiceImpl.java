package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.enums.EatingClass;
import com.project.dinopedia.enums.Size;
import com.project.dinopedia.exceptions.BadRequestException;
import com.project.dinopedia.mappers.DinosaurMapper;
import com.project.dinopedia.repositories.DinosaurRepository;
import com.project.dinopedia.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DinosaurServiceImpl implements DinosaurService {

    private static final int IMAGES_MAX_NUM = 2;
    private DinosaurRepository dinosaurRepository;
    private DinosaurMapper dinosaurMapper = Mappers.getMapper(DinosaurMapper.class);

    @Autowired
    public DinosaurServiceImpl(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }

    @Transactional
    @Override
    public DinosaurDto save(DinosaurRequestDto dinosaurRequestDto) {

        Dinosaur dinosaur = dinosaurMapper.dinosaurRequestDtoToDinosaur(dinosaurRequestDto);

        if (dinosaurRequestDto.getImages().size() > IMAGES_MAX_NUM)
            throw new BadRequestException("Maximum number of files is 2");

        dinosaur.setImages(Utils.buildImages(dinosaurRequestDto.getImages()));
        DinosaurDto dinosaurDto = dinosaurMapper.dinosaurToDinosaurDto(dinosaurRepository.save(dinosaur));
        log.info("New Dinosaur " + dinosaur.getName() + " was saved successfully");
        return dinosaurDto;
    }


    @Transactional
    @Override
    public DinosaurDto update(DinosaurDto dinosaurDto) {

        Dinosaur dinosaur = dinosaurRepository.findById(dinosaurDto.getId())
                .orElseThrow(() -> new BadRequestException("Dinosaur not found"));

        dinosaur.setName(dinosaurDto.getName());
        dinosaur.setSize(dinosaurDto.getSize());
        dinosaur.setColour(dinosaurDto.getColour());
        dinosaur.setPeriod(dinosaurDto.getPeriod());
        dinosaur.setEatingClass(dinosaurDto.getEatingClass());

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

    @Override
    public List<byte[]> getDinosaurImages(String name) {

        Dinosaur dinosaur = dinosaurRepository.findByName(name)
                .orElseThrow(() -> new BadRequestException("Dinosaur not found."));

        if (!CollectionUtils.isEmpty(dinosaur.getImages())) {
            return dinosaur.getImages().stream()
                    .map(image -> Utils.decompressImage(image.getImageData()))
                    .collect(Collectors.toList());
        } else
            throw new BadRequestException("Oops! This dinosaur has no associated images.");
    }

    @Override
    public List<String> getDinosaurEatingClasses() {
        return Arrays.stream(EatingClass.values())
                .map(EatingClass::getLabel)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDinosaurSizes() {
        return Arrays.stream(Size.values())
                .map(Size::getLabel)
                .collect(Collectors.toList());
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
