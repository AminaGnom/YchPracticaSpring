package com.example.demo.repo;

import com.example.demo.models.MakingOrder;
import org.springframework.data.repository.CrudRepository;

public interface MakingOrderRepository extends CrudRepository<MakingOrder, Long> {
}
