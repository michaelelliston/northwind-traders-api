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

    public Category addCategory(Category category) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO categories (CategoryName, CategoryID)\n" +
                     "VALUES (?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setLong(2, category.getCategoryId());

            int rowsAffected = preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            generatedKey.next();

            System.out.println("Adding " + rowsAffected + " records to products.");

            long id = generatedKey.getLong(1);

            generatedKey.close();

            return getCategoryById(id);

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
        return null;
    }

    public void updateCategoryById(long id, Category category) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE categories SET CategoryName = ? WHERE CategoryID = ?;")) {

            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setLong(2, category.getCategoryId());

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

            if (rowsUpdated != 1) {
                System.err.println("Error. A problem occurred when updating a product: " + id);
                throw new RuntimeException("Number of rows updated didn't equal 1");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
    }

    public void deleteCategoryById(long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM categories WHERE CategoryID = ?;")) {

            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted != 1) {
                System.err.println("Error. A problem occurred when deleting a product: " + id);
                throw new RuntimeException("Number of rows deleted didn't equal 1");
            }

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
    }
}
