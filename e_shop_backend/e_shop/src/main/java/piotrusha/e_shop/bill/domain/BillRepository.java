package piotrusha.e_shop.bill.domain;

import io.vavr.control.Option;

import java.math.BigDecimal;
import java.util.List;

public interface BillRepository {

    Option<Bill> findByBillId(BigDecimal id);

    List<Bill> findBillByClientId(BigDecimal clientId);

    List<Bill> findBillByClientIdAndBillState(BigDecimal clientId, BillState billState);

    Option<BigDecimal> findLastBillId();

    void create(Bill bill);

    void update(Bill bill);

}
