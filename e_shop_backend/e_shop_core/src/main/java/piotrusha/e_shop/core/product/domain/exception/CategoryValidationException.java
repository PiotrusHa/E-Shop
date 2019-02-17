package piotrusha.e_shop.core.product.domain.exception;

public class CategoryValidationException extends RuntimeException {

    public static CategoryValidationException emptyCategoryName() {
        return new CategoryValidationException("Category name cannot be empty.");
    }

    public static CategoryValidationException categoryNameAlreadyExists(String name) {
        return new CategoryValidationException(String.format("Category with name %s already exists.", name));
    }

    private CategoryValidationException(String message) {
        super(message);
    }

}
