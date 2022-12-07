package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;


@Entity
public class Categories {

    public Categories(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Заполните поле")
    private String type;


    @OneToMany(mappedBy = "categories", fetch=FetchType.EAGER)
    private Collection<Shoes> tenants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Shoes> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Shoes> tenants) {
        this.tenants = tenants;
    }

    public Categories(Long id, String type, Collection<Shoes> tenants) {
        this.type = type;
        this.tenants = tenants;
    }

}
