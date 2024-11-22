package com.i2i.ems.dao;

import java.time.LocalDateTime;

import jakarta.persistence.NoResultException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.connector.HibernateConnection;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.exception.FormerEmployeeCannotBeRead;
import com.i2i.ems.exception.NoSuchEmployeeException;
import com.i2i.ems.model.Certification;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import com.i2i.ems.model.Project;

public class EmployeeDao {

    private static Logger logger = LogManager.getLogger(EmployeeDao.class);

    public boolean isIdAvailable(String id) throws EmployeeManagementException {
        logger.debug("Entered into isIdAvailable to check if Employee with ID: {} exists in the record", id);
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            boolean state = session.createQuery("select e.isDeleted from Employee as e where e.id = :id", Boolean.class)
                            .setParameter("id", id)
                            .getSingleResult();
            if (state) {
                logger.warn("Employee with ID: {} exists in the record but marked as removed. Throwing FormerEmployeeCannotBeRead Exception", id);
                throw new FormerEmployeeCannotBeRead("Details of Former Employee cannot be read");
            }
            logger.info("Employee matched with ID: {}, returning true", id);
            return true;
        } catch (NoResultException e) {
            logger.error("Employee with ID: {} does not exists in the record", id);
            throw new NoSuchEmployeeException("No Employee Found in the record with ID: " + id, e);
        } catch (HibernateException e) {
            logger.error("Error in Hibernate while searching for ID: {}", id);
            throw new EmployeeManagementException("Error occured while checking the given ID already exists or not: " + id, e);
        }
    }

    public Employee getEmployeeWithId(String id) throws EmployeeManagementException {
        logger.debug("Entered into getEmployeeWithId to get Employee with ID: {} from the record", id);
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);
            logger.debug("Lazyly initializing projects and certifications of Employee : {}", employee.getNameWithId());
            Hibernate.initialize(employee.getProjects());
            Hibernate.initialize(employee.getCertifications());
            logger.info("Returning employee - {} to service class.", employee.getNameWithId());
            return employee;
        } catch (HibernateException e) {
            logger.error("Error occured in Hibernate while finding Employee with ID: {}", id);
            throw new EmployeeManagementException("Error occured while retrieving employee with id: " + id, e);
        }
    }

    public void addEmployee(Employee employee) throws EmployeeManagementException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(employee);
            session.getTransaction().commit();
            logger.info("New Employee {} added to the record successfully.", employee.getNameWithId());
        } catch (HibernateException e) {
            logger.error("Error occured in Hibernate while adding employee - {} to the record.", employee.getNameWithId());
            throw new EmployeeManagementException("Error occured while adding employee with id: " + employee.getId(), e);
        }
    }

    public void updateEmployee(Employee employee) throws EmployeeManagementException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            employee.setUpdatedOn(LocalDateTime.now().toString());
            session.merge(employee);
            session.getTransaction().commit();
            logger.info("Employee {} updated successfully.", employee.getNameWithId());
        } catch (HibernateException e) {
            logger.error("Error occured in Hibernate while adding employee - {} to the record.", employee.getNameWithId());
            throw new EmployeeManagementException("Error occured while updating employee: " + employee.getNameWithId(), e);
        }
    }

    public void removeEmployee(String employeeId) throws EmployeeManagementException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            employee.getAddress().setUpdatedOn(LocalDateTime.now().toString());
            employee.getAddress().setIsDeleted(true);
            logger.debug("{} Employee's Address marked as removed", employee.getNameWithId());
            int updated = session.createQuery("update Certification c set c.isDeleted = :state where c.employee.id = :employeeId", Certification.class)
                                    .setParameter("employeeId", employeeId)
                                    .setParameter("state", true)
                                    .executeUpdate();
            logger.debug("{} Employee's Certificates marked as removed", employee.getNameWithId());
            employee.setUpdatedOn(LocalDateTime.now().toString());
            employee.setIsDeleted(true);
            session.getTransaction().commit();
            logger.info("Employee - {}, marked as removed successfully", employee.getNameWithId());
        } catch (HibernateException e) {
            logger.error("Error occured in HIbernate while removing employee with ID: {}", employeeId);
            throw new EmployeeManagementException("Error occured while removing employee with id: " + employeeId, e);
        }
    }

  /*  public List<Employee> getAllEmployees() throws EmployeeManagementException {
        List<Employee> employees = new ArrayList<Employee>();
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            employees = session.createSelectionQuery("from Employee", Employee.class).list();
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Error occured while retriving details of all employees", e);
        }
        return employees;
    } */
}