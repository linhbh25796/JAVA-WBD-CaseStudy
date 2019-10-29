package com.codegym.repository;

import com.codegym.entity.EmployeeDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAO {
    @PersistenceContext
    EntityManager em;

    public List<EmployeeDetail> getListEmployee(Pageable pageable) {
        String sql = " select em.id, de.name dp_name,em.avatar,em.name,em.birthDate,em.address,em.salary\n" +
                " from  employee em LEFT JOIN department de" +
                " ON em.department_id = de.id";
        Query query = em.createNativeQuery(sql);
        List<Object[]> listResult = query.getResultList();
        List<EmployeeDetail> employeeDetails = new ArrayList<>();
        EmployeeDetail item;

        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new EmployeeDetail();
            item.setId((Long.parseLong("" + row[i++])));
            item.setDepartmentOfEmployee("" + row[i++]);
            item.setAvatar("" + row[i++]);
            item.setName("" + row[i++]);
            item.setBirthDate("" + row[i++]);
            item.setAddress("" + row[i++]);
            item.setSalary("" + row[i++]);
            employeeDetails.add(item);
        }
        return employeeDetails;
    }
    // sap xep tang dan

    public List<EmployeeDetail> getListByPriceASC(Pageable pageable) {
        String sql = " select em.id, de.name dp_name,em.avatar,em.name,em.birthDate,em.address,em.salary\n" +
                " from  employee em LEFT JOIN department de" +
                " ON em.department_id = de.id ORDER BY em.salary ASC";
        Query query = em.createNativeQuery(sql);
        List<Object[]> listResult = query.getResultList();
        List<EmployeeDetail> employeeDetails = new ArrayList<>();
        EmployeeDetail item;

        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new EmployeeDetail();
            item.setId((Long.parseLong("" + row[i++])));
            item.setDepartmentOfEmployee("" + row[i++]);
            item.setAvatar("" + row[i++]);
            item.setName("" + row[i++]);
            item.setBirthDate("" + row[i++]);
            item.setAddress("" + row[i++]);
            item.setSalary("" + row[i++]);
            employeeDetails.add(item);
        }
        return employeeDetails;
    }
        // sap xep giam dan
        public List<EmployeeDetail> getListByPriceDESC(Pageable pageable) {
            String sql = " select em.id, de.name dp_name,em.avatar,em.name,em.birthDate,em.address,em.salary\n" +
                    " from  employee em LEFT JOIN department de" +
                    " ON em.department_id = de.id ORDER BY em.salary DESC";
            Query query = em.createNativeQuery(sql);
            List<Object[]> listResult = query.getResultList();
            List<EmployeeDetail> employeeDetails = new ArrayList<>();
            EmployeeDetail item;

            int i;
            for (Object[] row : listResult) {
                i = 0;
                item = new EmployeeDetail();
                item.setId((Long.parseLong("" + row[i++])));
                item.setDepartmentOfEmployee("" + row[i++]);
                item.setAvatar("" + row[i++]);
                item.setName("" + row[i++]);
                item.setBirthDate("" + row[i++]);
                item.setAddress("" + row[i++]);
                item.setSalary("" + row[i++]);
                employeeDetails.add(item);
            }
            return employeeDetails;
        }
}
