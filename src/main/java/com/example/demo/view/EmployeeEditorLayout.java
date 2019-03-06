package com.example.demo.view;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@SpringComponent
public class EmployeeEditorLayout extends HorizontalLayout {

    private Button save = new Button("Save");
    private Button edit = new Button("Edit");
    private Button delete = new Button("Delete");
    private TextField filter = new TextField();


    @Autowired
    private EmployeeService employeeService;

    private Binder<Employee> binder = new Binder<>(Employee.class);

    private Employee bean = new Employee();

    @Autowired
    public void EmployeeEditorLayout() {

        filter.setPlaceholder("Search by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
      //  filter.addValueChangeListener();
        save.addClickListener(e -> saveEmployee());
        edit.addClickListener(e -> {
            if(bean.getId() != null)
            editEmployee();
        });
        delete.addClickListener(e -> {
            if(bean.getId() != null)
            deleteEmployee();
        });
        addComponents(save, edit, delete, filter);

    }

    public void saveEmployee() {
        Window subWindow = new Window("Add employee");
        HorizontalLayout addEmployee = new HorizontalLayout();
        Button save = new Button("Save");
        Button close = new Button("Close");
        TextField name = new TextField();
        name.setPlaceholder("Name");
        TextField email = new TextField();
        email.setPlaceholder("Email");
        DateField birthday = new DateField();
        birthday.setPlaceholder("Birth date");
        TextField companyId = new TextField();
        companyId.setPlaceholder("Company ID");
        binder.forField(name).bind("name");
        binder.forField(email).bind("email");
        binder.forField(birthday).bind("birthday");
        binder.setBean(bean);
        name.clear();
        email.clear();
        birthday.clear();
        save.addClickListener(e -> {
            employeeService.create(bean, Integer.valueOf(companyId.getValue()));
            Notification.show("User successfully added",Notification.Type.TRAY_NOTIFICATION);
            name.clear();
            email.clear();
            birthday.clear();
            companyId.clear();
            //TODO refresh data

        });
        close.addClickListener(e -> subWindow.close());
        addEmployee.addComponents(name, email, birthday, companyId, save, close);
        subWindow.setContent(addEmployee);
        subWindow.setModal(true);
        getUI().addWindow(subWindow);
    }

    public void editEmployee(){
        Window subWindow = new Window("Edit employee");
        HorizontalLayout editEmployee = new HorizontalLayout();
        Button save = new Button("Save");
        Button close = new Button("Close");
        TextField name = new TextField();
        TextField email = new TextField();
        DateField birthday = new DateField();
        TextField companyId = new TextField();
        companyId.setValue(bean.getCompany().getId().toString());
        binder.forField(name).bind("name");
        binder.forField(email).bind("email");
        binder.forField(birthday).bind("birthday");
        binder.setBean(bean);
        save.addClickListener(e -> {
            employeeService.update(bean, Integer.valueOf(companyId.getValue()));
            subWindow.close();
                    Notification.show("User successfully edited", Notification.Type.TRAY_NOTIFICATION);
                    //TODO refresh data
        });
        close.addClickListener(e -> subWindow.close());
        editEmployee.addComponents(name, email, birthday, companyId, save, close);
        subWindow.setContent(editEmployee);
        subWindow.setModal(true);
        getUI().addWindow(subWindow);
    }

    public void deleteEmployee() {
        employeeService.delete(bean.getId());
        Notification.show("User successfully deleted", Notification.Type.TRAY_NOTIFICATION);
    }

    public void setBean(Employee bean) {
        this.bean = bean;
    }
}
