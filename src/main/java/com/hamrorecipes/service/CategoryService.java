package com.hamrorecipes.service;

import com.hamrorecipes.models.CategoryModel;
import com.hamrorecipes.repository.CategoryRepository;
import com.hamrorecipes.security.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    public static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> createCategory (CategoryModel categoryModel){
        if (categoryRepository.existsByName(categoryModel.getName())){
            logger.info(" category name : {} already exist", categoryModel.getName());
            return ResponseEntity.ok(new MessageResponse("Category Already Exist in the Server"));
        }else{

            categoryRepository.save(categoryModel);
            logger.debug("Saving category name : {}", categoryModel.getName());
            return ResponseEntity.ok(new MessageResponse("Successfully Save a new category"));

        }

    }
}
