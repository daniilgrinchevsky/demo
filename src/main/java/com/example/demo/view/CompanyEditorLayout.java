package com.example.demo.view;

import com.example.demo.model.Company;
import com.example.demo.service.CompanyService;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
public class CompanyEditorLayout extends HorizontalLayout {

    private Button save = new Button("Save");
    private Button edit = new Button("Edit");
    private Button delete = new Button("Delete");
    private TextField filter = new TextField();

    private Company companyEdit;

    @Autowired
    private CompanyService companyService;

    @Autowired
    public void CompanyEditorLayout() {
        filter.setPlaceholder("Search by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
       // filter.addValueChangeListener();
        save.addClickListener(e -> saveCompany());
        edit.addClickListener(e -> {
            if(companyEdit != null)
            editCompany(companyEdit);
        });
        delete.addClickListener(e -> {
            if(companyEdit != null)
            deleteCompany(companyEdit.getId());
        });
        addComponents(save, edit, delete, filter);

    }

    public void saveCompany() {
        Window subWindow = new Window("Add company");
        HorizontalLayout addCompany = new HorizontalLayout();
        Button save = new Button("Save");
        Button close = new Button("Close");
        TextField name = new TextField();
        name.setPlaceholder("Name");
        TextField address = new TextField();
        address.setPlaceholder("Address");
        TextField tin = new TextField();
        tin.setPlaceholder("Tin");
        TextField phone = new TextField();
        phone.setPlaceholder("Phone number");
        save.addClickListener(e -> {
            companyService.create(
                    new Company(name.getValue(), address.getValue(), Long.valueOf(tin.getValue()), phone.getValue()));
            Notification.show("Company successfully added", Notification.Type.TRAY_NOTIFICATION);
            name.clear();
            address.clear();
            tin.clear();
            phone.clear();
            //TODO refresh data
        });
        close.addClickListener(e -> subWindow.close());
        addCompany.addComponents(name, address, tin, phone, save, close);
        subWindow.setContent(addCompany);
        subWindow.setModal(true);
        getUI().addWindow(subWindow);
    }

    public void editCompany(Company company) {
        Window subWindow = new Window("Edit Company");
        HorizontalLayout editCompany = new HorizontalLayout();
        Button save = new Button("Save");
        Button close = new Button("Close");
        TextField name = new TextField();
        TextField address = new TextField();
        TextField tin = new TextField();
        TextField phone = new TextField();

        name.setValue(company.getName());
        address.setValue(company.getAddress());
        tin.setValue(company.getTin().toString());
        phone.setValue(company.getPhone());
        save.addClickListener(e -> {
            Company updated = new Company(company.getId(), name.getValue(), address.getValue(), Long.valueOf(tin.getValue()), phone.getValue());
            companyService.update(updated);
            Notification.show("Company successfully edited", Notification.Type.TRAY_NOTIFICATION);
            //TODO refresh data
        });
        close.addClickListener(e -> subWindow.close());
        editCompany.addComponents(name, address, tin, phone, save, close);
        subWindow.setContent(editCompany);
        subWindow.setModal(true);
        getUI().addWindow(subWindow);
    }

    public void deleteCompany(Integer id) {
        companyService.delete(id);
        Notification.show("Company successfully deleted", Notification.Type.TRAY_NOTIFICATION);
    }

    public void setCompanyEdit(Company companyEdit) {
        this.companyEdit = companyEdit;
    }
}