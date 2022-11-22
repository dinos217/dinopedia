package com.project.dinopedia.exceptions;

import com.project.dinopedia.utils.DinopediaApiMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class DinopediaExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<DinopediaApiMessage> processValidationError(InvalidRequestException e) {

        log.warn("CONFLICT --> " + e.getMessage());

        DinopediaApiMessage response = buildDinopediaErrorMessage(e, HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<DinopediaApiMessage> processValidationError(BadRequestException e) {

        log.warn("ERROR --> " + e.getMessage());

        DinopediaApiMessage response = buildDinopediaErrorMessage(e, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private DinopediaApiMessage buildDinopediaErrorMessage(RuntimeException e, HttpStatus status) {
        DinopediaApiMessage response = new DinopediaApiMessage();
        response.setStatus(status);
        response.setStatusCode(status.value());
        response.setMessage(e.getMessage());
        return response;
    }

}
