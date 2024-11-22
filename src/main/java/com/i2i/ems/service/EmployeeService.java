package com.i2i.ems.service;

import java.time.LocalDateTime;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.dao.EmployeeDao;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import com.i2i.ems.model.Project;
import com.i2i.ems.service.AddressService;
import com.i2i.ems.utils.Util;

public class EmployeeService {

    private static AddressService addressService = new AddressService();
    private static EmployeeDao employeeDao = new EmployeeDao();
    private static Logger logger = LogManager.getLogger(EmployeeService.class);

    private String generateIdForEmployee() {
        return "I2I" + Util.generateShortId();
    }

    public Employee addEmployee(String name, String email, String doj, String role, String[] addr, Department department)
                     throws EmployeeManagementException {
        logger.debug("Entered into addEmployee to add new Employee to the record with name: {}, under department: {}", name, department);
        Employee employee = new Employee();
        String id = generateIdForEmployee();
        employee.setId(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setDoj(doj);
        employee.setRole(role);
        employee.setDepartment(department);
        employee.setCreatedOn(LocalDateTime.now().toString());
        employeeDao.addEmployee(employee);
        Address address = addressService.createAddress(addr,employee);
        employee.setAddress(address);
        logger.info("New Employee added to the record successfully with ID: {}", id);
        return employee;
    }

    public void updateEmployee(Employee employee) throws EmployeeManagementException {
        logger.debug("Entered into updateEmployee to update - {}", employee.getNameWithId());
        employeeDao.updateEmployee(employee);
        logger.info("{} - Updated Successfully", employee.getNameWithId());
    }

    public boolean isIdAvailable(String id) throws EmployeeManagementException {
        logger.debug("Entered into isIdAvailable to check the id: {} is available in the record", id);
        boolean result = employeeDao.isIdAvailable(id.toUpperCase());
        logger.info("The given id: {} was matched with an entry in the record", id);
        return result;
    }

    public Employee getEmployeeWithId(String id) throws EmployeeManagementException {
        logger.debug("Entered into getEmployeeWithId to retrieve information about employee with id: {}", id);
        Employee employee = employeeDao.getEmployeeWithId(id.toUpperCase());
        logger.info("The information about employee with ID: {} was retrieved successfully", id);
        return employee;
    }

    public void removeEmployee(String id) throws EmployeeManagementException {
        logger.debug("Entered into removeEmployee to remove information about the employee with id: {}", id);
        employeeDao.removeEmployee(id.toUpperCase());
        logger.info("The information about employee with ID: {} was removed successfully", id);
    }
}