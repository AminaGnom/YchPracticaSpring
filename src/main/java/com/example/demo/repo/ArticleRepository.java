package com.example.demo.repo;

import com.example.demo.models.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findByNumber(String number);
}
