package org.example.springbatch.repository;

import org.example.springbatch.entity.BatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchEntityRepository extends JpaRepository<BatchEntity, Long> {
    Page<BatchEntity> findAll(Pageable page);
}
