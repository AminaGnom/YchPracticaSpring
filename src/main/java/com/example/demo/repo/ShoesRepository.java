package com.example.demo.repo;


import com.example.demo.models.Shoes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoesRepository extends CrudRepository<Shoes, Long> {

    List<Shoes> findByBrandContains(String brand); //не точный поиск

    Shoes findByBrand(String brand); //точный поиск



}