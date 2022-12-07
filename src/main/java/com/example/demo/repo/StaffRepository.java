package com.example.demo.repo;

import com.example.demo.models.Staff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface StaffRepository extends CrudRepository<Staff, Long> {

    List<Staff> findByFioContains (String fio);

    List<Staff> findByFio(String fio);

}
