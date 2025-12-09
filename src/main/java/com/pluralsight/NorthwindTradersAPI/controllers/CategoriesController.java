package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.daos.CategoryDao;
import com.pluralsight.NorthwindTradersAPI.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}

