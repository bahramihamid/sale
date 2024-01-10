package com.bean;

import com.ejb.DetailEjb;
import com.ejb.FactEjb;
import com.entity.FactDetail;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@ViewScoped
public class DetailView implements Serializable {

    //پروپرتی جهت انتخاب یک ردیف اقلام
    private FactDetail selectedDetail;

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
    @EJB
    private FactEjb factEjb;

    @Inject
    private FactView factView;

    @EJB
    private DetailEjb detailEjb;

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

    }
    public void Create() {
        // ایجاد نمونه اقلام در همین جا و نه بعنوان پروپرتی
        FactDetail detail = new FactDetail();
        // ارتباط دوطرفه هدر و اقلام بخاطر ثبت همزمان هدر و اقلام
        factView.getHeader().getDetails().add(detail);
        detail.setHeader(factView.getHeader());

        readOnly = false;
        readOnlySave = false;
        isClickNew = true;
        readOnlyCancle = false;
        isClickCopy = false;
        readOnlyCreate = true;
    }

    public void Edit() {
        readOnly = false;
        readOnlySave = false;
        readOnlyCreate = true;
        isClickEdit = true;
        readOnlyCopy = true;
        readOnlyDelete = true;
        readOnlyCancle = false;
    }


    public void delete() {
        isClickDelete = true;
        readOnlyDelete = true;
        // انتخاب ردیف اقلام از طریق هدر
        factView.getHeader().getDetails().remove(selectedDetail);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "حذف شد"));
        readOnlyDelete = true;
        readOnlySave = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyCancle = false;
    }

    public void copy() {
        FactDetail oldFactDetail = selectedDetail;
        selectedDetail = new FactDetail();
        selectedDetail.setAmount(oldFactDetail.getAmount());
        selectedDetail.setFee(oldFactDetail.getFee());
        selectedDetail.setPrice(oldFactDetail.getPrice());
        selectedDetail.setGood(oldFactDetail.getGood());

        isClickCopy = true;
        readOnlyCopy = true;
        readOnlySave = false;
        readOnly = false;
        readOnlyDelete = true;
        readOnlyEdit = true;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "کپی شد"));
    }

    public void onRowSelect(SelectEvent event) {
        FactDetail detail1 = (FactDetail) event.getObject();
        FacesMessage msg = new FacesMessage("Detail ", String.valueOf(detail1.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyEdit = false;
        readOnlyCopy = false;
        readOnlyCreate = true;
        readOnlyDelete = false;

    }

    public void onRowUnselect(UnselectEvent event) {
        FactDetail detail1 = (FactDetail) event.getObject();
        FacesMessage msg = new FacesMessage("Detail Unselected", String.valueOf(detail1.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyCreate = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyDelete = true;
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

    public boolean isReadOnlyCancle() {
        return readOnlyCancle;
    }

    public boolean isReadOnlyCopy() {
        return readOnlyCopy;
    }
    public boolean isClickCopy() {
        return isClickCopy;
    }

    public FactDetail getSelectedDetail() {
        return selectedDetail;
    }

    public void setSelectedDetail(FactDetail selectedDetail) {
        this.selectedDetail = selectedDetail;
    }


}



