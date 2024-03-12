package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository_API extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
}
