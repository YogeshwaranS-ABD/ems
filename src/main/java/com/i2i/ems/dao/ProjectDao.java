package com.i2i.ems.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.NoResultException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.connector.HibernateConnection;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.exception.NoSuchProjectException;
import com.i2i.ems.model.Project;

public class ProjectDao {

    private static Logger logger = LogManager.getLogger(ProjectDao.class);

    public boolean isProjectAvailable(String id) throws EmployeeManagementException {
        logger.debug("Checking if the project with ID: {} available or not", id);
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            String projectId = session.createQuery("select id from Project as p where p.id = :id", String.class)
                            .setParameter("id", id)
                            .getSingleResult();
            logger.info("Project With ID: {} exists. Returning True", id);
            return true;
        } catch (HibernateException e) {
            logger.error("Error while searching for project with id: {}", id);
            throw new EmployeeManagementException("Error occured while checking the given ID already exists or not: " + id, e);
        } catch (NoResultException e) {
            logger.error("Project with ID: {} not found in the record.", id);
            throw new NoSuchProjectException("No Project Found in the record with ID: " + id, e);
        }
    }
    public void addProject(Project project) throws EmployeeManagementException {
        logger.debug("Entered into addProject to add new project to the record");
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(project);
            session.getTransaction().commit();
            logger.info("{} - {} added to the record successfully.", project.getName(), project.getId());
        } catch (HibernateException e) {
            logger.error("Error while adding project - {} to the record", project.getName());
            throw new EmployeeManagementException("Error occured while creating a new project with name: " + project.getName(), e);
        }
    }

    public List<Project> getAllProjects() throws EmployeeManagementException {
        logger.debug("Entered into getAllProjects to retrive all projects as list");
        List<Project> projects = new ArrayList<Project>();
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            projects = session.createQuery("from Project", Project.class).list();
            logger.info("Retrival of all projects successful");
            return projects;
        } catch (HibernateException e) {
            logger.error("Error while retriving all Projects");
            throw new EmployeeManagementException("Error occured while retriving projects information from record", e);
        }
    }

    public Project getProjectWithId(String id) throws EmployeeManagementException {
        logger.debug("Trying to retrive project with id: {}", id);
        Project project = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            project = session.get(Project.class, id);
            Hibernate.initialize(project.getEmployees());
            logger.info("Project with ID: {} retrived successfully", id);
            return project;
        } catch (HibernateException e) {
            logger.error("Error while retriving project with ID: {}", id);
            throw new EmployeeManagementException("Error occured while retrieving project with id: " + id, e);
        }
    }

    public void updateProjectStatus(String id, String status) throws EmployeeManagementException {
        logger.debug("Trying to update status of the project with ID: {}", id);
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            Project project = session.get(Project.class, id);
            project.setStatus(status);
            project.setUpdatedOn(LocalDateTime.now().toString());
            session.getTransaction().commit();
            logger.info("Status of project with ID: {}, updated successfully", id);
        } catch (HibernateException e) {
            logger.error("Error while trying to update status of project with ID: {}", id);
            throw new EmployeeManagementException("Error occured while updating status of project with ID: " + id, e);
        }
    }
}