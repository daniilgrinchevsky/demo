package com.example.demo.service;

import com.example.demo.model.Company;

import java.util.List;


public interface CompanyService {
    Company create(Company company);

    Company get(Integer id);

    void update(Company company);

    void delete(Integer id);

    List<Company> getAll();

    List<Company> filter(String filter);
}
