package piotrusha.e_shop.core.bill.domain;

import io.vavr.control.Either;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;

public class BillFacade {

    private final BillCreator billCreator;
    private final BillCanceller billCanceller;
    private final BillPayer billPayer;

    private final BillRepository billRepository;

    private final DtoValidator dtoValidator;

    public BillFacade(BillCreator billCreator, BillCanceller billCanceller, BillPayer billPayer, BillRepository billRepository,
                      DtoValidator dtoValidator) {
        this.billCreator = billCreator;
        this.billCanceller = billCanceller;
        this.billPayer = billPayer;
        this.billRepository = billRepository;
        this.dtoValidator = dtoValidator;
    }

    public Either<AppError, BillDto> createBill(CreateBillDto createBillDto) {
        return dtoValidator.validateDto(createBillDto)
                           .flatMap(billCreator::createBill)
                           .peek(billRepository::save)
                           .map(Bill::toDto);
    }

    public Either<AppError, BillDto> cancelBill(BillActionDto billActionDto) {
        return dtoValidator.validateDto(billActionDto)
                           .flatMap(billCanceller::cancelBill)
                           .peek(billRepository::save)
                           .map(Bill::toDto);
    }

    public Either<AppError, BillDto> payBill(BillActionDto billActionDto) {
        return dtoValidator.validateDto(billActionDto)
                           .flatMap(billPayer::payBill)
                           .peek(billRepository::save)
                           .map(Bill::toDto);
    }

}
