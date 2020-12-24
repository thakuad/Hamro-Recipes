package com.hamrorecipes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "category")
public class CategoryModel {

    @Id
    private String id;
    private String categoryName;
    private String categoryImage;
    private String categoryId;
}
