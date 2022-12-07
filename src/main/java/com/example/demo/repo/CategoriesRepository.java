package com.example.demo.repo;

import com.example.demo.models.Categories;
import org.springframework.data.repository.CrudRepository;

public interface CategoriesRepository extends CrudRepository<Categories, Long>{
    Categories findByType (String type);

}
