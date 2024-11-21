package com.i2i.ems.exception;

public class EmployeeManagementException extends RuntimeException {

    public EmployeeManagementException() {
        super();
    }

    public EmployeeManagementException(Throwable cause) {
        super(cause);
    }

    public EmployeeManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeManagementException(String message) {
        super(message);
    }
}