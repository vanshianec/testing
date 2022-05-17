package com.example.studentmanagement.web.controllers;

import com.example.studentmanagement.exceptions.CourseNotFoundException;
import com.example.studentmanagement.exceptions.InvalidGradeException;
import com.example.studentmanagement.exceptions.NoGradesException;
import com.example.studentmanagement.exceptions.RecordAlreadyExistsException;
import com.example.studentmanagement.exceptions.StudentNotEnrolledForCourseException;
import com.example.studentmanagement.exceptions.StudentNotFoundException;
import com.example.studentmanagement.exceptions.TeacherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleAllExceptionsController {

    @ExceptionHandler({StudentNotFoundException.class, TeacherNotFoundException.class,
            CourseNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundStatusExceptions(Throwable ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({StudentNotEnrolledForCourseException.class, RecordAlreadyExistsException.class})
    public ResponseEntity<Object> handleConflictStatusExceptions(Throwable ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoGradesException.class})
    public ResponseEntity<Object> handleNoContentStatusExceptions(Throwable ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({InvalidGradeException.class})
    public ResponseEntity<Object> handleRangeNotSatisfiableStatusExceptions(Throwable ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    }
}
