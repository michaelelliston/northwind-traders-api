package com.pluralsight.NorthwindTradersAPI.daos;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class CategoryDao {

    private final BasicDataSource dataSource;

    @Autowired
    public CategoryDao(@Value("${datasource.url}") String url,
                       @Value("${datasource.username}") String userName,
                       @Value("${datasource.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        this.dataSource = dataSource;
    }

    public ArrayList<Category> getAllCategories() {

        ArrayList<Category> categories = new ArrayList<Category>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM categories;")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Category category = new Category(resultSet.getLong("CategoryID"), resultSet.getString("CategoryName"));
                    categories.add(category);
                }
            }

            return categories;

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
        return null;
    }

    public Category getCategoryById(long id) {

        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM categories WHERE CategoryID = ?;")) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();

                return new Category(resultSet.getLong("CategoryID"), resultSet.getString("CategoryName"));
            }
        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
        return null;
    }
}
