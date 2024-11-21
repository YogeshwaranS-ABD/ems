package com.i2i.ems.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.i2i.ems.constants.Constants;

@Entity
@Table(name = "projects")
public class Project implements Comparable<Project> {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String status;

    @Column(name = "tech_used")
    private String techUsed;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_on")
    private String updatedOn;

    public Project() {}

    public Project(String id, String name, String status, String techUsed) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.techUsed = techUsed;
        this.isDeleted = false;
        this.createdOn = LocalDateTime.now().toString();
        this.updatedOn = LocalDateTime.now().toString();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTechUsed() {
        return this.techUsed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTechUsed(String techUsed) {
        this.techUsed = techUsed;
    }

    public void updateTechUsed(String tech) {
        this.techUsed = this.techUsed + "," + tech;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public int compareTo(Project project) {
        return (this.hashCode() - project.hashCode());
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
        return "Project Name : " + this.name
                + "\nProject ID: " + this.id
                + "\nStatus: " + this.status
                + "\nTechnologies Used: " + this.techUsed
                + "\nNumber of Employees worked and working in this project: " + this.employees.size();
    }
}