package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface DinosaurService {

    DinosaurDto save(DinosaurRequestDto dinosaurRequestDto);

    DinosaurDto update(DinosaurDto dinosaurDto);

    void delete(Long id);

    Page<DinosaurDto> findAll(Pageable pageable);

    void addImageToDinosaur(Long id, List<MultipartFile> files);

    void removeImage(Long id);

    void removeAllImagesOfDinosaur(Long dinosaurId);
}
