package com.Mythili.Splitwise.Exception;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException() {
    }

    public GroupNotFoundException(String message) {
        super(message);
    }
}
