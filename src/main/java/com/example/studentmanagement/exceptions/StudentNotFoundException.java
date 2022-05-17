package com.example.studentmanagement.exceptions;

public class StudentNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "There is no such student";

    public StudentNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
