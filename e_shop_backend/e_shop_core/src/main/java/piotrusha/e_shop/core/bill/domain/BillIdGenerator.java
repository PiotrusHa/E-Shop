package piotrusha.e_shop.core.bill.domain;

import java.math.BigDecimal;

class BillIdGenerator {

    private final BillRepository billRepository;

    BillIdGenerator(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    BigDecimal generate() {
        BigDecimal maxProductId = billRepository.findLastBillId()
                                                .getOrElse(BigDecimal.ZERO);
        return maxProductId.add(BigDecimal.ONE);
    }

}
