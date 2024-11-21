package com.i2i.ems.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.i2i.ems.model.Employee;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column
    private String id;

    @Column
    private String name;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;

    @Column(name = "created_on")
    private String createdOn;

//    private String updatedOn;
//    private boolean isDeleted;

    public Department() {}

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
        this.employees = new TreeSet<Employee>();
//        this.isDeleted = false;
        this.createdOn = LocalDateTime.now().toString();
//        this.updatedOn = LocalDateTime.now().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void addEmployee(Employee employee) {
        employee.setDepartment(this);
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

/*    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
*/
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

/*    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
*/
    public String getCreatedOn() {
        return this.createdOn;
    }
/*
    public String getUpdatedOn() {
        return this.updatedOn;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }
*/
    public String toString() {
        return "Department Name: " + this.name
                + "\nDepartment ID: " + this.id
                + "\nNumber of Employees in this department: " + this.employees.size();
    }
}