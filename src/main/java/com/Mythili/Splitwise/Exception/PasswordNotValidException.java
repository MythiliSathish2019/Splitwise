package com.Mythili.Splitwise.Exception;

public class PasswordNotValidException extends RuntimeException{
    public PasswordNotValidException() {
    }

    public PasswordNotValidException(String message) {
        super(message);
    }
}
