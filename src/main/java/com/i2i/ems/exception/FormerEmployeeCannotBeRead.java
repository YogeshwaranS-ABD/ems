package com.i2i.ems.exception;

import com.i2i.ems.exception.EmployeeManagementException;

public class FormerEmployeeCannotBeRead extends EmployeeManagementException {

    public FormerEmployeeCannotBeRead() {
        super();
    }

    public FormerEmployeeCannotBeRead(Throwable cause) {
        super(cause);
    }

    public FormerEmployeeCannotBeRead(String message, Throwable cause) {
        super(message, cause);
    }

    public FormerEmployeeCannotBeRead(String message) {
        super(message);
    }

}