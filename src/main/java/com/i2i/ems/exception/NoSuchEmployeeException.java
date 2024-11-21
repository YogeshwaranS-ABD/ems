package com.i2i.ems.exception;

import com.i2i.ems.exception.EmployeeManagementException;

public class NoSuchEmployeeException extends EmployeeManagementException {

    public NoSuchEmployeeException() {
        super();
    }

    public NoSuchEmployeeException(Throwable cause) {
        super(cause);
    }

    public NoSuchEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEmployeeException(String message) {
        super(message);
    }

}