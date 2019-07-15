package piotrusha.e_shop.product.domain;

import io.vavr.Tuple2;
import piotrusha.e_shop.product.domain.dto.ModifyProductDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductModifier {

    Product modifyProduct(Tuple2<ModifyProductDto, Product> productToModify) {
        modify(productToModify._2, productToModify._1);
        return productToModify._2;
    }

    private void modify(Product product, ModifyProductDto dto) {
        if (dto.getProductPrice() != null) {
            product.modifyPrice(dto.getProductPrice());
        }
        if (dto.getProductAvailablePiecesNumber() != null) {
            product.modifyAvailablePiecesNumber(dto.getProductAvailablePiecesNumber());
        }
        if (dto.getProductDescription() != null) {
            product.modifyDescription(dto.getProductDescription());
        }
        if (dto.getProductCategoriesToAssign() != null && !dto.getProductCategoriesToAssign()
                                                              .isEmpty()) {
            product.assignCategories(convertToCategories(dto.getProductCategoriesToAssign()));
        }
        if (dto.getProductCategoriesToUnassign() != null && !dto.getProductCategoriesToUnassign()
                                                                .isEmpty()) {
            product.unassignCategories(convertToCategories(dto.getProductCategoriesToUnassign()));
        }
    }

    private List<Category> convertToCategories(List<String> categories) {
        return categories.stream()
                         .map(Category::new)
                         .collect(Collectors.toList());
    }

}
