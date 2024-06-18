package com.Mythili.Splitwise.Exception;

public class InvalidUserEmailException extends RuntimeException{
    public InvalidUserEmailException() {
    }

    public InvalidUserEmailException(String message) {
        super(message);
    }

}
