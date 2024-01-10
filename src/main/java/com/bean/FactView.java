package com.bean;

import com.ejb.FactEjb;
import com.entity.FactHeader;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Named
@ViewScoped
public class FactView implements Serializable {
    private FactHeader header;

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
    private List<FactHeader> headers;
    private Set<FactHeader> deletedHeaders;
    @EJB
    private FactEjb factEjb;

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
        deletedHeaders = new HashSet<FactHeader>();
    }

    public FactHeader getHeader() {
        return header;
    }

    public void setHeader(FactHeader header) {
        this.header = header;
        //جهت کلیک کردن روی ردیفی از لیست هدر ها
        if (header != null) {
            this.header = factEjb.finedFetch(header);
        }
    }

    public List<FactHeader> getHeaders() {
        // برای لود خودکارلیست فاکتورها ابتدا در خط 174 نال می کنیم و اینجا دوباره گت می کنیم
        if (headers == null) {
            headers = factEjb.getAllHeaders();
        }
        return headers;

    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isReadOnlySave() {
        return readOnlySave;
    }

    public boolean isReadOnlyEdit() {
        return readOnlyEdit;
    }

    public boolean isReadOnlyDelete() {
        return readOnlyDelete;
    }

    public boolean isClickDelete() {
        return isClickDelete;
    }

    public boolean isReadOnlyCreate() {
        return readOnlyCreate;
    }

    public boolean isClickEdit() {
        return isClickEdit;
    }

    public boolean isClickNew() {
        return isClickNew;
    }

    public boolean isReadOnlyCopy() {
        return readOnlyCopy;
    }

    public boolean isClickCopy() {
        return isClickCopy;
    }

    public void setHeaders(List<FactHeader> headers) {
        this.headers = headers;
    }


    public void CreateHeader() {
        header = new FactHeader();
        readOnly = false;
        readOnlySave = false;
        isClickNew = true;
        readOnlyCancle = false;
        isClickCopy = false;
        readOnlyCreate = true;
    }

    public void EditHeader() {
        readOnly = false;
        readOnlySave = false;
        readOnlyCreate = true;
        isClickEdit = true;
        readOnlyCopy = true;
        readOnlyDelete = true;
        readOnlyCancle = false;
    }


    public void save() {
        if (isClickNew) {
            factEjb.save(header);
        } else if (isClickEdit) {
            factEjb.update(header);
        } else if (isClickDelete) {
            Iterator<FactHeader> iterator = deletedHeaders.iterator();
            while (iterator.hasNext())
                factEjb.delete(iterator.next());
        } else if (isClickCopy) {
            /*customers.forEach(cs-> {
                        if (customer.getCode().equals(cs.getCode())) {
                            FacesContext.getCurrentInstance().
                                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", " کد مشتری تکراریست"));
                        }});*/
            factEjb.save(header);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "ذخیره شد"));
        }

        readOnly = true;
        readOnlyCancle = true;
        readOnlySave = true;
        readOnlyCreate = false;
        readOnlyEdit = true;
        headers = null;
    }

    public void delete() {
        isClickDelete = true;
        readOnlyDelete = true;
        deletedHeaders.add(header);
        headers.remove(header);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "حذف شد"));
        readOnlyDelete = true;
        readOnlySave = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyCancle = false;
    }

    public void copy() {
        FactHeader oldFactHeader = header;
        header = new FactHeader();
        header.setNo(oldFactHeader.getNo());
        header.setFactDate(oldFactHeader.getFactDate());

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
        deletedHeaders.forEach(dc -> headers.add(dc));
    }


    public void onRowSelect(SelectEvent event) {
        FactHeader header1 = (FactHeader) event.getObject();
        FacesMessage msg = new FacesMessage("Header ", String.valueOf(header1.getNo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyEdit = false;
        readOnlyCopy = false;
        readOnlyCreate = true;
        readOnlyDelete = false;

    }

    public void onRowUnselect(UnselectEvent event) {
        FactHeader header1 = (FactHeader) event.getObject();
        FacesMessage msg = new FacesMessage("Header Unselected", String.valueOf(header1.getNo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyCreate = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyDelete = true;
    }
}
