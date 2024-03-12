package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.Category;

import com.posco.poscoproject.repository.CategoryRepository_API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService_API {

    private final CategoryRepository_API categoryRepositoryAPI;

    public void insertCol(Category category){
        categoryRepositoryAPI.save(category);
    }
}
