package com.codegym.repository;

import com.codegym.entity.EmployeeForm;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    Iterable<Employee> findAllByName(Employee employee);


//    Page<Employee> findAllByDepartmentOfEmployee(String DepartmentOfEmployee, Pageable pageable);


}
