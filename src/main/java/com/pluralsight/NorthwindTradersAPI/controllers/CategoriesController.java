package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.daos.CategoryDao;
import com.pluralsight.NorthwindTradersAPI.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class CategoriesController {

    CategoryDao dao;

    @Autowired
    public CategoriesController(CategoryDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public ArrayList<Category> getAllCategories() {

        return this.dao.getAllCategories();
    }

    @RequestMapping(path = "/categories/{id}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable long id) {

        return this.dao.getCategoryById(id);
    }

    @RequestMapping(path = "/categories", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {

        return dao.addCategory(category);
    }

    @RequestMapping(path = "/categories/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCategoryById(@PathVariable long id,@RequestBody Category category) {
        if (id != category.getCategoryId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The product ID in the path is different from the product ID in the body.");
        }

        this.dao.updateCategoryById(id, category);
    }

    @RequestMapping(path = "/categories/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable long id) {

        this.dao.deleteCategoryById(id);
    }
}

