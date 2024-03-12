package com.posco.poscoproject.repository;


import com.posco.poscoproject.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SampleRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
}
