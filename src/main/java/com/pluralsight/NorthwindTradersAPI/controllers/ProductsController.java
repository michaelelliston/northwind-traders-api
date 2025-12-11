package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.daos.ProductDao;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @RequestMapping(path = "/products/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateProductById(@PathVariable int id,@RequestBody Product product) {
        if (id != product.getProductId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The product ID in the path is different from the product ID in the body.");
        }

        this.dao.updateProductById(id, product);
    }

    @RequestMapping(path = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable int id) {

        this.dao.deleteProductById(id);
    }
}
