package com.codegym.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String birthDate;
    private String address;
    private String avatar;
    private String salary;

    @Column(name = "department_id")
    private Long departmentOfEmployee;

    public Employee() {
    }

    public Employee(String name, String birthDate, String address, String avatar, String salary) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.avatar = avatar;
        this.salary = salary;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Employee(Long departmentOfEmployee) {
        this.departmentOfEmployee = departmentOfEmployee;
    }

    public Long getDepartmentOfEmployee() {
        return departmentOfEmployee;
    }

    public void setDepartmentOfEmployee(Long departmentOfEmployee) {
        this.departmentOfEmployee = departmentOfEmployee;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", salary='" + salary + '\'' +
                ", departmentOfEmployee=" + departmentOfEmployee +
                '}';
    }
}
