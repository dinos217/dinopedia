package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.exceptions.BadRequestException;
import com.project.dinopedia.exceptions.InvalidRequestException;
import com.project.dinopedia.mappers.DinosaurMapper;
import com.project.dinopedia.repositories.DinosaurRepository;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DinosaurServiceImpl implements DinosaurService {

    private DinosaurRepository dinosaurRepository;
    private DinosaurMapper dinosaurMapper = Mappers.getMapper(DinosaurMapper.class);

    @Autowired
    DinosaurServiceImpl(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }

    @Transactional
    @Override
    public DinosaurDto save(DinosaurRequestDto dinosaurRequestDto) {

        Dinosaur dinosaur = dinosaurMapper.dinosaurRequestDtoToDinosaur(dinosaurRequestDto);

        if (!CollectionUtils.isEmpty(dinosaurRequestDto.getImages())) {
            //todo: check if I should use ImageDtos in DinosaurDtos
        }

        return dinosaurMapper.dinosaurToDinosaurDto(dinosaurRepository.save(dinosaur));
    }

    @Transactional
    @Override
    public DinosaurDto update(DinosaurRequestDto dinosaurRequestDto) {
        return null;
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
