package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Article {

    public Article(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Заполните поле")
    private String number;

    @OneToOne(optional = true, mappedBy = "article")
    private Shoes owner;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Shoes getOwner() {
        return owner;
    }

    public void setOwner(Shoes owner) {
        this.owner = owner;
    }
}
