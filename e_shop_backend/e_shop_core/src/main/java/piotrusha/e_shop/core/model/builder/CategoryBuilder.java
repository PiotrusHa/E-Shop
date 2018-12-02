package piotrusha.e_shop.core.model.builder;

import piotrusha.e_shop.core.model.Category;

import java.math.BigDecimal;

public class CategoryBuilder {

    private Category category;

    public CategoryBuilder(String name) {
        category = new Category();
        category.setName(name);
    }

    public CategoryBuilder id(BigDecimal id) {
        category.setId(id);
        return this;
    }

    public Category build() {
        return category;
    }

}
