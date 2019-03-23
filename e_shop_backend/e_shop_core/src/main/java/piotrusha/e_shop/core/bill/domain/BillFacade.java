package piotrusha.e_shop.core.bill.domain;

import io.vavr.control.Either;
import piotrusha.e_shop.core.base.AbstractFacade;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.core.bill.domain.exception.BillNotFoundException;

import java.util.List;

public class BillFacade extends AbstractFacade<Bill, BillActionDto> {

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

    public void cancelBill(BillActionDto billActionDto) {
        dtoValidator.validateDto(billActionDto);
        performAction(billCanceller::cancelBill, billActionDto);
    }

    public void payBill(BillActionDto billActionDto) {
        dtoValidator.validateDto(billActionDto);
        performAction(billPayer::payBill, billActionDto);
    }

    @Override
    protected Bill findEntity(BillActionDto billActionDto) {
        return billRepository.findByBillId(billActionDto.getBillId())
                             .getOrElseThrow(() -> new BillNotFoundException(billActionDto.getBillId()));
    }

    @Override
    protected void save(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    protected void saveAll(List<Bill> bills) {
        billRepository.saveAll(bills);
    }

}
