package com.example.studentmanagement.exceptions;

public class InvalidGradeException extends RuntimeException{
    public InvalidGradeException(String message) {
        super(message);
    }
}
