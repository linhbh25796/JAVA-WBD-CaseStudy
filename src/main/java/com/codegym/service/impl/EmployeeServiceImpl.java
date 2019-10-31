package com.codegym.service.impl;

import com.codegym.entity.EmployeeForm;
import com.codegym.model.Employee;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return  employeeRepository.findOne(id);
    }

    @Override
    public void edit(EmployeeForm employeeForm, String avatar) {
        Employee employeeObject = employeeRepository.findOne(employeeForm.getId());
        employeeObject.setName(employeeForm.getName());
        employeeObject.setAddress(employeeForm.getAddress());
        employeeObject.setBirthDate(employeeForm.getBirthDate());
        employeeObject.setSalary(employeeForm.getSalary());
        employeeObject.setAvatar(avatar);
        employeeObject.setDepartmentOfEmployee(employeeForm.getDepartmentOfEmployee());
        employeeRepository.save(employeeObject);
    }


    @Override
    public void remove(Long id) {
        employeeRepository.delete(id);
    }

    @Override
    public Iterable<Employee> findAllByName(Employee employee) {
        return employeeRepository.findAllByName(employee);
    }


}
