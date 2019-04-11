package piotrusha.e_shop.bill.persistence.in_memory;

import io.vavr.control.Option;
import piotrusha.e_shop.bill.domain.BillRepository;
import piotrusha.e_shop.bill.domain.dto.BillDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBillRepository implements BillRepository {

    private Map<BigDecimal, BillDto> map = new ConcurrentHashMap<>();

    @Override
    public Option<BillDto> findByBillId(BigDecimal id) {
        return Option.of(map.get(id));
    }

    @Override
    public List<BillDto> findBillByClientId(BigDecimal clientId) {
        return map.values()
                  .stream()
                  .filter(bill -> bill.getClientId()
                                      .equals(clientId))
                  .collect(Collectors.toList());
    }

    @Override
    public List<BillDto> findBillByClientIdAndBillState(BigDecimal clientId, String billState) {
        return map.values()
                  .stream()
                  .filter(bill -> bill.getClientId().equals(clientId)
                                  && bill.getBillState().equals(billState))
                  .collect(Collectors.toList());
    }

    @Override
    public Option<BigDecimal> findLastBillId() {
        return Option.ofOptional(map.values()
                                    .stream()
                                    .map(BillDto::getBillId)
                                    .max(BigDecimal::compareTo));
    }

    @Override
    public void save(BillDto bill) {
        map.put(bill.getBillId(), bill);
    }

}
