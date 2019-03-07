package piotrusha.e_shop.core.bill.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BillConfiguration {

    @Bean
    BillFacade billFacade(BillRepository billRepository) {
        return new BillFacade();
    }

    BillFacade billFacade() {
        BillRepository billRepository = new InMemoryBillRepository();
        return billFacade(billRepository);
    }

}
