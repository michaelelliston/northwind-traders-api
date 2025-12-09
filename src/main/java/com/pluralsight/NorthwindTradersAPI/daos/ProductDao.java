package com.pluralsight.NorthwindTradersAPI.daos;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class ProductDao {

    private static final String[] USER_INFO = {"root", "yearup"};
    private static final String DB_URL = "jdbc:mysql://localhost:3306/northwind";
    private final BasicDataSource dataSource;

    @Autowired
    public ProductDao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(USER_INFO[0]);
        dataSource.setUsername(USER_INFO[1]);
        this.dataSource = dataSource;
    }

    public ArrayList<Product> getAllProducts() {

        ArrayList<Product> products = new ArrayList<Product>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products\n" +
                     "JOIN northwind.categories\n" +
                     "ON northwind.categories.CategoryID = northwind.products.CategoryID;")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product(resultSet.getLong("ProductID"), resultSet.getString("ProductName"),
                            resultSet.getString("CategoryName"), resultSet.getDouble("UnitPrice"));

                    products.add(product);
                }
            }

            return products;

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }

        return null;
    }

    public Product getProductById(long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE ProductID = ?;")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();

                return new Product(resultSet.getLong("ProductID"), resultSet.getString("ProductName"),
                        resultSet.getString("CategoryName"), resultSet.getDouble("UnitPrice"));
            }

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }

        return null;
    }
}
