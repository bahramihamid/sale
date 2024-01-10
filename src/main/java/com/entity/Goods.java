package com.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Goods")
public class Goods implements Serializable {
    @Column(name = "id" ,nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code" , nullable = false)
    private String code;
    @Column(name = "name" , nullable = false)
    private String name;
    @Column(name = "unitRate" , nullable = true)
    private String unitRate;
//    @ManyToMany(mappedBy = "")
    public Goods() {
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

    public String getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(String unitRate) {
        this.unitRate = unitRate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Goods)) {
            return false;
        }
        final Goods other = (Goods) obj;
        return this.getId() != null && this.getId().equals(other.getId());
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.getId() != null ? java.util.Objects.hashCode(this.id) : System.identityHashCode(this));
        return hash;
    }

}

