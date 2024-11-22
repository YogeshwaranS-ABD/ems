package com.i2i.ems.dao;

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
import com.i2i.ems.exception.NoSuchDepartmentException;
import com.i2i.ems.model.Department;

public class DepartmentDao {

    private static Logger logger = LogManager.getLogger(DepartmentDao.class);

    public boolean isDepartmentAvailable(String id) throws EmployeeManagementException {
        logger.debug("Entered into isDepartmentAvailable to search for department with id: {}", id);
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            String departmentId = session.createQuery("select id from Department as d where d.id = :id", String.class)
                            .setParameter("id", id)
                            .getSingleResult();
            logger.info("Depertment With ID: {} exists in the record. Returning True", id);
            return true;
        } catch (HibernateException e) {
            logger.error("Hibernate Error: {}", e.getMessage());
            throw new EmployeeManagementException("Error occured while checking the given ID already exists or not: " + id, e);
        } catch (NoResultException e) {
            logger.error("No department is found with ID: {} in the record", id);
            throw new NoSuchDepartmentException("No Department Found in the record with ID: " + id, e);
        }
    }

    public Department getDepartmentWithId(String id) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Department department = session.get(Department.class, id);
            Hibernate.initialize(department.getEmployees());
            logger.info("Department with ID: {} retrieved successfully");
            return department;
        } catch (HibernateException e) {
            logger.error("Error in Hibernate while retriving department with id: {}", id);
            throw new EmployeeManagementException("Error occured while retrieving information about department with id: " + id, e);
        }
    }

    public void addDepartment(Department department) throws EmployeeManagementException {
        logger.debug("Entered into addDepartment to add department - {} in the record", department.getName());
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(department);
            session.getTransaction().commit();
            logger.info("Department {} added to the record successfully with id: {}.", department.getName(), department.getId());
        } catch (HibernateException e) {
            logger.error("Error in Hibernate while adding a new department with id: {}\n{}", department.getId(), e.getMessage());
            throw new EmployeeManagementException("Error occured while creating a new department named: " + department.getName(), e);
        }
    }

    public List<Department> getAllDepartments() throws EmployeeManagementException {
        List<Department> departments = new ArrayList<Department>();
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            departments = session.createQuery("from Department", Department.class).list();
            return departments;
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Error occured while retriving information about all deparments", e);
        }
    }

/*    public void removeDepartmentWithId(String id) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            Department department = session.get(Department.class, id);
            session.remove(department);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Error occured while removing department with id: " + id, e);
        }
    }*/ 
}