package com.i2i.ems.exception;

import com.i2i.ems.exception.EmployeeManagementException;

public class NoSuchDepartmentException extends EmployeeManagementException {

    public NoSuchDepartmentException() {
        super();
    }

    public NoSuchDepartmentException(Throwable cause) {
        super(cause);
    }

    public NoSuchDepartmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchDepartmentException(String message) {
        super(message);
    }

}