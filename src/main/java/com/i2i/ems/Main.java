package com.i2i.ems;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.controller.DepartmentController;
import com.i2i.ems.controller.EmployeeController;
import com.i2i.ems.controller.ProjectController;

import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Certification;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import com.i2i.ems.model.Project;

import com.i2i.ems.service.AddressService;
import com.i2i.ems.service.CertificationService;
import com.i2i.ems.service.DepartmentService;
import com.i2i.ems.service.EmployeeService;
import com.i2i.ems.service.ProjectService;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static DepartmentController departmentController = new DepartmentController();
    private static EmployeeController employeeController = new EmployeeController();
    private static ProjectController projectController = new ProjectController();

    private static AddressService addressService = new AddressService();
    private static EmployeeService employeeService = new EmployeeService();
    private static CertificationService certificationService = new CertificationService();
    private static DepartmentService departmentService = new DepartmentService();
    private static ProjectService projectService = new ProjectService();

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Application Started");
        String choice;
        do {
            System.out.print("\n----- MAIN MENU -----"
                                + "\n1. Create"
                                + "\n2. Read"
                                + "\n3. Update"
                                + "\n4. Remove Employee"
                                + "\n5. Exit" + "\nEnter your option: ");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    logger.debug("Calling create method from main after successful input - 1 from user.");
                    create();
                    break;
                case "2":
                    logger.debug("Calling read method from main after successful input - 2 from user.");
                    read();
                    break;
                case "3":
                    logger.debug("Calling update method from main after successful input - 3 from user.");
                    update();
                    break;
                case "4":
                    logger.debug("Calling removeEmployee from main method after successful input - 4 from user.");
                    employeeController.removeEmployee();
                    break;
                case "5":
                    logger.debug("Exiting from main after successful input - 5 from user.");
                    System.out.println("\n\t Exiting...");
                    break;
                default:
                    logger.debug("Recived Invalid option from the user. Continuing to Main Menu.");
                    System.out.println("Invalid Option, Please Enter the correct option from Menu.");
                    break;
            } 
        } while (!choice.equals("5"));
    }

    public static void create() {
        logger.debug("Enterd into create() method.");
        String option;
        System.out.print("\n1. Create Department"
                                + "\n2. Create Project"
                                + "\n3. Create Employee"
                                + "\n4. Exit to Main Menu" + "\n Enter your option: ");
        option = scanner.nextLine();
        switch (option) {
            case "1":
                logger.debug("Calling createDepartment from create after successful input 1 from user");
                departmentController.createDepartment();
                break;
            case "2":
                logger.debug("Calling createProject from create after successful input 2 from user");
                projectController.createProject();
                break;
            case "3":
                logger.debug("Calling createEmployee from create after successful input 3 from user");
                employeeController.createEmployee();
                break;
            case "4":
                logger.debug("Calling createDepartment from create after successful input 4 from user");
                System.out.println("Exiting to main menu");
                break;
            default:
                logger.debug("Invalid option received as input from create. Returning to main menu");
                System.out.println("Invalid Option, Try again");
        }
    }

    public static void read() {
        logger.debug("Entered into read functoin from main menu");
        String option;
        System.out.print("\n1. Read Department"
                                + "\n2. Read Project"
                                + "\n3. Read Employee"
                                + "\n4. Read Certification"
                                + "\n5. Exit to Main Menu" + "\n Enter your option: ");
        option = scanner.nextLine();
        switch (option) {
            case "1":
                logger.debug("Calling readDepartment from read after successful input 1 from user");
                departmentController.readDepartment();
                break;
            case "2":
                logger.debug("Calling readProject from read after successful input 2 from user");
                projectController.readProject();
                break;
            case "3":
                logger.debug("Calling readEmployee from read after successful input 3 from user");
                employeeController.readEmployee();
                break;
            case "4":
                logger.debug("Calling readCertifications from read after successful input 4 from user");
                employeeController.readCertifications();
                break;
            case "5":
                logger.debug("Received input 5 from user. Exiting to main menu");
                System.out.println("Exiting to main menu");
                break;
            default:
                logger.debug("Invalid Option received. Returning to main menu.");
                System.out.println("Invalid Option, Try again");
        }
    }

    public static void update() {
        logger.debug("Entered into update method from main menu");
        String option;
        System.out.print("\n1. Update Project Status"
                                + "\n2. Update Employee"
                                + "\n3. Exit to Main Menu" + "\n Enter your option: ");
        option = scanner.nextLine();
        switch (option) {
            case "1":
                logger.debug("Calling updateDepartment from update after successful input 1 from user");
                projectController.updateProject();
                break;
            case "2":
                logger.debug("Calling updateEmployee from update after successful input 1 from user");
                employeeController.updateEmployee();
                break;
            case "3":
                logger.debug("Exiting to main memu from update after succcessful input 3 from user.");
                System.out.println("Exiting to main menu");
                break;
            default:
                logger.debug("Invalid option received. Returning to main menu");
                System.out.println("Invalid Option, Try again");
        }
    }
}