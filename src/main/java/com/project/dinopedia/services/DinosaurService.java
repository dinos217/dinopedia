package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface DinosaurService {

    DinosaurDto save(DinosaurRequestDto dinosaurRequestDto);

    DinosaurDto update(DinosaurRequestDto dinosaurRequestDto);

    void delete(Long id);

    Page<DinosaurDto> findAll(Pageable pageable);



}
