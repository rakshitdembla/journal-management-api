package com.rakshitdembla.JournalApp.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppException extends RuntimeException{
    private int status;

    public AppException(int status, String message) {
        super(message);
        this.status = status;
    }
}
