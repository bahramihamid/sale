package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "FactDetail")
public class FactDetail implements Serializable {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "fee", nullable = false)
    private Double fee;
    @Column(name = "price", nullable = false)
    private Double price;
    //@ManyToOne(cascade = CascadeType.ALL)
    /*@JoinTable(name = "Detail_Good" ,
    joinColumns = {@JoinColumn(name = "detail_Id" , referencedColumnName = "id")
            },inverseJoinColumns = {@JoinColumn(name = "Good_id" , referencedColumnName = "id")})
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "Detail_good", referencedColumnName = "id", nullable = false)}, foreignKey=@ForeignKey(name="FKDetail_Goods"))
    private Goods good = new Goods();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "header", referencedColumnName = "id", nullable = false)}, foreignKey=@ForeignKey(name="FKDetail_Header"))
    private FactHeader header = new FactHeader();

    public void setGood(Goods goods) {
        this.good = goods;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public FactHeader getHeader() {
        return header;
    }

    public void setHeader(FactHeader factHeader) {
        this.header = factHeader;
    }

    public Goods getGood() {
        return good;
    }

    public FactDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {

        this.amount = amount;
        if (fee != null)
        {
            price = this.amount * fee;
        }
         else  {
            price = 0D;
        }
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
        if (amount != null)
        {
            price = this.amount * fee;
        }
        else  {
            price = 0D;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = getFee()*getAmount();
    }

    @Column(name = "uuid", nullable = true, length = 255)
    private String uuid;

    public String getUuid() {
        if (uuid == null) {
            setUuid();
        }
        return uuid;
    }

    public void setUuid() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "FactDetail{" +
                "amount=" + amount +
                ", fee=" + fee +
                ", price=" + price +
                '}';
    }
    // برای مواقعی که هنوز آی دی ایجاد نشده لازم می شود مثلا در جداول
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.getId() != null ? Objects.hashCode(this.id) : System.identityHashCode(this));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FactDetail)) {
            return false;
        }
        final FactDetail other = (FactDetail) obj;
        return this.getId() != null && this.getId().equals(other.getId());
    }
}
