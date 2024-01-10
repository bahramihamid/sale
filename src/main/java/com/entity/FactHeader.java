package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
@Entity
@Table(name = "FactHeader")
public class FactHeader implements Serializable {
    @Column(name = "id" , nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no" , nullable = false)
    private String no;

    @Column(name = "factDate" , nullable = true)
    private String factDate;

    @OneToMany( mappedBy="header",orphanRemoval=true, cascade = CascadeType.ALL)
    private List<FactDetail> details = new ArrayList<FactDetail>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "Header_Cust", referencedColumnName = "id", nullable = false)}, foreignKey=@ForeignKey(name="FKHeader_Customer"))
    private Customer customers;

    public Customer getCustomers() {
        return customers;
    }

    public void setCustomers(Customer customer) {
        this.customers = customer;
    }


    public List<FactDetail> getDetails() {
        return details;
    }


    public void setDetails(List<FactDetail> details) {
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFactDate() {
        return factDate;
    }

    public void setFactDate(String factDate) {
        this.factDate = factDate;
    }

    public FactHeader() {
    }

    @Override
    public String toString() {
        return "FactHeader{" +
                "Number='" + no + '\'' +
                ", Date='" + factDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FactHeader)) {
            return false;
        }
        final FactHeader other = (FactHeader) obj;
        return this.getId() != null && this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.getId() != null ? Objects.hashCode(this.id) : System.identityHashCode(this));
        return hash;
    }

}
