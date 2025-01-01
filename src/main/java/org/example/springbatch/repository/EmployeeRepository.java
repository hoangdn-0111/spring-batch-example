package org.example.springbatch.repository;

import org.example.springbatch.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Page<EmployeeEntity> findAll(Pageable page);

    Page<EmployeeEntity> findByBatchId(Long batchId, Pageable page);
}
