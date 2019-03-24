package piotrusha.e_shop.bill.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import piotrusha.e_shop.bill.domain.BillState;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface BillJpaRepository extends JpaRepository<BillEntity, BigDecimal> {

    @Query("SELECT max(b.id) FROM BillEntity b")
    Optional<BigDecimal> findMaxBillId();

    List<BillEntity> findByClientId(BigDecimal clientId);

    List<BillEntity> findByClientIdAndBillState(BigDecimal clientId, BillState billState);

}
