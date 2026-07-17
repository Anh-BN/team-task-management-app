package com.teamtaskmanagement.dto.response;

import java.util.List;

public class ErrorResponse {
    private int status;
    private String message;
    private List<FieldErrorResponse> errors;

    public ErrorResponse(int status, String message, List<FieldErrorResponse> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public List<FieldErrorResponse> getErrors() { return errors; }
}
