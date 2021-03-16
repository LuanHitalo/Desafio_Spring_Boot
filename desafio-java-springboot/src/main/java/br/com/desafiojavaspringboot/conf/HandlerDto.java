package br.com.desafiojavaspringboot.conf;

import org.springframework.web.bind.annotation.RestControllerAdvice;

public class HandlerDto {

    private int status_code;
    private String message;

    public HandlerDto(int status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public String getMessage() {
        return message;
    }
}
