package com.example.swagger.repository;

import com.example.swagger.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Integer > {
    Page<BoardEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}