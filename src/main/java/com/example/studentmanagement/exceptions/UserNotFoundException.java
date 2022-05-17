package com.example.studentmanagement.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "There is no such user";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
