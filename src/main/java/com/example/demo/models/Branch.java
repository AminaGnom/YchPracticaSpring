package com.example.demo.models;


import javax.persistence.*;

import java.util.List;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    @ManyToMany
    @JoinTable (name="shoes_branch",
            joinColumns=@JoinColumn (name="branch_id"),
            inverseJoinColumns=@JoinColumn(name="shoes_id"))
    private List<Shoes> shoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Shoes> getShoes() {
        return shoes;
    }

    public void setShoes(List<Shoes> shoes) {
        this.shoes = shoes;
    }
}

