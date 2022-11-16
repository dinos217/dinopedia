package com.project.dinopedia.dtos;

import com.project.dinopedia.entities.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DinosaurRequestDto {

    private String name;
    private String eatingClass;
    private String colour;
    private String period;
    private String size;
    private List<Image> images;
}
