package piotrek.e_shop.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrek.e_shop.api.exceptions.EntityNotFoundException;
import piotrek.e_shop.api.services.CategoryService;
import piotrek.e_shop.api.services.ProductService;
import piotrek.e_shop.api.repositories.ProductRepository;
import piotrek.e_shop.model.Category;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Product> updateProductsPiecesNumber(List<PurchaseProduct> purchaseProducts) {
        purchaseProducts.forEach(purchaseProduct -> {
            Product product = purchaseProduct.getProduct();
            product.setAvailablePiecesNumber(product.getAvailablePiecesNumber() - purchaseProduct.getPiecesNumber());
            product.setSoldPiecesNumber(product.getSoldPiecesNumber() + purchaseProduct.getPiecesNumber());
        });
        List<Product> products = purchaseProducts.stream()
                                                 .map(PurchaseProduct::getProduct)
                                                 .collect(Collectors.toList());
        return productRepository.saveAll(products);
    }

}
