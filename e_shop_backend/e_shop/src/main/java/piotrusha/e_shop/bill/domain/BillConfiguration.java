package piotrusha.e_shop.bill.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import piotrusha.e_shop.bill.persistence.in_memory.InMemoryBillRepository;
import piotrusha.e_shop.product.domain.ProductFacade;

@Configuration
class BillConfiguration {

    @Bean
    BillFacade billFacade(ProductFacade productFacade, BillRepository billRepository) {
        BillIdGenerator billIdGenerator = new BillIdGenerator(billRepository);
        BillCreator billCreator = new BillCreator(productFacade, billIdGenerator);
        BillCanceller billCanceller = new BillCanceller(productFacade);
        BillPayer billPayer = new BillPayer(productFacade);

        DtoValidator dtoValidator = new DtoValidator(billRepository);
        return new BillFacade(billCreator, billCanceller, billPayer, billRepository, dtoValidator);
    }

    BillFacade billFacade(ProductFacade productFacade) {
        BillRepository billRepository = new InMemoryBillRepository();
        return billFacade(productFacade, billRepository);
    }

}
