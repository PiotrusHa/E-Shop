package piotrusha.e_shop.core.bill.domain;

import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface BillRepository extends Repository<Bill, BigDecimal> {

    List<Bill> findAll();

    Optional<Bill> findByBillId(BigDecimal id);

    void save(Bill bill);

}
