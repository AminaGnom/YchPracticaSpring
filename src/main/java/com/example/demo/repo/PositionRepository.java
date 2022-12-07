package com.example.demo.repo;

import com.example.demo.models.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionRepository extends CrudRepository<Position, Long> {

    List<Position> findByName(String name);
}
