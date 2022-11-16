package com.project.dinopedia.mappers;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import com.project.dinopedia.entities.Dinosaur;
import org.mapstruct.Mapper;

@Mapper
public interface DinosaurMapper {

    DinosaurDto dinosaurToDinosaurDto(Dinosaur dinosaur);
    Dinosaur dinosaurDtoToDinosaur(DinosaurDto dinosaurDto);
    Dinosaur dinosaurRequestDtoToDinosaur(DinosaurRequestDto dinosaurRequestDto);
}
