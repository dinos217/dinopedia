package com.project.dinopedia.dtos;

import com.project.dinopedia.entities.DinosaurPicture;
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
    private String colour;
    private String period;
    private String size;
    private List<DinosaurPicture> pictures;
    private List<Vote> likes;
}
