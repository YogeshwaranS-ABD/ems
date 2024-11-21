package com.i2i.ems.dao;

import java.time.LocalDateTime;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.connector.HibernateConnection;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Address;

public class AddressDao {

    private static Logger logger = LogManager.getLogger(AddressDao.class);

    public void addAddress(Address address) {
        logger.debug("Entered into addAddress to add new address to the Employee with ID: {}", address.getEmployee().getId());
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(address);
            session.getTransaction().commit();
            logger.info("Address of Employee : {} successfully added into the record", address.getEmployee().getNameWithId());
        } catch (HibernateException e) {
            logger.error("Failed to add address of Employee: {} to the record", address.getEmployee().getNameWithId());
            throw new EmployeeManagementException("Failed to add address to the database", e);
        }
    }

    public void updateAddress(Address address) {
        logger.debug("Entered into updateAddress to update address of employee: {}", address.getEmployee().getNameWithId());
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(address);
            address.setUpdatedOn(LocalDateTime.now().toString());
            session.getTransaction().commit();
            logger.info("Success: update address of employee: {}", address.getEmployee().getNameWithId());
        } catch (HibernateException e) {
            logger.error("Error occured while updating address of employee: {}", address.getEmployee().getNameWithId());
            throw new EmployeeManagementException("Failed to update address with id" + address.getId(), e);
        }
    }

 /*   public boolean removeAddressWithId(String id) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            String hql = "update Address a set a.isDeleted = true where a.id = :id";
            int updated = session.createQuery(hql, Integer.class)
                                .setParameter("id" = id)
                                .executeUpdate();
            logger.info("Address With ID: {} marked as removed.", id);
            return true;
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Failed to remove address from the database", e);
        }
    }

    public Address getAddressWithId(String id) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            return session.get(Address.class, id);
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Failed to fetch Address with ID: " + id, e);
        }
    } */
}