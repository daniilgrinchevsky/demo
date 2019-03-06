package com.example.demo;

import com.example.demo.model.Company;
import com.example.demo.service.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
public class CompanyServiceImplTest {

    @Autowired
    private CompanyService companyService;

    private final static Company COMPANY_1 = new Company(100000,"Company1", "Address1", 123123319876L, "89076283792");
    private final static Company COMPANY_2 = new Company(100001,"Company2", "Address2", 999887631234L, "89062333718");
    private final static Company COMPANY_TEST= new Company(100004,"test", "testAddress", 999999999999L, "89017777777");

    @Test
    public void createTest() {
        companyService.create(COMPANY_TEST);
        List<Company> expected = new ArrayList<>();
        expected.add(COMPANY_1);
        expected.add(COMPANY_2);
        expected.add(COMPANY_TEST);
        assertThat(companyService.getAll()).usingElementComparatorIgnoringFields("employees").isEqualTo(expected);
    }

    @Test
    public void getTest() {
        Company company = companyService.get(COMPANY_1.getId());
        assertThat(company).isEqualToIgnoringGivenFields(COMPANY_1, "employees");
    }

    @Test
    public void updateTest() {
        Company updated = new Company(COMPANY_1);
        updated.setName("updated");
        updated.setAddress("updated address");
        updated.setTin(777777777777L);
        updated.setPhone("89088888888");
        companyService.update(updated);
        assertThat(companyService.get(COMPANY_1.getId())).isEqualToIgnoringGivenFields(updated, "employees");
    }

    @Test
    public void getAllTest() {
        List<Company> all = companyService.getAll();
        List<Company> expected = new ArrayList<>();
        expected.add(COMPANY_1);
        expected.add(COMPANY_2);
        assertThat(all).usingElementComparatorIgnoringFields("employees").isEqualTo(expected);
    }

    @Test
    public void deleteTest() {
        companyService.delete(COMPANY_1.getId());
        List<Company> expected = new ArrayList<>();
        expected.add(COMPANY_2);
        assertThat(companyService.getAll()).usingElementComparatorIgnoringFields("employees").isEqualTo(expected);
    }
}
