package com.hamrorecipes.repository;

import com.hamrorecipes.models.CategoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CategoryRepository extends MongoRepository<CategoryModel, String> {
    Boolean existsByName(String name);
}
