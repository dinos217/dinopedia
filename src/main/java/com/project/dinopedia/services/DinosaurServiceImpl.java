package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import com.project.dinopedia.repositories.DinosaurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class DinosaurServiceImpl implements DinosaurService {

    private DinosaurRepository dinosaurRepository;

    @Autowired
    DinosaurServiceImpl(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }

    @Transactional
    @Override
    public DinosaurDto save(DinosaurRequestDto dinosaurRequestDto) {
        return null;
    }

    @Transactional
    @Override
    public DinosaurDto update(DinosaurRequestDto dinosaurRequestDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<DinosaurDto> findAll(Pageable pageable) {
        return null;
    }
}
