package piotrusha.e_shop.core.bill.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
    public List<Bill> findBillByClientId(BigDecimal clientId) {
        return map.values()
                  .stream()
                  .filter(bill -> bill.getClientId()
                                      .equals(clientId))
                  .collect(Collectors.toList());
    }

    @Override
    public List<Bill> findBillByClientIdAndBillState(BigDecimal clientId, BillState billState) {
        return map.values()
                  .stream()
                  .filter(bill -> bill.getClientId().equals(clientId)
                                  && bill.getBillState().equals(billState))
                  .collect(Collectors.toList());
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


    @Override
    public void saveAll(List<Bill> bills) {
        bills.forEach(this::save);
    }

}
