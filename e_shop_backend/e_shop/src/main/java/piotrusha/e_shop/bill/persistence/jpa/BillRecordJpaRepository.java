package piotrusha.e_shop.bill.persistence.jpa;

import org.springframework.data.repository.Repository;

import java.math.BigDecimal;

interface BillRecordJpaRepository extends Repository<BillRecordEntity, BigDecimal> {

    void save(BillRecordEntity entity);

}
