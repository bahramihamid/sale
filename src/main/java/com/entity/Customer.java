package com.entity;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Objects;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
    @Column(name = "id" , nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code" , nullable = false)
    private String code;
    @Column(name = "name" , nullable = false)
    private String name;
    @Column(name = "Email",nullable = true)
    private String Email;
    @Column(name = "mobile",nullable = true)
    private String mobile;
    @Column(name = "nationalId",nullable = true)
    private String nationalId;
    @Column(name = "address" , nullable = true)
    private String address;

    public Customer() {
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.getId() != null ? Objects.hashCode(this.id) : System.identityHashCode(this));
        return hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        final Customer other = (Customer) obj;
        return this.getId() != null && this.getId().equals(other.getId());
    }
}
