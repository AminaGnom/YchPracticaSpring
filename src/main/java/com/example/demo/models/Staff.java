package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;


@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Заполните поле ФИО")
    @Size(min = 2, max = 100,message = "Размер данного поля должен быть в диапазоне от 2 до 100")
//    @Pattern(regexp = "^[а-яА-Я]+$", message = "Вводить только кириллицу")
    private String fio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @Past
    private Date birth;

    @NotNull
    @Column(nullable = false)
    private Boolean work;

    @Min(value = 1000,message = "Минимальное значение 1000")
    private Double wages;

    @NotEmpty(message = "Заполните поле банковский номер")
    @Size(min = 16, max = 16, message = "Банковский номер содержит 16 цифр")
    private String bank;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Position position;
    public Staff() {
        wages = 0.0;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {

        this.id = id;
    }

    public String getFio() {
        return fio;
    }
    public void setFio (String fio) {
        this.fio = fio;
    }

    public Date getBirth() {
        return birth;
    }
    public void setBirth(Date birth) {

        this.birth = birth;
    }

    public Boolean getWork() {
        return work;
    }
    public void setWork (Boolean work) {

        this.work = work;
    }

    public double getWages () {
        return wages;
    }
    public void setWages (Double wages) {

        this.wages = wages;
    }

    public String getBank() {return bank;}
    public void setBank (String bank){

        this.bank=bank;
    }

}
