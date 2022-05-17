package com.example.studentmanagement.exceptions;

public class TeacherNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "There is no such teacher";

    public TeacherNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
