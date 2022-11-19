package com.project.dinopedia.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteDto {

    private Long id;
    private String username;
    private String dinosaurName;
    private Boolean like;
}
