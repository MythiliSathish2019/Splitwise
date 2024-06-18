package com.Mythili.Splitwise.Exception;

public class PasswordLengthShortException extends RuntimeException{
    public PasswordLengthShortException() {
    }

    public PasswordLengthShortException(String message) {
        super(message);
    }
}
