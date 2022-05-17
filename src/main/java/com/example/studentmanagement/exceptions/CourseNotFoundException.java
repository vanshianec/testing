package com.example.studentmanagement.exceptions;

public class CourseNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "There is no such course";

    public CourseNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}