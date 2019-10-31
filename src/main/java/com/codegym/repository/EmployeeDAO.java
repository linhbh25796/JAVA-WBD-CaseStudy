package com.codegym.repository;

import com.codegym.entity.EmployeeDetail;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAO {
    @PersistenceContext
    EntityManager em;

    public List<EmployeeDetail> getListEmployee(int page, int pageSize) {
        System.out.println(" page size:"+pageSize);

        String sql = " select em.id, de.name dp_name,em.avatar,em.name,em.birthDate,em.address,em.salary\n" +
                " from  employee em LEFT JOIN department de" +
                " ON em.department_id = de.id" +
                " ORDER BY em.salary ASC"+
                " limit :page_size_ " +
                " offset :offset_ ";
        Query query = em.createNativeQuery(sql);
        query.setParameter("page_size_", pageSize);
        query.setParameter("offset_", page * pageSize);

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

    public List<EmployeeDetail> getSearchList(Long id) {

        String sql = " select em.id, de.name dp_name,em.avatar,em.name,em.birthDate,em.address,em.salary\n" +
                " from  employee em LEFT JOIN department de\n" +
                " on em.department_id=de.id\n" +
                " where de.id = :id_ "+
                " ORDER BY em.salary ASC";
        Query query = em.createNativeQuery(sql);

        query.setParameter("id_", id);

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
//    public int getListSearchByIdLength(Long id){
//        String sql = " select count(*)\n" +
//                " from  employee em LEFT JOIN department de\n" +
//                " on em.department_id=de.id\n" +
//                " where de.id = :id_ ";
//        Query query = em.createNativeQuery(sql);
//        query.setParameter("id_", id);
//        BigInteger len = (BigInteger) query.getSingleResult();
//        return len.intValue();
//    }

    public int getListEmployeeLength() {

        String sql = " select count(*) from employee";
        Query query = em.createNativeQuery(sql);
        BigInteger len = (BigInteger) query.getSingleResult();
        return len.intValue();
    }
}
