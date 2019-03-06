package com.example.demo;

import com.example.demo.model.Company;
import com.example.demo.model.Employee;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeMapper implements RowMapper<Employee> {


    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setName(resultSet.getString("name"));
        employee.setEmail(resultSet.getString("email"));
        employee.setBirthday(resultSet.getObject("birthday", LocalDate.class));
        Company company = new Company();
        company.setAddress(resultSet.getString("cmp_address"));
        company.setName(resultSet.getString("cmp_name"));
        company.setTin(resultSet.getLong("cmp_tin"));
        company.setId(resultSet.getInt("cmp_id"));
        company.setPhone(resultSet.getString("cmp_phone"));
        employee.setCompany(company);


        return employee;
    }
}
