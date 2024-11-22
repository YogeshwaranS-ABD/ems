package com.i2i.ems.controller;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Department;
import com.i2i.ems.service.DepartmentService;

public class DepartmentController {

    private static Scanner scanner = new Scanner(System.in);
    private static DepartmentService departmentService = new DepartmentService();
    private static Logger logger = LogManager.getLogger(DepartmentController.class);

    public static void createDepartment() {
        logger.debug("Enterd into createDepartment from create method");
        System.out.print("\nEnter the Department Name : ");
        String name = scanner.nextLine();
        try {
            logger.debug("Calling DepartmentService.creteDepertment to create a department with name: {}", name);
            Department department = departmentService.createDepartment(name);
            System.out.println("Department: " + name + " created succesfully with ID - " + department.getId());
            logger.info("Department created Successfuly\n{}", department);
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void readDepartment() {
        logger.debug("Entered into readDepartmentment from read method");
        System.out.print("\n1. Read All Departments"
                            + "\n2. Read Department With ID"
                            + "\nEnter your Option: ");
        String option = scanner.nextLine();
        switch(option) {
            case "1":
                readAllDepartments();
                break;
            case "2":
                readDepartmentWithId();
                break;
            default:
                logger.debug("Invalid option received as input from create. Returning to main menu");
                System.out.println("Invalid Option, Try again");
        }
    }

    private static void readAllDepartments() {
        logger.debug("Entered into readAllDepartments to list all available departments");
        try {
            List<Department> departments = departmentService.getAllDepartments();
            printAsList(departments);
            logger.info("Departments List displayed successfully");
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void readDepartmentWithId() {
        System.out.println("\nEnter the deparment Id to read: ");
        String id = scanner.nextLine();
        try {
            departmentService.isDepartmentAvailable(id);
            System.out.println(departmentService.getDepartmentWithId(id));
            logger.info("The Department with id: {}, was read successfully", id);
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void printAsList(List<Department> departments) {
        for(Department department: departments) {
            System.out.println(department.getNameWithId());
        }
    }
}