package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Table(name = "order_byu")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 1, message = "Значение не может быть меньше 1.")
    private String quantity;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Shoes shoes;

    @OneToMany (mappedBy = "order", fetch = FetchType.EAGER)
    private Collection<MakingOrder> makingOrders;


    public Order () {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }

    public Collection<MakingOrder> getMakingOrders() {
        return makingOrders;
    }

    public void setMakingOrders(Collection<MakingOrder> makingOrders) {
        this.makingOrders = makingOrders;
    }
}

