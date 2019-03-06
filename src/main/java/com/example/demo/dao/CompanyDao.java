package com.example.demo.dao;

import com.example.demo.model.Company;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CompanyDao {
    Company create(Company company);

    Company get(Integer id);

    void update(Company company);

    void delete(Integer id);

    List<Company> getAll();

    List<Company> filter(String filter);
}
