package br.com.gabrieudev.picpay.application.exceptions;

import java.time.LocalDateTime;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(400, this.getMessage(), LocalDateTime.now());
    }
}
