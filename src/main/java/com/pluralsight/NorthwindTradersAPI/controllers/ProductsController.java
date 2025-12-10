package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.daos.ProductDao;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ProductsController {

    ProductDao dao;

    @Autowired
    public ProductsController(ProductDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public ArrayList<Product> getAllProducts() {

        return this.dao.getAllProducts();
    }

    @RequestMapping(path = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {

        return this.dao.getProductById(id);
    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {

        return dao.addProduct(product);
    }
}
