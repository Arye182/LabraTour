package com.example.labratour.domain.exception;

public interface ErrorHandler {
    Exception getException();
    String getErrorMessage();
}
