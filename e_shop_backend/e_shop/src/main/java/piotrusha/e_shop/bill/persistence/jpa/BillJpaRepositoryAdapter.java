package piotrusha.e_shop.bill.persistence.jpa;

import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.bill.domain.BillRepository;
import piotrusha.e_shop.bill.domain.dto.BillDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
class BillJpaRepositoryAdapter implements BillRepository {

    private final BillJpaRepository repository;
    private final BillRecordJpaRepository recordRepository;

    @Autowired
    BillJpaRepositoryAdapter(BillJpaRepository repository, BillRecordJpaRepository recordRepository) {
        this.repository = repository;
        this.recordRepository = recordRepository;
    }

    @Override
    public Option<BillDto> findByBillId(BigDecimal id) {
        return Option.ofOptional(repository.findByBillId(id)
                                           .map(BillEntity::toDto));
    }

    @Override
    public List<BillDto> findBillByClientId(BigDecimal clientId) {
        return toDtos(repository.findByClientId(clientId));
    }

    @Override
    public List<BillDto> findBillByClientIdAndBillState(BigDecimal clientId, String billState) {
        return toDtos(repository.findByClientIdAndBillState(clientId, billState));
    }

    @Override
    public Option<BigDecimal> findLastBillId() {
        return Option.ofOptional(repository.findMaxBillId());
    }

    @Override
    public void add(BillDto bill) {
        BillEntity billEntity = BillEntity.fromDto(bill);

        for (BillRecordEntity recordEntity : billEntity.getBillRecords()) {
            recordRepository.save(recordEntity);
        }
        repository.save(billEntity);
    }

    @Override
    public void update(BillDto bill) {
        BillEntity billEntity = BillEntity.fromDto(bill);
        repository.findByBillId(billEntity.getBillId())
                  .ifPresent(entity -> billEntity.setBillRecords(entity.getBillRecords()));
        repository.save(billEntity);
    }

    private List<BillDto> toDtos(List<BillEntity> entities) {
        return entities.stream()
                       .map(BillEntity::toDto)
                       .collect(Collectors.toList());
    }

}
