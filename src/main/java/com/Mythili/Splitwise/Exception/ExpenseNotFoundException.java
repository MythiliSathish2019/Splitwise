package com.Mythili.Splitwise.Exception;

public class ExpenseNotFoundException extends RuntimeException{
    public ExpenseNotFoundException() {
    }

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
