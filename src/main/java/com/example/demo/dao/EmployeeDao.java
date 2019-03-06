package com.example.demo.dao;

import com.example.demo.model.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface EmployeeDao {
    Employee create(Employee employee, Integer companyId);

    Employee get(Integer id);

    void update(Employee employee, Integer companyId);

    void delete(Integer id);

    List<Employee> getAll();

    List<Employee> filter(String filter);

}
