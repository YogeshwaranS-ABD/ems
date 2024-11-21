package com.i2i.ems.model;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.i2i.ems.constants.Constants;

import com.i2i.ems.model.Address;
import com.i2i.ems.model.Certification;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Project;

@Entity
@Table(name = "employees")
public class Employee implements Comparable<Employee>{

    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String doj;

    @Column
    private String role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private Address address;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_on")
    private String updatedOn;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<Certification> certifications;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employees_projects",
                joinColumns = @JoinColumn(name = "employee_id"),
                inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;

    public Employee() {}

    public Employee(String id, String name, String email, String doj, String role, Address address, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.doj = doj;
        this.role = role;
        address.setEmployee(this);
        this.address = address;
        this.department = department;
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

    public String getDoj() {
        return this.doj;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRole() {
        return this.role;
    }

    public Address getAddress() {
        return this.address;
    }

    public Department getDepartment() {
        return this.department;
    }

    public Set<Certification> getCertifications() {
        return this.certifications;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public String getNameWithId() {
        return this.id + " - " + this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAddress(Address address) {
        address.setEmployee(this);
        this.address = address;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setCertifications(Set<Certification> certifications) {
        this.certifications = certifications;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if (this.projects == null) {
            this.projects = new TreeSet<Project>();
        }
        this.projects.add(project);
    }

    private String expParser(String doj) {
        Period exp = Period.between(LocalDate.parse(doj), LocalDate.now());
        String experience = "" + ((exp.getYears() > 0) ? exp.getYears() + " Years " : "")
                            .concat( (exp.getMonths() > 0) ? exp.getMonths() + " Months " : "" )
                            .concat( (exp.getDays() > 0) ? exp.getDays() + " Days " : "" );
        return experience;
    }

    public int compareTo (Employee employee) {
        return (this.hashCode() - employee.hashCode());
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
        String returnString = ( "\nEmployee Name: " + this.name
                + "\nEmployee Id: " + this.id
                + "\nEmail Id: " + this.email
                + "\nRole: " + this.role
                + "\nDate Joined: " + this.doj
                + "\nExperience: " + expParser(this.doj)
                + "\nCertifications Count: " + this.certifications.size()
                + "\nNumber of projects worked with: " + this.projects.size())
                + "\nDepartment Id: " + this.department.getId();
        if (this.address != null) {
            returnString = returnString + this.address;
            return returnString;
        } else {
            return "No Address found, Please update the Address";
        }
    }
}