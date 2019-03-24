package piotrusha.e_shop.core.bill.domain;

import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.util.List;

interface BillRepository extends Repository<Bill, BigDecimal> {

    List<Bill> findAll();

    Option<Bill> findByBillId(BigDecimal id);

    List<Bill> findBillByClientId(BigDecimal clientId);

    List<Bill> findBillByClientIdAndBillState(BigDecimal clientId, BillState billState);

    @Query("SELECT max(b.id) FROM Bill b")
    Option<BigDecimal> findLastBillId();

    void save(Bill bill);

    void saveAll(List<Bill> bills);

}
