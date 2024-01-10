package com.bean;

import com.ejb.GoodsEjb;
import com.entity.Goods;
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
public class GoodView implements Serializable {


    private Goods good;
    private List<Goods> goods;
    private Set<Goods> deletedGoods;
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
    private GoodsEjb goodsEjb;

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

    public List<Goods> getGoods() {
        if (goods == null){
            goods = goodsEjb.getGoodsSet();
        }
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public Goods getGood() {
        return good;
    }


    public void setGood(Goods good) {
        this.good = good;
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
        deletedGoods = new HashSet<Goods>();
    }


    public List<Goods> GoodList(String q) {

        String queryLowerCase = q.toLowerCase();
        List<Goods> goodsList = goodsEjb.getGoodsSet();
        return goodsList.stream().filter(t -> t.getName().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());

    }


    public void create() {
        good = new Goods();
        readOnly = false;
        readOnlySave = false;
        isClickNew = true;
        readOnlyCancle = false;
        isClickCopy = false;
    }

    public void EditGood() {
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
            goodsEjb.save(good);
        }
        if (isClickEdit) {
            goodsEjb.update(good);
        }
        if (isClickDelete) {
            for (Goods goods2 : goods) {
                goodsEjb.delete(goods2);
            }

        }
        if (isClickCopy) {
            if (goodsEjb.checkGoodCode(good)) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", " کد کالا تکراریست"));
            }
            if (goodsEjb.checkGoodName(good)) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", " نام کالا تکراریست"));
            }
            if (!(goodsEjb.checkGoodCode(good) && goodsEjb.checkGoodName(good))) {
                goodsEjb.save(good);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "ذخیره شد"));
            }
        }
        readOnly = true;
        readOnlyCancle = true;
        readOnlySave = true;
        readOnlyCreate = false;
        readOnlyEdit = true;
        goods = null;
    }

    public void delete() {
        isClickDelete = true;
        readOnlyDelete = true;
        deletedGoods.add(good);
        goods.remove(good);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "حذف شد"));
        readOnlyDelete = true;
        readOnlySave = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyCancle = false;
    }

    public void copy() {
        Goods oldGood = good;
        good = new Goods();
        good.setName(oldGood.getName());
        good.setCode(oldGood.getCode());

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
        deletedGoods.forEach(dc -> goods.add(dc));
    }


    public void onRowSelect(SelectEvent event) {
        Goods goods1 = (Goods) event.getObject();
        FacesMessage msg = new FacesMessage("Good ", String.valueOf(goods1.getCode()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyEdit = false;
        readOnlyCopy = false;
        readOnlyCreate = true;
        readOnlyDelete = false;

    }

    public void onRowUnselect(UnselectEvent event) {
        Goods goods1 = (Goods) event.getObject();
        FacesMessage msg = new FacesMessage("Good Unselected", String.valueOf(goods1.getName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        readOnlyCreate = false;
        readOnlyEdit = true;
        readOnlyCopy = true;
        readOnlyDelete = true;
    }


}
