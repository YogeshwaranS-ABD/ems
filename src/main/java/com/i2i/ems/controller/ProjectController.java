package com.i2i.ems.controller;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Project;

import com.i2i.ems.service.ProjectService;

public class ProjectController {

    private static Scanner scanner = new Scanner(System.in);
    private static ProjectService projectService = new ProjectService();
    private static Logger logger = LogManager.getLogger(ProjectController.class);

    public static void createProject() {
        logger.debug("Entered into createProject from create method");
        try {
            System.out.print("Enter the Project Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter the current status (Active / Completed) : ");
            String status = scanner.nextLine();
            System.out.println("Enter the technologies used with comma(,) as seperator: ");
            String techUsed = scanner.nextLine();
            logger.debug("Calling ProjectService.createProject to create a project with name: " + name);
            String id = projectService.createProject(name, status, techUsed);
            System.out.println("The project was created successfully with ID: " + id);
            logger.info("The project with name " + name + " created successfully with id: " + id);
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void readProject() {
        logger.debug("Entered into readProject from read method");
        System.out.print("\n1. Read list of Projects\n2. Read Project with ID\nEnter your Option: ");
        String option = scanner.nextLine();
        try {
            switch(option) {
                case "1":
                    logger.debug("Calling ProjectService.getAllProjects to get list of all projects");
                    printAllProjects();
                    break;
                case "2":
                    System.out.print("Enter the Project ID to read : ");
                    String id = scanner.nextLine();
                    logger.debug("Calling ProjectService.getProjectWithId to get details of project with id: " + id);
                    projectService.isProjectAvailable(id);
                    Project project = projectService.getProjectWithId(id);
                    System.out.println(project);
                    logger.info("Read operation of Project with id: {} and name: {} completed successfully", id, project.getName());
                    break;
                default:
                    System.out.println("Invalid input. Please Try again");
                    break;
            }
            
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void printAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        for(Project project: projects) {
            System.out.println(project.getNameWithId());
        }
        logger.info("List of all projects displayed successfully");
    }

    public static void updateProject() {
        logger.debug("Entered into updateProject from update method");
        System.out.println("Enter the Project ID: ");
        String id = scanner.nextLine();
        try {
            logger.debug("Calling ProjectService.getProjectWithId to get the current status of the project with id: " + id);
            projectService.isProjectAvailable(id);
            System.out.println("Enter the status of the project to be updated : ");
            String status = scanner.nextLine();
            logger.debug("Updating the status of the project with id: " + id);
            projectService.updateProject(id, status);
            logger.info("Update operation completed Successfully for project with id: " + id);
        } catch (EmployeeManagementException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}