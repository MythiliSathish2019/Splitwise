package com.Mythili.Splitwise.Exception;

public class UserExpenseNotFoundException extends RuntimeException{
    public UserExpenseNotFoundException() {
    }

    public UserExpenseNotFoundException(String message) {
        super(message);
    }
}
