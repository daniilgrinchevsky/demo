package com.example.demo;

import com.example.demo.model.Company;
import com.example.demo.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyMapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getInt("id"));
        company.setName(resultSet.getString("name"));
        company.setAddress(resultSet.getString("address"));
        company.setPhone(resultSet.getString("phone"));
        company.setTin(resultSet.getLong("tin"));
        return company;
    }
}
