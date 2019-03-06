package com.example.demo.dao;

import com.example.demo.CompanyMapper;
import com.example.demo.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Company create(Company company) {
        String SQL = "INSERT INTO companies (name, address, tin, phone) VALUES (:name, :address, :tin, :phone)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", company.getName())
                .addValue("address", company.getAddress())
                .addValue("tin", company.getTin())
                .addValue("phone", company.getPhone());
        namedParameterJdbcTemplate.update(SQL, namedParameters);
        return company;
    }

    @Override
    public Company get(Integer id) {
        String SQL = "SELECT * FROM companies WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(id));
        Company company = namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, new CompanyMapper());
        return company;

    }

    @Override
    public void update(Company company) {
        String SQL = "UPDATE companies SET name = :name, address = :address, tin = :tin, phone = :phone WHERE id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", company.getId())
                .addValue("name", company.getName())
                .addValue("address", company.getAddress())
                .addValue("tin", company.getTin())
                .addValue("phone", company.getPhone());
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM companies WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(id));
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    @Override
    public List<Company> getAll() {
        String SQL = "SELECT * FROM companies";
        List companies = namedParameterJdbcTemplate.query(SQL, new CompanyMapper());
        return companies;
    }

    @Override
    public List<Company> filter(String filter) {
        String SQL = "SELECT * FROM companies WHERE LOWER(name) LIKE LOWER(concat('%', :filter, '%'))";
        SqlParameterSource namedParameters = new MapSqlParameterSource("filter", filter);
        List<Company> companies = namedParameterJdbcTemplate.query(SQL, namedParameters, new CompanyMapper());
        return companies;
    }
}
