package com.example.demo.view;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Set;

@SpringComponent
public class EmployeeEditorLayout extends HorizontalLayout {

    private Button save = new Button("Save");
    private Button edit = new Button("Edit");
    private Button delete = new Button("Delete");
    private TextField filter = new TextField();


    @Autowired
    private EmployeeService employeeService;

    private Employee employeeEdit;


    @Autowired
    public void EmployeeEditorLayout() {

        filter.setPlaceholder("Search by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
      //  filter.addValueChangeListener();
        save.addClickListener(e -> saveEmployee());
        edit.addClickListener(e -> {
            if(employeeEdit != null)
            editEmployee(employeeEdit);
        });
        delete.addClickListener(e -> {
            if(employeeEdit != null)
            deleteEmployee(employeeEdit.getId());
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
        birthday.setValue(LocalDate.now());
        TextField companyId = new TextField();
        companyId.setPlaceholder("Company ID");
        save.addClickListener(e -> {
            employeeService.create(
                    new Employee(name.getValue(), email.getValue(), birthday.getValue()), Integer.valueOf(companyId.getValue()));
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

    public void editEmployee(Employee employee){
        Window subWindow = new Window("Edit employee");
        HorizontalLayout editEmployee = new HorizontalLayout();
        Button save = new Button("Save");
        Button close = new Button("Close");
        TextField name = new TextField();
        TextField email = new TextField();
        DateField birthday = new DateField();
        TextField companyId = new TextField();
        name.setValue(employee.getName());
        email.setValue(employee.getEmail());
        birthday.setValue(employee.getBirthday());
        companyId.setValue(employee.getCompany().getId().toString());
        save.addClickListener(e -> {
            Employee updated = new Employee(employee.getId(), name.getValue(), email.getValue(), birthday.getValue());
            employeeService.update(updated, Integer.valueOf(companyId.getValue()));
                    Notification.show("User successfully edited", Notification.Type.TRAY_NOTIFICATION);
                    //TODO refresh data
        });
        close.addClickListener(e -> subWindow.close());
        editEmployee.addComponents(name, email, birthday, companyId, save, close);
        subWindow.setContent(editEmployee);
        subWindow.setModal(true);
        getUI().addWindow(subWindow);
    }

    public void deleteEmployee(Integer id){
        employeeService.delete(id);
        Notification.show("User successfully deleted", Notification.Type.TRAY_NOTIFICATION);
    }

    public void setEmployeeEdit(Employee employeeEdit) {
        this.employeeEdit = employeeEdit;
    }
}
