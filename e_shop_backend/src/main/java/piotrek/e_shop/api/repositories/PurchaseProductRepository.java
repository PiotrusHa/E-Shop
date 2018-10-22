package piotrek.e_shop.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import piotrek.e_shop.model.PurchaseProduct;

import java.math.BigDecimal;

public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, BigDecimal> {

}
