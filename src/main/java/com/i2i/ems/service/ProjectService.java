package com.i2i.ems.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.dao.ProjectDao;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Project;
import com.i2i.ems.utils.Util;

public class ProjectService {

    private static Logger logger = LogManager.getLogger(ProjectService.class);
    private ProjectDao projectDao = new ProjectDao();

    public String createProject(String name, String status, String techUsed) throws EmployeeManagementException {
        logger.debug("Entered into createProject to create a new project");
        String id = Util.generateIdForProject();
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setStatus(status);
        project.setTechUsed(techUsed);
        project.setCreatedOn(LocalDateTime.now().toString());
        projectDao.addProject(project);
        logger.info("New project was created successfully with id: {} and name: {}", id, name);
        return id;
    }

    public void updateProject(String id, String status) throws EmployeeManagementException {
        logger.debug("Entered into updateProject to update the status of project with id: {}", id);
        projectDao.updateProjectStatus(id.toUpperCase(), status);
        logger.info("The status of the project with id: {} was updated to {} successfully", id, status);
    }

    public List<Project> getAllProjects() throws EmployeeManagementException {
        logger.debug("Entered into method: getAllProjects and returning the list of projects");
        return projectDao.getAllProjects();
    }

    public boolean isProjectAvailable(String id) throws EmployeeManagementException {
        logger.debug("Enterd into method: isProjectAvailable to check if id: {} exists or not", id);
        boolean state = projectDao.isProjectAvailable(id.toUpperCase());
        logger.info("Project found with ID: {}", id);
        return state;
    }

    public Project getProjectWithId(String id) throws EmployeeManagementException {
        logger.debug("Entered into getProjectWithId to retrive project with id: {} from the record", id);
        Project project = projectDao.getProjectWithId(id.toUpperCase());
        logger.info("The project with id: {}, retrived successfully", id);
        return project;
    }
}