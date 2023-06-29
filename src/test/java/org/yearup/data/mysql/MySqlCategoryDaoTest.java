package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCategoryDaoTest extends BaseDaoTestClass {

    private MySqlCategoryDao dao;

    @BeforeEach
    public void setup() {
        dao = new MySqlCategoryDao(dataSource);
    }

    @Test
    public void getById_shouldReturn_theCorrectCategory() {
        // arrange
        int categoryId = 1;

        Category expected = new Category() {{
            setCategoryId(1);
            setName("Electronics");
            setDescription("Explore the latest gadgets and electronic devices.");
        }};
        // act
        Category actual = dao.getById(categoryId);

        // assert
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void update_shouldUpdate_theCorrectCategory() {
        // arrange
        Category updatedCategory = new Category() {{
            setCategoryId(2);
            setName("Fashion");
            setDescription("Testing");
        }};
        // act

        dao.update(updatedCategory.getCategoryId(), updatedCategory);

        Category actual = dao.getById(updatedCategory.getCategoryId());

        // assert
        assertEquals(updatedCategory.getDescription(), actual.getDescription());
    }

    @Test
    public void delete_shouldDelete_theCorrectCategoryId() {
        // Arrange
        Category deleteCategory = new Category();
        deleteCategory.setCategoryId(-3);
        deleteCategory.setName("Home & Kitchen");
        deleteCategory.setDescription("");

        // Act
        boolean deleted = dao.delete(deleteCategory.getCategoryId());

        // Assert
        assertTrue(deleted);
    }
}