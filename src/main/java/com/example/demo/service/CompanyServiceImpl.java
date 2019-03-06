package com.example.demo.service;

import com.example.demo.dao.CompanyDao;
import com.example.demo.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public Company create(Company company) {
        return companyDao.create(company);
    }

    @Override
    public Company get(Integer id) {
        return companyDao.get(id);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void delete(Integer id) {
        companyDao.delete(id);
    }

    @Override
    public List<Company> getAll() {
        return companyDao.getAll();
    }

    @Override
    public List<Company> filter(String filter) {
        return companyDao.filter(filter);
    }
}
