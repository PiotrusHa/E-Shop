package piotrek.e_shop.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.BillState;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, BigDecimal> {

    List<Bill> findByClientId(BigDecimal clientId);

    List<Bill> findByStateAndPaymentExpirationDateBefore(BillState state, Date paymentExpirationLimitDate);

}
