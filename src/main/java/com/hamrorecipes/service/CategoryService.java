package com.hamrorecipes.service;

import com.hamrorecipes.model.CategoryModel;
import com.hamrorecipes.repository.CategoryRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CategoryService {
    private static final Logger log = Logger.getLogger(CategoryService.class.getName());
    // Declare all the Repo here
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryModel createCategory(CategoryModel categoryModel){
        log.info("Creating Category: " + categoryModel.getCategoryName());

        if (StringUtils.isEmpty(categoryModel.getCategoryId())){
            String categoryId = UUID.randomUUID().toString();
            categoryModel.setCategoryId(categoryId);
        }
        return categoryRepository.save(categoryModel);
    }

    // update Category Details
    public CategoryModel updateCategory( CategoryModel categoryModel){
        return categoryRepository.save(categoryModel);
    }

    // get All Category in list
    public List<CategoryModel> getAll(){
        return categoryRepository.findAll();
    }

    // delete by Ids
    public CategoryModel deleteCategory(CategoryModel categoryModel){
      return categoryRepository.deleteCategoryModelById(categoryModel.getId());
    }

}
