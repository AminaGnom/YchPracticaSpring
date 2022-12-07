package com.example.demo.repo;

import com.example.demo.models.Branch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BranchRepository extends CrudRepository<Branch, Long> {

    List<Branch>  findByAddress (String address);
}