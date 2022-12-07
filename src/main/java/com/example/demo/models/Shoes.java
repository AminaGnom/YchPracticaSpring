package com.example.demo.models;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shoes")
public class Shoes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Заполните поле бренд и наименование")
    @Size(min = 2, max = 50,message = "Размер данного поля должен быть в диапазоне от 2 до 50")
    private String brand;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @Past
    private Date releaseDate;
    @NotNull
    @Column(nullable = false)
    private Boolean season;

    @Min(value = 1,message = "Минимальное значение 1")
    private Double weight;

    @ManyToMany
    @JoinTable(name = "shoes_branch",
            joinColumns = @JoinColumn(name = "shoes_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id"))
    private List<Branch> branches;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Provider provider;

    @OneToMany(mappedBy = "shoes", cascade = CascadeType.ALL)
    private Collection<Order> orders;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Categories categories;


    @Min(value = 1000,message = "Минимальное значение 1000")
    private Double price;

    public Shoes(String brand, Date releaseDate, Boolean season, Double weight, List<Branch> branches, Provider provider, Collection<Order> orders, Article article, Categories categories, Double price) {
        this.brand = brand;
        this.releaseDate = releaseDate;
        this.season = season;
        this.weight = weight;
        this.branches = branches;
        this.provider = provider;
        this.orders = orders;
        this.article = article;
        this.categories = categories;
        this.price = price;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Shoes(){
        weight = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {

        this.brand = brand;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getSeason() {
        return season;
    }

    public void setSeason(Boolean season) {

        this.season = season;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {

        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

}
