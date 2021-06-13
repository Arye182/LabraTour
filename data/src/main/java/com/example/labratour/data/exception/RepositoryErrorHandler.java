package com.example.labratour.data.exception;


import com.example.labratour.domain.exception.ErrorHandler;

public class RepositoryErrorHandler implements ErrorHandler {
    private final Exception exception;

    RepositoryErrorHandler(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        String message = "";
        if (this.exception != null) {
            message = this.exception.getMessage();
        }
        return message;
    }
}
