package com.example.studentmanagement.exceptions;

public class NoGradesException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "There are no grades available";

    public NoGradesException() {
        super(DEFAULT_MESSAGE);
    }
}
