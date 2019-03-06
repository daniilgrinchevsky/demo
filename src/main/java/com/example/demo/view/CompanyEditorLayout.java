package com.example.demo.view;

import com.example.demo.model.Company;
import com.example.demo.service.CompanyService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
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

    private Binder<Company> binder = new Binder<>(Company.class);

    private Company bean = new Company();

    @Autowired
    private CompanyService companyService;

    @Autowired
    public void CompanyEditorLayout() {
        filter.setPlaceholder("Search by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
       // filter.addValueChangeListener();
        save.addClickListener(e -> saveCompany());
        edit.addClickListener(e -> {
            if(bean.getId() != null)
            editCompany(bean);
        });
        delete.addClickListener(e -> {
            if(bean.getId() != null)
            deleteCompany();
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
        binder.forField(name).bind("name");
        binder.forField(address).bind("address");
        binder.forField(tin).withNullRepresentation("").withConverter(new StringToLongConverter("Must be a number")).bind("tin");
        binder.forField(phone).bind("phone");
        binder.setBean(bean);
        name.clear();
        address.clear();
        tin.clear();
        phone.clear();
        save.addClickListener(e -> {
            companyService.create(bean);
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

        binder.forField(name).bind("name");
        binder.forField(address).bind("address");
        binder.forField(tin).bind("tin");
        binder.forField(phone).bind("phone");
        binder.setBean(bean);
        save.addClickListener(e -> {
            companyService.update(bean);
            subWindow.close();
            Notification.show("Company successfully edited", Notification.Type.TRAY_NOTIFICATION);
            //TODO refresh data
        });
        close.addClickListener(e -> subWindow.close());
        editCompany.addComponents(name, address, tin, phone, save, close);
        subWindow.setContent(editCompany);
        subWindow.setModal(true);
        getUI().addWindow(subWindow);
    }

    public void deleteCompany() {
        companyService.delete(bean.getId());
        Notification.show("Company successfully deleted", Notification.Type.TRAY_NOTIFICATION);
    }

    public void setBean(Company bean) {
        this.bean = bean;
    }
}