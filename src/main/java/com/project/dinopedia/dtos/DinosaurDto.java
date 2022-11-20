package com.project.dinopedia.dtos;

import com.project.dinopedia.entities.Image;
import com.project.dinopedia.entities.Vote;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DinosaurDto {

    private Long id;
    private String name;
    private String eatingClass;
    private String period;
    private String size;
    private List<Image> images;
    private List<Vote> likes;
}
