package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin
// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests
public class CategoriesController {

    private CategoryDao categoryDao;
    private ProductDao productDao;


    // create an Autowired controller to inject the categoryDao and ProductDao

    // add the appropriate annotation for a get action

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @GetMapping()
    public List<Category> getAll() {
        // find and return all categories
        return categoryDao.getAllCategories();
    }

    // add the appropriate annotation for a get action

    @GetMapping("{id}")
    public Category getById(@PathVariable int id) {
        // get the category by id
        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products


    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {

        return productDao.listByCategoryId(categoryId);
        // get a list of product by categoryId

    }

    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        try {
            return categoryDao.insert(category);
        } catch (Exception ex) {
            // insert the category
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }
    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function

    @PutMapping("{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int categoryId, @RequestBody Category category) {

        try {
            categoryDao.update(categoryId, category);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function

    @DeleteMapping("{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int categoryId) {

        try {
            var category = categoryDao.getById(categoryId);

            if (category == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            categoryDao.delete(categoryId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        // delete the category by id
    }
}


