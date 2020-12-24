package com.hamrorecipes.repository;

import com.hamrorecipes.model.CategoryModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface CategoryRepository extends MongoRepository<CategoryModel, String> {

    CategoryModel deleteCategoryModelById(String id);

}
