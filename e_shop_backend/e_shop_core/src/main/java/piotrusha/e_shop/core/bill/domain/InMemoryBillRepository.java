package piotrusha.e_shop.core.bill.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryBillRepository implements BillRepository {

    private Map<BigDecimal, Bill> map = new ConcurrentHashMap<>();

    @Override
    public List<Bill> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Bill> findByBillId(BigDecimal id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<BigDecimal> findLastBillId() {
        return map.values()
                  .stream()
                  .map(Bill::getBillId)
                  .max(BigDecimal::compareTo);
    }

    @Override
    public void save(Bill bill) {
        map.put(bill.getBillId(), bill);
    }

}
