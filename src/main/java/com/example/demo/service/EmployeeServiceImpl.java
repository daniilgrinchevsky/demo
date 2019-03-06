package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee create(Employee employee, Integer companyId) {
        return employeeDao.create(employee, companyId);
    }

    @Override
    public Employee get(Integer id) {
        return employeeDao.get(id);
    }

    @Override
    public void update(Employee employee, Integer companyId) {
        employeeDao.update(employee, companyId);
    }

    @Override
    public void delete(Integer id) {
        employeeDao.delete(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    @Override
    public List<Employee> filter(String filter) {
        return employeeDao.filter(filter);
    }
}
