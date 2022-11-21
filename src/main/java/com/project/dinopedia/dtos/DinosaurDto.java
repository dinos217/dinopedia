package com.project.dinopedia.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DinosaurDto {

    private Long id;
    private String name;
    private String eatingClass;
    private String period;
    private String size;
}
