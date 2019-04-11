package piotrusha.e_shop.bill.persistence.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface BillJpaRepository extends Repository<BillEntity, BigDecimal> {

    Optional<BillEntity> findByBillId(BigDecimal billId);

    @Query("SELECT max(b.id) FROM BillEntity b")
    Optional<BigDecimal> findMaxBillId();

    List<BillEntity> findByClientId(BigDecimal clientId);

    List<BillEntity> findByClientIdAndBillState(BigDecimal clientId, String billState);

    void save(BillEntity billEntity);

}
