package com.example.demo.view;


import com.example.demo.model.Company;
import com.example.demo.model.Employee;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@SpringUI
public class MainView extends UI {

    private VerticalLayout root = new VerticalLayout();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeEditorLayout employeeEditorLayout;

    @Autowired
    private CompanyEditorLayout companyEditorLayout;

    private TabSheet tabs = new TabSheet();
    private VerticalLayout companies = new VerticalLayout();
    private VerticalLayout employees = new VerticalLayout();
    private Grid<Employee> employeeGrid = new Grid<>(Employee.class);
    private Grid<Company> companyGrid = new Grid<>(Company.class);



    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(root);
        setupTabs();
        root.addComponent(companyEditorLayout);
        root.addComponent(tabs);

    }

    public void setupTabs(){
        companies.setId("0");
        employees.setId("1");
        tabs.addTab(companies, "Companies");
        tabs.addTab(employees, "Employees");
        tabs.setSelectedTab(companies);
        tabs.setWidth("60%");
        tabs.addSelectedTabChangeListener(e-> {
           if(tabs.getSelectedTab().getId().equals("0"))
               root.replaceComponent(employeeEditorLayout, companyEditorLayout);
           if(tabs.getSelectedTab().getId().equals("1"))
               root.replaceComponent(companyEditorLayout, employeeEditorLayout);
        });
        addEmployees();
        addCompanies();
    }

    public void addEmployees() {
        employeeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        employeeGrid.setItems(employeeService.getAll());
        employeeGrid.setColumnOrder("id", "name", "email", "birthday", "company");
        employeeGrid.getColumns().forEach(c->c.setResizable(false));
        employeeGrid.setWidth("100%");
        employees.addComponentsAndExpand(employeeGrid);
        employeeGrid.addSelectionListener(e -> {
            Employee employee = e.getFirstSelectedItem().get();
            employeeEditorLayout.setEmployeeEdit(employee);
        });
    }

    public void addCompanies() {
        employeeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        companyGrid.setItems(companyService.getAll());
        companyGrid.getColumn("employees").setHidden(true);
        companyGrid.setColumnOrder("id", "name", "address", "tin", "phone");
        companyGrid.getColumns().forEach(c->c.setResizable(false));
        companyGrid.setWidth("100%");
        companies.addComponentsAndExpand(companyGrid);
        companyGrid.addSelectionListener(e -> {
           Company company = e.getFirstSelectedItem().get();
           companyEditorLayout.setCompanyEdit(company);
        });
    }


}


