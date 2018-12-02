package piotrusha.e_shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import piotrusha.e_shop.core.model.PurchaseProduct;

import java.math.BigDecimal;

public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, BigDecimal> {

}
