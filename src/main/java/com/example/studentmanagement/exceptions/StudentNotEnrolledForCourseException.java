package com.example.studentmanagement.exceptions;

public class StudentNotEnrolledForCourseException extends RuntimeException {
    public StudentNotEnrolledForCourseException(String message) {
        super(message);
    }
}
