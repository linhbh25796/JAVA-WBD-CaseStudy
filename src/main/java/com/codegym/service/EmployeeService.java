package com.codegym.service;

import com.codegym.entity.EmployeeForm;
import com.codegym.model.Department;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EmployeeService {
    Page<Employee> findAll(Pageable pageable);

    void save(Employee employee);

    Employee findById(Long id);

    void edit(EmployeeForm employeeForm,String avatar);

    void remove(Long id);

    Iterable<Employee> findAllByName(Employee employee);



}
