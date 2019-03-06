package com.example.demo;

import com.example.demo.model.Company;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    private final static Employee EMPLOYEE_TEST= new Employee(100004,"test", "test@gmail.com", LocalDate.now());
    private final static Employee EMPLOYEE_1 = new Employee(100002,"qwe", "qwe@gmail.com", LocalDate.of(2019, 1, 1));
    private final static Employee EMPLOYEE_2 = new Employee(100003,"asd", "asd@gmail.com", LocalDate.of(2019, 3,3));
    private final static Company COMPANY_2 = new Company("Company2", "Address2", 999887631234L, "89062333718");
    // private final static Company COMPANY = new Company("test", "testAddress", 999999999999L, "89017777777");

    @Test
    public void createTest() {
        employeeService.create(EMPLOYEE_TEST, 100000);
        List<Employee> expected = new ArrayList<>();
        expected.add(EMPLOYEE_1);
        expected.add(EMPLOYEE_2);
        expected.add(EMPLOYEE_TEST);
        assertThat(employeeService.getAll()).usingElementComparatorIgnoringFields("company").isEqualTo(expected);
    }

    @Test
    public void getTest() {
        Employee employee = employeeService.get(EMPLOYEE_1.getId());
        //assertThat(employee).isEqualToIgnoringGivenFields(EMPLOYEE_1, "company");
        assertThat(employee).isEqualTo(EMPLOYEE_1);
    }

    @Test
    public void updateTest() {
        Employee updated = new Employee(EMPLOYEE_1);
        updated.setName("updated");
        updated.setEmail("updated@gmail.com");
        updated.setBirthday(LocalDate.of(2018, 9, 9));
        employeeService.update(updated, 100001);
        assertThat(employeeService.get(EMPLOYEE_1.getId())).isEqualToIgnoringGivenFields(updated, "company");
    }

    @Test
    public void getAllTest(){
        List<Employee> all = employeeService.getAll();
        List<Employee> expected = new ArrayList<>();
        expected.add(EMPLOYEE_1);
        expected.add(EMPLOYEE_2);
        assertThat(all).usingElementComparatorIgnoringFields("company").isEqualTo(expected);
    }

    @Test
    public void deleteTest() {
        employeeService.delete(EMPLOYEE_1.getId());
        List<Employee> expected = new ArrayList<>();
        expected.add(EMPLOYEE_2);
        assertThat(employeeService.getAll()).usingElementComparatorIgnoringFields("company").isEqualTo(expected);
    }
}
