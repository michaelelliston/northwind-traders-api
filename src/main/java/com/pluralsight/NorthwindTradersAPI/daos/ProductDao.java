package com.pluralsight.NorthwindTradersAPI.daos;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class ProductDao {

    private final BasicDataSource dataSource;

    @Autowired
    public ProductDao(@Value("${datasource.url}") String url,
                      @Value("${datasource.username}") String userName,
                      @Value("${datasource.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
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
                    Product product = new Product(resultSet.getInt("ProductID"), resultSet.getString("ProductName"),
                            resultSet.getString("CategoryName"), resultSet.getDouble("UnitPrice"), resultSet.getInt("CategoryID"));

                    products.add(product);
                }
            }

            return products;

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }

        return null;
    }

    public Product getProductById(int id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products\n" +
                     "JOIN northwind.categories\n" +
                     "ON northwind.categories.CategoryID = northwind.products.CategoryID\n" +
                     "WHERE ProductID = ?;")) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();

                return new Product(resultSet.getInt("ProductID"), resultSet.getString("ProductName"),
                        resultSet.getString("CategoryName"), resultSet.getDouble("UnitPrice"), resultSet.getInt("CategoryID"));
            }

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }

        return null;
    }

    public Product addProduct(Product product) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (ProductName, CategoryID, UnitPrice)\n" +
                     "VALUES (?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setInt(2, product.getCategoryId());
            preparedStatement.setDouble(3, product.getUnitPrice());

            int rowsAffected = preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            generatedKey.next();

            System.out.println("Adding " + rowsAffected + " records to products.");

            int id = generatedKey.getInt(1);

            generatedKey.close();

            return getProductById(id);

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
        return null;
    }
}
