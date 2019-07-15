package piotrusha.e_shop.bill.domain;

import io.vavr.control.Option;
import piotrusha.e_shop.bill.domain.dto.BillDto;

import java.math.BigDecimal;
import java.util.List;

public interface BillRepository {

    Option<BillDto> findByBillId(BigDecimal id);

    List<BillDto> findBillByClientId(BigDecimal clientId);

    List<BillDto> findBillByClientIdAndBillState(BigDecimal clientId, String billState);

    Option<BigDecimal> findLastBillId();

    void add(BillDto dto);

    void update(BillDto dto);

}
