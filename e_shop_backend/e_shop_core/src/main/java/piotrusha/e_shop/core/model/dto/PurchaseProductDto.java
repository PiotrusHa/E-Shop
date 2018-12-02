package piotrusha.e_shop.core.model.dto;

import java.math.BigDecimal;

public class PurchaseProductDto {

    private BigDecimal productId;
    private Integer piecesNumber;

    public PurchaseProductDto() {
    }

    public PurchaseProductDto(BigDecimal productId, Integer piecesNumber) {
        this.productId = productId;
        this.piecesNumber = piecesNumber;
    }

    public BigDecimal getProductId() {
        return productId;
    }

    public void setProductId(BigDecimal productId) {
        this.productId = productId;
    }

    public Integer getPiecesNumber() {
        return piecesNumber;
    }

    public void setPiecesNumber(Integer piecesNumber) {
        this.piecesNumber = piecesNumber;
    }

}
