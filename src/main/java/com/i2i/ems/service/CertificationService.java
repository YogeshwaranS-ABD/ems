package com.i2i.ems.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.dao.CertificationDao;
import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.model.Certification;
import com.i2i.ems.model.Employee;
import com.i2i.ems.utils.Util;

public class CertificationService {

    private CertificationDao certificationDao = new CertificationDao();
    private static Logger logger = LogManager.getLogger(CertificationService.class);

    private String generateIdForCertification() {
        return "CERT" + Util.generateLongId();
    }

    public Certification createCertification(String name, String provider, String expiryDate, String level, Employee employee)
                            throws EmployeeManagementException {
        logger.debug("Entered into createCertification to create new Certification for employee: {}", employee.getNameWithId());
        String id = generateIdForCertification();
        Certification certificate = new Certification(id, name, provider, expiryDate, level, employee);
        certificationDao.addCertification(certificate);
        logger.info("Certification with id: {} for Employee: {} was created successfully", id, employee.getNameWithId());
        return certificate;
    }

    public List<Certification> getAllCertificationsByEmployee(String employeeId) throws EmployeeManagementException {
        logger.debug("Entered into getAllCertificationsByEmployee to retrive all certification of employee with id: " + employeeId);
        List<Certification> certifications = certificationDao.getAllCertificationsByEmployee(employeeId);
        logger.info("All certifications from the record has been retrieved successfully");
        return certifications;
    }

    public List<Certification> getActiveCertificationsByEmployee(String employeeId) throws EmployeeManagementException {
        logger.debug("Enterd into getActiveCertificationsByEmployee to retrive only active certifications from the record");
        List<Certification> certificates = certificationDao.getAllCertificationsByEmployee(employeeId);
        for (Iterator<Certification> iterator = certificates.iterator(); iterator.hasNext();) {
            if (iterator.next().getStatus().equals("Expired")) {
                iterator.remove();
            }
        }
        logger.info("All active certifications was retrieved successfully");
        return certificates;
    }

/*    public Certification getCertificationWithId(String id) throws EmployeeManagementException {
        logger.debug("Entered into getcertificationWithId to retrieve certification with id: {}", id);
        Certification certificate = certificationDao.getCertificationWithId(id);
        logger.info("Certification {} - {} was retrieved successfully from the record", certificate.getId(), certificate.getName());
        return certificate;
    } */

    public void updateCertification(String certificateId, String expiryDate) {
        logger.debug("Entered into updateCertification method to update certificate with id: {}", certificateId);
        certificationDao.updateCertification(certificateId, expiryDate);
    }

    public void removeCertificatesByEmployee(String employeeId) throws EmployeeManagementException {
        logger.debug("Entered into removeCertificatesByEmployee to remove certifications of Employee with id: {}", employeeId);
        certificationDao.removeCertificatesWithEmployeeId(employeeId);
        logger.debug("The certificates of employee with id: {} was removed succussfully from the record", employeeId);
    }

}