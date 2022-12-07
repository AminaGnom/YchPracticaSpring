package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Collection;

@Entity
public class Provider {
    public Provider() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String organization;


@OneToMany (mappedBy = "provider", fetch=FetchType.EAGER)
private Collection<Shoes> tenants;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Collection<Shoes> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Shoes> tenants) {
        this.tenants = tenants;
    }

    public Provider(String organization, Collection<Shoes> tenants) {
        this.organization = organization;
        this.tenants = tenants;
    }
}