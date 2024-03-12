package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

// 해당 클래스는 추후 분리될 예정
public interface ItemRepository_API extends JpaRepository<Item, Long> {
}
