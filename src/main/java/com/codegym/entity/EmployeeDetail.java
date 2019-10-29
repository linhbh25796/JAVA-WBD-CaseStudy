package com.codegym.entity;

import com.codegym.model.Employee;

import java.time.LocalDate;

public class EmployeeDetail {
    private Long id;
    private String name;
    private String birthDate;
    private String address;
    private String avatar;
    private String salary;
    private String departmentOfEmployee;

    public EmployeeDetail() {
    }

    public EmployeeDetail(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.birthDate = ""+employee.getBirthDate();
        this.address = employee.getAddress();
        this.avatar = employee.getAvatar();
        this.salary = employee.getSalary();
        this.departmentOfEmployee = ""+employee.getDepartmentOfEmployee();
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

    public String getDepartmentOfEmployee() {
        return departmentOfEmployee;
    }

    public void setDepartmentOfEmployee(String departmentOfEmployee) {
        this.departmentOfEmployee = departmentOfEmployee;
    }

    @Override
    public String toString() {
        return "EmployeeDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", salary='" + salary + '\'' +
                ", departmentOfEmployee='" + departmentOfEmployee + '\'' +
                '}';
    }
}