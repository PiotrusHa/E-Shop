package piotrusha.e_shop.bill.persistence.jpa;

import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.bill.domain.Bill;
import piotrusha.e_shop.bill.domain.BillRepository;
import piotrusha.e_shop.bill.domain.BillState;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
class BillJpaRepositoryAdapter implements BillRepository {

    private final BillJpaRepository repository;

    @Autowired
    BillJpaRepositoryAdapter(BillJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Option<Bill> findByBillId(BigDecimal id) {
        return Option.ofOptional(repository.findById(id)
                                           .map(BillEntity::toDomainBill));
    }

    @Override
    public List<Bill> findBillByClientId(BigDecimal clientId) {
        return toDomainBills(repository.findByClientId(clientId));
    }

    @Override
    public List<Bill> findBillByClientIdAndBillState(BigDecimal clientId, BillState billState) {
        return toDomainBills(repository.findByClientIdAndBillState(clientId, billState));
    }

    @Override
    public Option<BigDecimal> findLastBillId() {
        return Option.ofOptional(repository.findMaxBillId());
    }

    @Override
    public void create(Bill bill) {
        repository.save(BillEntity.fromDomainBill(bill, true));
    }

    @Override
    public void update(Bill bill) {
        repository.save(BillEntity.fromDomainBill(bill, false));
    }

    private List<Bill> toDomainBills(List<BillEntity> entities) {
        return entities.stream()
                       .map(BillEntity::toDomainBill)
                       .collect(Collectors.toList());
    }

}
