package piotrusha.e_shop.core.bill.domain;

import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;

public class BillFacade {

    private final BillCreator billCreator;

    private final DtoValidator dtoValidator;

    public BillFacade(BillCreator billCreator, DtoValidator dtoValidator) {
        this.billCreator = billCreator;
        this.dtoValidator = dtoValidator;
    }

    public BillDto createBill(CreateBillDto createBillDto) {
        dtoValidator.validateDto(createBillDto);
        Bill bill = billCreator.createBill(createBillDto);
        return bill.toDto();
    }

}
