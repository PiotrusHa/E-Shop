package piotrusha.e_shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.core.exception.EntityNotFoundException;
import piotrusha.e_shop.core.model.Category;
import piotrusha.e_shop.core.model.Product;
import piotrusha.e_shop.core.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Optional<Product> findById(BigDecimal id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public Product add(Product product) {
        prepareProductToAdd(product);
        return productRepository.save(product);
    }

    private void prepareProductToAdd(Product product) {
        product.setId(null);
        validateCategories(product);
    }

    private void validateCategories(Product product) {
        List<Category> categoriesFromDatabase = categoryService.validateCategories(product.getCategories());
        product.setCategories(categoriesFromDatabase);
    }

    @Override
    public List<Product> addAll(List<Product> products) {
        products.forEach(this::prepareProductToAdd);
        return productRepository.saveAll(products);
    }

    @Override
    public Product update(Product product) {
        if (product.getId() == null || !productRepository.findById(product.getId()).isPresent()) {
            throw new EntityNotFoundException(Product.class, product.getId());
        }
        validateCategories(product);
        return productRepository.save(product);
    }

}
