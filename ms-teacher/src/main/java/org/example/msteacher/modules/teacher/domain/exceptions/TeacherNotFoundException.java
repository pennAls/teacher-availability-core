package org.example.msteacher.modules.teacher.domain.exceptions;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}