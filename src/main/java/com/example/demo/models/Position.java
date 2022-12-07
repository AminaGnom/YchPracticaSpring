package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Position {

    public Position(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Заполните поле должность")
    @Size(min = 2, max = 60,message = "Размер данного поля должен быть в диапазоне от 2 до 60")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Вводить только кириллицу")
    private String name;
    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER)
    private Collection<Staff> tenants;
    public Position(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
