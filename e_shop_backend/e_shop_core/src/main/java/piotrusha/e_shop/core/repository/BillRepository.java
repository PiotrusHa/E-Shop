package piotrusha.e_shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piotrusha.e_shop.core.model.Bill;
import piotrusha.e_shop.core.model.BillState;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, BigDecimal> {

    List<Bill> findByClientId(BigDecimal clientId);

    List<Bill> findByClientIdAndState(BigDecimal clientId, BillState state);

    List<Bill> findByStateAndPaymentExpirationDateBefore(BillState state, Date paymentExpirationLimitDate);

}
