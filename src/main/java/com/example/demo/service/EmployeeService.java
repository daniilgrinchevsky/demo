package com.example.demo.service;

import com.example.demo.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee, Integer companyId);

    Employee get(Integer id);

    void update(Employee employee, Integer companyId);

    void delete(Integer id);

    List<Employee> getAll();

    List<Employee> filter(String filter);
}
