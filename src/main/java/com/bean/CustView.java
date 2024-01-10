package com.bean;

import com.ejb.CustEjb;
import com.entity.Customer;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class CustView implements Serializable {

    private List<Customer> customers;

    private Set<Customer> deletedCustomers;
    private Customer customer;
    private boolean readOnly;
    private boolean readOnlySave;
    private boolean readOnlyEdit;
    private boolean readOnlyDelete;
    private boolean isClickDelete;
    private boolean readOnlyCreate;
    private boolean isClickEdit;
    private boolean isClickNew;
    private boolean readOnlyCancle;
    private boolean readOnlyCopy;
    private boolean isClickCopy;

    public boolean isReadOnlyCopy() {
        return readOnlyCopy;
    }

    public boolean isReadOnlyDelete() {
        return readOnlyDelete;
    }

    public void setReadOnlyDelete(boolean readOnlyDelete) {
        this.readOnlyDelete = readOnlyDelete;
    }

    public boolean isReadOnlyCreate() {
        return readOnlyCreate;
    }

    public void setReadOnlyCreate(boolean readOnlyCreate) {
        this.readOnlyCreate = readOnlyCreate;
    }

    public boolean isReadOnlySave() {
        return readOnlySave;
    }

    public void setReadOnlySave(boolean readOnlySave) {
        this.readOnlySave = readOnlySave;
    }

    public boolean isReadOnlyEdit() {
        return readOnlyEdit;
    }

    public void setReadOnlyEdit(boolean readOnlyEdit) {
        this.readOnlyEdit = readOnlyEdit;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }



    @PostConstruct
    public void init() {

        readOnly = true;
        readOnlySave = true;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyCreate = false;
        isClickEdit = false;
        isClickCopy = false;
        isClickNew = false;
        readOnlyDelete = true;
        isClickDelete = false;
        readOnlyCancle = true;
        deletedCustomers = new HashSet<Customer>();

    }


    @EJB
    private CustEjb custEjb;

    public List<Customer> getCustomers() {
        if(customers == null){
            customers = custEjb.getAllCutomers();
        }
        return customers;
    }

    public List<Customer> CustomerList(String q) {
        String queryLowerCase = q.toLowerCase();
        List<Customer> custList = custEjb.getAllCutomers();
        return custList.stream().filter(t -> t.getName().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
    }

    public void CreateCustomer() {
        customer = new Customer();
        readOnly = false;
        readOnlySave = false;
        isClickNew = true;
        readOnlyCancle = false;
        isClickCopy = false;
    }

    public void EditCustomer() {
        readOnly = false;
        readOnlySave = false;
        readOnlyCreate = true;
        isClickEdit = true;
        readOnlyCopy = true;
        readOnlyDelete = true;
        readOnlyCancle = false;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void save() {
        if (isClickNew) {
            custEjb.save(customer);
        }
        if (isClickEdit) {
            custEjb.update(customer);
        }
        if (isClickDelete) {
            Iterator<Customer> iterator = deletedCustomers.iterator();
            while (iterator.hasNext())
                custEjb.delete(iterator.next());
        }
        if (isClickCopy) {
            /*customers.forEach(cs-> {
                        if (customer.getCode().equals(cs.getCode())) {
                            FacesContext.getCurrentInstance().
                                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", " کد مشتری تکراریست"));
                        }});*/
            if (custEjb.customerCheckCode(customer)) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", " کد مشتری تکراریست"));
            }
            if (custEjb.customerCheckName(customer)) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", " نام مشتری تکراریست"));
            }
            if (!(custEjb.customerCheckCode(customer) && custEjb.customerCheckName(customer))) {
                custEjb.save(customer);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "ذخیره شد"));
            }
        }
        readOnly = true;
        readOnlyCancle = true;
        readOnlySave = true;
        readOnlyCreate = false;
        readOnlyEdit = true;
        customers  = null;
    }

    public void delete() {
        isClickDelete = true;
        readOnlyDelete = true;
        deletedCustomers.add(customer);
        customers.remove(customer);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "حذف شد"));
        readOnlyDelete = true;
        readOnlySave = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyCancle = false;
    }

    public void copy() {
        Customer oldCustomer = customer;
        customer = new Customer();
        customer.setName(oldCustomer.getName());
        customer.setCode(oldCustomer.getCode());

        isClickCopy = true;
        readOnlyCopy = true;
        readOnlySave = false;
        readOnly = false;
        readOnlyDelete = true;
        readOnlyEdit = true;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "کپی شد"));
    }

    public boolean isReadOnlyCancle() {
        return readOnlyCancle;
    }

    public void cancle() {
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlySave = true;
        readOnly = true;
        readOnlyCancle = true;
        /*Iterator<Customer> iteratorC = deletedCustomers.iterator();
        while (iteratorC.hasNext())
            customers.add(iteratorC.next());*/
        // بجای استفاده از ایتریتور از لامبدا استفاده کردیم
        // اضافه کردن اعضای حذف شده به لیست مشتریان
        deletedCustomers.forEach(dc -> customers.add(dc));
    }

    public void onRowSelect(SelectEvent event) {
        Customer customer1 = (Customer) event.getObject();
        FacesMessage msg = new FacesMessage("Customer ", String.valueOf(customer1.getCode()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyEdit = false;
        readOnlyCopy = false;
        readOnlyCreate = true;
        readOnlyDelete = false;

    }

    public void onRowUnselect(UnselectEvent event) {
        Customer customer1 = (Customer) event.getObject();
        FacesMessage msg = new FacesMessage("Customer Unselected", String.valueOf(customer1.getName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyCreate = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyDelete = true;
    }


}
