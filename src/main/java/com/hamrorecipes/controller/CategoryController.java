package com.hamrorecipes.controller;
import com.hamrorecipes.model.CategoryModel;
import com.hamrorecipes.service.CategoryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "updateCategory", method = {RequestMethod.POST})
    public CategoryModel createCategory(@RequestBody CategoryModel categoryModel){
        return categoryService.createCategory(categoryModel);
    }


    @RequestMapping(value = "updateCategory", method = {RequestMethod.PUT})
    public CategoryModel updateCategory(@RequestBody CategoryModel categoryModel){
        return categoryService.updateCategory(categoryModel);
    }

    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public List<CategoryModel> getAll(){
        return categoryService.getAll();
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public CategoryModel deleteCategory(@RequestBody CategoryModel categoryModel){
        return categoryService.deleteCategory(categoryModel);
    }

}
