package com.i2i.ems.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.i2i.ems.constants.Constants;

import com.i2i.ems.model.Employee;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    private String id;

    @Column(name = "door_no")
    private String doorNo;

    @Column
    private String street;

    @Column
    private String area;

    @Column
    private String city;

    @Column(name = "pin_code")
    private String pinCode;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_on")
    private String updatedOn;

    public Address() {}

    public Address(String id, String doorNo, String streetName, String area, String city, String pinCode) {
        this.id = id;
        this.doorNo = doorNo;
        this.street = streetName;
        this.area = area;
        this.city = city;
        this.pinCode = pinCode;
        this.isDeleted = false;
        this.createdOn = LocalDateTime.now().toString();
        this.updatedOn = LocalDateTime.now().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public String getId() {
        return this.id;
    }

    public String getDoorNo() {
        return this.doorNo;
    }

    public String getStreet() {
        return this.street;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public String getCity() {
        return this.city;
    }

    public String getArea() {
        return this.area;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public void setStreet(String streetName) {
        this.street = streetName;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    public String toString() {
        return "\nAddress:\n\t" + this.doorNo + ", " + this.street
                + "\n\t" + this.area + ", " + this.city + " - " + this.pinCode;
    }

}