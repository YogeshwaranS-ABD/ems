package com.i2i.ems.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.i2i.ems.constants.Constants;
import com.i2i.ems.model.Employee;

@Entity
@Table(name = "certifications")
public class Certification implements Comparable<Certification> {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String level;

    @Column
    private String provider;

    @Column(name = "expiry_date")
    private String expiryDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_on")
    private String updatedOn;

    public Certification() {}

    public Certification(String id, String name, String provider, String expiryDate, String level, Employee employee) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.expiryDate = expiryDate;
        this.level = level;
        this.employee = employee;
        this.isDeleted = false;
        this.createdOn = LocalDateTime.now().toString();
        this.updatedOn = LocalDateTime.now().toString();
    }

    public String getStatus() {
        if (LocalDate.parse(this.expiryDate).isAfter(LocalDate.now())) {
            return "Active";
        }
        return "Expired";
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getLevel() {
        return this.level;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiryDate(String date) {
        this.expiryDate = date;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int compareTo(Certification certificate) {
        return (this.hashCode() - certificate.hashCode());
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUpdatedOn() {
        return this.updatedOn;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    @Override
    public String toString() {
        return "Certification Name: " + this.name
                + "\nCertificate ID: " + this.id
                + "\nCertification Holder: " + this.employee.getNameWithId()
                + "\nCertification Provider: " + this.provider
                + "\nCertification Level: " + this.level
                + "\nCertificatoin Status: " + getStatus();
    }
}