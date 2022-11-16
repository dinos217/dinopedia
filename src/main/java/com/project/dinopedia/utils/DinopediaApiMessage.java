package com.project.dinopedia.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DinopediaApiMessage {

    private HttpStatus status;
    private int statusCode;
    private String message;
}
