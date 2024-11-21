package com.i2i.ems.controller;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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

public class EmployeeController {

    private static Scanner scanner = new Scanner(System.in);
    private static AddressService addressService = new AddressService();
    private static CertificationService certificationService = new CertificationService();
    private static DepartmentService departmentService = new DepartmentService();
    private static ProjectService projectService = new ProjectService();
    private static EmployeeService employeeService = new EmployeeService();

    private static Logger logger = LogManager.getLogger(EmployeeController.class);

    public static void createEmployee() {
        logger.debug("Entered into createEmployee from create funciton");
        try {
            System.out.print("Name : ");
            String name = scanner.nextLine();
            System.out.print("Email : ");
            String email = scanner.nextLine();
            System.out.print("Date Joined (yyyy-mm-dd): ");
            String doj = scanner.nextLine();
            doj = getValidDate(doj);
            System.out.print("Role: ");
            String role = scanner.nextLine();
            System.out.println("Enter the Address Details:");
            String[] address = new String[5];
            System.out.print("Door No.: ");
            address[0] = scanner.nextLine();
            System.out.print("Street Name: ");
            address[1] = scanner.nextLine();
            System.out.print("Area: ");
            address[2] = scanner.nextLine();
            System.out.print("City: ");
            address[3] = scanner.nextLine();
            System.out.print("Pin Code: ");
            address[4] = scanner.nextLine();
            System.out.print("Department Id: ");
            String id = scanner.nextLine();
            Department department = getValidDepartment(id);
            System.out.print("Project Id: ");
            id = scanner.nextLine();
            Project project = getValidProject(id);
            Employee employee = employeeService.addEmployee(name, email, doj, role, address, department);
            employee.addProject(project);
            
            System.out.println("\nCertification Details:\nEnter the number of certifications you have: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            getCertifications(employee, num);
            System.out.println("Employee created Successfully with the ID: " + employee.getId());
            logger.info("A new Employee created successfully with id: " + employee.getId()
                        + " with " + num + " certifications"
                        + "\n\tUnder department: " + department.getName()
                        + "\n\tCurrent project: " + project.getName());
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void readEmployee() {
        logger.debug("Entered into readEmployee from read method.");
        try {
            System.out.print("Enter the Employee ID : ");
            String id = scanner.nextLine();
            employeeService.isIdAvailable(id);
            Employee employee = employeeService.getEmployeeWithId(id);
            System.out.println(employee);
            logger.info("Employee with id: " + id + " was displayed successfully from the record");
        } catch (EmployeeManagementException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    public static void updateEmployee() {
        logger.debug("Enterd into updateEmployee from update method");
        try {
            System.out.print("Enter the ID of the Employee : ");
            String id = scanner.nextLine();
            employeeService.isIdAvailable(id);
            Employee employee = employeeService.getEmployeeWithId(id);
            System.out.println("Current Details:\n" + employee);
            logger.debug("Employee record with given ID exist and displayed to user");
            System.out.print("\n1. Change Department"
                                + "\n2. Add Another Project"
                                + "\n3. Change Email"
                                + "\n4. Change Role"
                                + "\n5. Add New Certification"
                                + "\n6. Update Existing Certification's Expiry Date"
                                + "\n7. Change Address"
                                + "\n8. Update Date of Joining"
                                + "\n9. Exit to Main Menu" + "\nEnter your option: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    logger.debug("Trying to update the department of the employee with id: " + employee.getId());
                    System.out.print("Enter the ID of the new Department : ");
                    String departmentId = scanner.nextLine();
                    departmentService.isDepartmentAvailable(departmentId);
                    Department department = departmentService.getDepartmentWithId(departmentId);
                    employee.setDepartment(department);
                    employeeService.updateEmployee(employee);
                    logger.info("Department of " + employee.getName() + " changed to " + department.getName() + " successfully.");
                    break;
                case "2":
                    logger.debug("Trying to add another project to the employee with id: " + employee.getId());
                    System.out.print("Enter the ID of the Project to be added : ");
                    String projectId = scanner.nextLine();
                    projectService.isProjectAvailable(projectId);
                    Project project = projectService.getProjectWithId(projectId);
                    employee.addProject(project);
                    employeeService.updateEmployee(employee);
                    logger.info("Employee " + employee.getName() + ", added to another Project: " + project.getName() + " successfully.");
                    break;
                case "3":
                    logger.debug("Trying to update Email of the Employee with ID: " + id);
                    System.out.print("Enter the new Email: ");
                    String email = scanner.nextLine();
                    employee.setEmail(email);
                    employeeService.updateEmployee(employee);
                    logger.info("Email updated Successfully for Employee with ID: " + id);
                    break;
                case "4":
                    logger.debug("Trying to update Role of the Employee with ID: " + id);
                    System.out.print("Enter the new Role: ");
                    String role = scanner.nextLine();
                    employee.setRole(role);
                    employeeService.updateEmployee(employee);
                    logger.info("Role updated Successfully for Employee with ID: " + id);
                    break;
                case "5":
                    logger.debug("Trying to add new Certification to the Employee with ID: " + id);
                    System.out.print("Enter the new Certification details: ");
                    System.out.print("Certification Name: ");
                    String certificateName = scanner.nextLine();
                    System.out.print("Certification Provider: ");
                    String provider = scanner.nextLine();
                    System.out.print("Certification level (Foundation / Associate / Expert): ");
                    String level = scanner.nextLine();
                    System.out.print("Expiry date (yyyy-mm-dd): ");
                    String expiryDate = scanner.nextLine();
                    expiryDate = getValidExpiryDate(expiryDate);
                    Certification certificate = certificationService.createCertification(certificateName, provider, expiryDate, level, employee);
                    System.out.println("Certification added successfully with the ID: " + certificate.getId());
                    logger.info("Certification " + id + " - " + certificateName + " was added to Employee with ID: " + id + " successfully.");
                    break;
                case "6":
                    System.out.println("Certification List:");
                    printCertificationsByEmployee(employee);
                    System.out.print("Enter the certificate ID to which Expiry Date need to be updated: ");
                    String certificateId = scanner.nextLine();
                    updateCertificationExpiryDate(certificateId);
                    logger.info("Update successful for certification Id: {}", certificateId);
                    break;
                case "7":
                    logger.debug("Trying to update Address of the Employee with ID: " + id);
                    System.out.println("Enter the new Address Details:");
                    System.out.print("New Door Number: ");
                    String doorNo = scanner.nextLine();
                    System.out.print("New Street Name: ");
                    String street = scanner.nextLine();
                    System.out.print("New Area: ");
                    String area = scanner.nextLine();
                    System.out.print("New City: ");
                    String city = scanner.nextLine();
                    System.out.print("New Pin Code: ");
                    String pinCode = scanner.nextLine();
                    if (employee.getAddress() == null) {
                        logger.debug("Address for employee with id: {} found to be null. creating new address.", employee.getId());
                        Address newAddress = addressService.createAddress(new String[]{doorNo, street, area, city, pinCode}, employee);
                        employee.setAddress(newAddress);
                    } else {
                        employee.getAddress().setDoorNo(doorNo);
                        employee.getAddress().setStreet(street);
                        employee.getAddress().setArea(area);
                        employee.getAddress().setCity(city);
                        employee.getAddress().setPinCode(pinCode);
                    }
//                    addressService.update(employee.getAddress());
                    logger.info("Address of the Employee with ID: " + id + " was updated successfully.");
                    break;
                case "8":
                    System.out.println("Enter the correct DOJ in format: yyyy-mm-dd: ");
                    String date = scanner.nextLine();
                    date = getValidDate(date);
                    employee.setDoj(date);
                    employeeService.updateEmployee(employee);
                    break;
                case "9":
                    System.out.println("Exiting....");
                    logger.debug("Exiting from updateEmployee to Main Menu after getting input 7 from user");
                    break;
                default:
                    System.out.println("Invalid Option, Exiting to main menu..");
                    logger.debug("Invalid option enterd bu the User. Returning to main menu.");
                    break;
            }
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void removeEmployee() {
        logger.debug("Enterd into removeEmployee from main menu");
        System.out.print("Enter the ID of Employee you want to Remove : ");
        String id = scanner.nextLine();
        try {
            logger.debug("Calling EmployeeService.isIdAvailable from removeEmployee if the received Employee ID from user is available in record.");
            employeeService.isIdAvailable(id);
            logger.debug("Calling EmployeeService.removeEmployee from removeEmployee to remove the employee with id: " + id);
            employeeService.removeEmployee(id);
            System.out.println("Employee with given Id removed successfully!");
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void readCertifications() {
        try {
            logger.debug("Enterd into readCertification from read method");
            System.out.println("\n1. Read All Certifications of an Employee\n2. Read Active Certifications of an Employee\nEnter your Option : ");
            String option = scanner.nextLine();
            System.out.print("Enter the Employee ID: ");
            String employeeId = scanner.nextLine();
            employeeService.isIdAvailable(employeeId);
            List<Certification> certificates;
            switch (option) {
                case "1":
                    logger.debug("Trying to read all certifications in the record.");
                    certificates = certificationService.getAllCertificationsByEmployee(employeeId);
                    printCertifications(certificates);
                    logger.debug("All certifications in the record was read successfully.");
                    break;
                case "2":
                    logger.debug("Trying to read all active certifications from the record");
                    certificates = certificationService.getActiveCertificationsByEmployee(employeeId);
                    printCertifications(certificates);
                    logger.debug("All active certifications in the record was read successfully.");
                    break;
                default:
                    System.out.println("Enter a valid Option...");
                    break;
            }
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void printCertifications(List<Certification> certificates) {
        int num = 1;
        if (!certificates.isEmpty()) {
            for (Certification certificate : certificates) {
                System.out.println("Certification: " + num++);
                System.out.println(certificate);
            }
        } else {
            System.out.println("The Certification Record is empty.");
            logger.error("Certification record is empty");
        }
    }

    public static void updateCertificationExpiryDate(String certificateId) {
        try{
            logger.debug("Entered into updateCertification method to update the certification with id: {}", certificateId);
            System.out.println("Enter the expiry date in proper format as yyyy-mm-dd: ");
            String date = LocalDate.parse(scanner.nextLine()).toString();
            certificationService.updateCertification(certificateId, date);
            logger.info("Successful update done to field expiryDate for Certificate with ID: {}", certificateId);
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            logger.error("Received wrongly formatted date string for certificate id: {}. Update to field expiryDate failed.");
            System.out.println("Update to field expiryDate failed.\nReceived wrongly formatted date input. Persisting the older date.");
        }
    }

    private static Department getValidDepartment(String id) {
        try {
            departmentService.isDepartmentAvailable(id.toUpperCase());
            return departmentService.getDepartmentWithId(id);
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("Assigning to default department: 'FRESHERS' with ID: 'DEPT01'");
            return departmentService.getDepartmentWithId("DEPT01");
        }
    }

    private static Project getValidProject(String id) {
        try {
            projectService.isProjectAvailable(id.toUpperCase());
            return projectService.getProjectWithId(id);
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("Assigning to default Project: 'FRESHER TRAINING' with ID: 'PROJ01'");
            return projectService.getProjectWithId("PROJ01");
        }
    }

    private static String getValidDate(String date) {
        try {
            return LocalDate.parse(date).toString();
        } catch (DateTimeParseException e) {
            date = LocalDate.now().toString();
            System.out.println("You have entered the date in wrong format"
                                + "\nBy Default DOJ will be set to today - ." + date
                                + "\nPlease Update it later.");
            logger.error("The date format was incorrect. Field DOJ Defaults to today's date {}.", date);
            return date;
        }
    }

    private static String getValidExpiryDate(String date) {
        try {
            return LocalDate.parse(date).toString();
        } catch (DateTimeParseException e) {
            date = LocalDate.now().plusDays(10).toString();
            System.out.println("You have entered the date in wrong format"
                                + "\nBy Default Expiry Date will be set to 10 days from today - ." + date
                                + "\nPlease Update it later.");
            logger.error("The date format was incorrect. Field expiryDate Defaults to 10 days from today {}.", date);
            return date;
        }
    }

    private static void printCertificationsByEmployee(Employee employee) {
        for (Certification certif: employee.getCertifications()) {
            System.out.println(certif.getId() + " - " + certif.getStatus() + ": Valid upto " + certif.getExpiryDate());
        }
    }

    private static void getCertifications(Employee employee, int num) {
        for(int i = 0; i < num; i++) {
            System.out.println("Certification: " + (i + 1));
            System.out.print("Certification Name: ");
            String certificateName = scanner.nextLine();
            System.out.print("Certification Provider: ");
            String provider = scanner.nextLine();
            System.out.print("Certification level (Foundation / Associate / Expert): ");
            String level = scanner.nextLine();
            System.out.print("Expiry Date (yyyy-mm-dd): ");
            String expiryDate = scanner.nextLine();
            expiryDate = getValidExpiryDate(expiryDate);
            Certification certificate = certificationService.createCertification(certificateName, provider, expiryDate, level, employee);
            System.out.println("Certification added successfully with the ID: " + certificate.getId());
        }
    }
}