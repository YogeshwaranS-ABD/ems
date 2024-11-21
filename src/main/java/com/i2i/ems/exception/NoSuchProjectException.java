package com.i2i.ems.exception;

import com.i2i.ems.exception.EmployeeManagementException;

public class NoSuchProjectException extends EmployeeManagementException {

    public NoSuchProjectException() {
        super();
    }

    public NoSuchProjectException(Throwable cause) {
        super(cause);
    }

    public NoSuchProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchProjectException(String message) {
        super(message);
    }

}