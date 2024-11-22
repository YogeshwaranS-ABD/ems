package com.i2i.ems.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.dao.DepartmentDao;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import com.i2i.ems.utils.Util;

public class DepartmentService {

    private DepartmentDao departmentDao = new DepartmentDao();
    private static Logger logger = LogManager.getLogger(DepartmentService.class);

    private String generateIdForDepartment() {
        return "DEPT" + Util.generateShortId();
    }

    public boolean isDepartmentAvailable(String id) throws EmployeeManagementException {
        logger.debug("Enterd into method: isDepartmentAvailable to check if id: {} exists or not", id);
        boolean result = departmentDao.isDepartmentAvailable(id.toUpperCase());
        logger.info("Department found with ID: {}", id);
        return result;
    }

    public Department createDepartment(String name) throws EmployeeManagementException {
        logger.debug("Entered into createDepartment to create a department with name: {}", name);
        String id = generateIdForDepartment();
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setCreatedOn(LocalDateTime.now().toString());
        departmentDao.addDepartment(department);
        logger.info("New Department, {} - {} was added to the record successfully", id, name);
        return department;
    }

    public Department getDepartmentWithId(String id) throws EmployeeManagementException {
        return departmentDao.getDepartmentWithId(id.toUpperCase());
    }

    public List<Department> getAllDepartments() throws EmployeeManagementException {
        return departmentDao.getAllDepartments();
    }
}