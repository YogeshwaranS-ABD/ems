package com.i2i.ems.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.connector.HibernateConnection;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Certification;

public class CertificationDao {

    private static Logger logger = LogManager.getLogger(CertificationDao.class);

    public List<Certification> getAllCertificationsByEmployee(String employeeId) throws EmployeeManagementException {
        logger.debug("Entered into getAllCertificationsOfEmployee to retrive certifications of employee with ID: {}", employeeId);
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            String hql = "from Certification c where c.employee.id = :id";
            List<Certification> certificates = session.createSelectionQuery(hql, Certification.class).setParameter("id", employeeId).getResultList();
            logger.info("Certificates of Employee with ID: {} retrived successfully. Returning as a List.", employeeId);
            return certificates;
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Error occured while retriving all certifications details of employee with id: " + employeeId, e);
        }
    }

    public void addCertification(Certification certificate) throws EmployeeManagementException {
        logger.debug("Enterd into addCertification to add certification: {}, to Employee: {}", certificate.getName(), certificate.getEmployee().getNameWithId());
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(certificate);
            session.getTransaction().commit();
            logger.info("certification: {} successfully added to Employee: {}", certificate.getName(), certificate.getEmployee().getNameWithId());
        } catch (HibernateException e) {
            logger.error("Error occured while adding new certification to Employee: {}", certificate.getEmployee().getNameWithId());
            throw new EmployeeManagementException("Error occured while saving certification of employee " + certificate.getEmployee().getNameWithId(), e);
        }
    }

    public void updateCertification(String certificateId, String expiryDate) throws EmployeeManagementException {
        logger.debug("Entered into updateCertification to update expiry date for certificate: {}", certificateId);
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            Certification certificate = session.get(Certification.class, certificateId);
            certificate.setExpiryDate(expiryDate);
            certificate.setUpdatedOn(LocalDateTime.now().toString());
            session.getTransaction().commit();
            logger.info("Certificate with ID: {} updated successfuly", certificateId);
        } catch (HibernateException e) {
            logger.error("Expiry date updation failed for Certificate wiht ID: {}", certificateId);
            throw new EmployeeManagementException("Error occured while updating expiry date for certification with ID: " + certificateId, e);
        }
    }

    public boolean removeCertificatesWithEmployeeId(String employeeId) throws EmployeeManagementException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hql = "update from Certification c set c.isDeleted = true where c.employeeId = :id";
            int updated = session.createQuery(hql, Integer.class)
                                .setParameter("id", employeeId)
                                .executeUpdate();
            return true;
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Error occured while removing certifications of employee with id: " + employeeId, e);
        }
    }

/*    public Certification getCertificationWithId(String cid) throws EmployeeManagementException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Certification certificate = session.get(Certification.class, cid);
            if (!certificate.getIsDeleted()) {
                return certificate;
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new EmployeeManagementException("Error occured while retriving certification with id: " + cid, e);
        }
    }

            List<Certification> certifications = session.createQuery("from Certification where employeeId = ?", Certification.class)
                                                            .setParameter(0, employeeId).list();
            int count = 0;
            Iterator<Certification> certificationsIter = certifications.iterator();
            while (certificationsIter.hasNext()) {
                Certification certificate = certificationsIter.next();
                certificate.setIsDeleted(true);
                count++;
            }
            session.getTransaction().commit();
            if (count == certifications.size()) {
                return true;
            }
            return false; */

}