package com.example.demo.dao;

import com.example.demo.EmployeeMapper;
import com.example.demo.model.Employee;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Employee create(Employee employee, Integer companyId) {
        String SQL = "INSERT INTO employees (name, email, birthday, company_id) VALUES (:name, :email, :birthday, :company)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", employee.getName())
                .addValue("email", employee.getEmail())
                .addValue("birthday", employee.getBirthday())
                .addValue("company", companyId);
        namedParameterJdbcTemplate.update(SQL, namedParameters);
        return employee;
    }

    @Override
    public Employee get(Integer id) {
        String SQL = "SELECT *,companies.id AS cmp_id,companies.name AS cmp_name,companies.address AS cmp_address," +
                "companies.tin AS cmp_tin, companies.phone AS cmp_phone " +
                "FROM employees LEFT JOIN companies  ON companies.id=employees.company_id WHERE employees.id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(id));
        Employee employee = namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, new EmployeeMapper());
        return employee;

    }

    @Override
    public void update(Employee employee, Integer companyId) {
        String SQL = "UPDATE employees SET name = :name, email = :email, birthday = :birthday, company_id = :company WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", employee.getId())
                .addValue("name", employee.getName())
                .addValue("email", employee.getEmail())
                .addValue("birthday", employee.getBirthday())
                .addValue("company", companyId);
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM employees WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(id));
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    @Override
    public List<Employee> getAll() {
        String SQL = "SELECT *,companies.id as cmp_id,companies.name as cmp_name,companies.address as cmp_address," +
                "companies.tin as cmp_tin, companies.phone as cmp_phone " +
                "FROM employees LEFT JOIN companies  on companies.id=employees.company_id";
        List employees = namedParameterJdbcTemplate.query(SQL, new EmployeeMapper());
        return employees;
    }

    @Override
    public List<Employee> filter(String filter) {
        String SQL = "SELECT * FROM employees WHERE LOWER(name) LIKE LOWER(concat('%', :filter, '%'))";
        SqlParameterSource namedParameters = new MapSqlParameterSource("filter", filter);
        List<Employee> employees = namedParameterJdbcTemplate.query(SQL, namedParameters, new EmployeeMapper());
        return employees;
    }
}
