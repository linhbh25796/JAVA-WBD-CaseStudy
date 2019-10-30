package com.codegym.entity;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class EmployeeForm {
    private Long id;

    private String name;
    private LocalDate birthDate;
    private String address;
    private MultipartFile avatar;
    private Double salary;
    private Long departmentOfEmployee;

    public EmployeeForm() {
    }

    public EmployeeForm(Long id, String name, LocalDate birthDate, String address, MultipartFile avatar, Double salary, Long departmentOfEmployee) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.avatar = avatar;
        this.salary = salary;
        this.departmentOfEmployee = departmentOfEmployee;
    }

    public EmployeeForm(String name, LocalDate birthDate, String address, String fileName, Double salary, Long departmentOfEmployee) {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getDepartmentOfEmployee() {
        return departmentOfEmployee;
    }

    public void setDepartmentOfEmployee(Long departmentOfEmployee) {
        this.departmentOfEmployee = departmentOfEmployee;
    }
}
