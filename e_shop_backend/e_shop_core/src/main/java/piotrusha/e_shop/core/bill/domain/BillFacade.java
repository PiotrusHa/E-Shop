package piotrusha.e_shop.core.bill.domain;

import piotrusha.e_shop.core.base.AbstractFacade;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.core.bill.domain.exception.BillNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public BillDto createBill(CreateBillDto createBillDto) {
        dtoValidator.validateDto(createBillDto);
        Bill bill = billCreator.createBill(createBillDto);
        return bill.toDto();
    }

    public void cancelBill(BillActionDto billActionDto) {
        dtoValidator.validateDto(billActionDto);
        performAction(billCanceller::cancelBill, billActionDto);
    }

    public void payBill(BillActionDto billActionDto) {
        dtoValidator.validateDto(billActionDto);
        performAction(billPayer::payBill, billActionDto);
    }

    public BillDto findBillByBillId(BigDecimal billId) {
        return billRepository.findByBillId(billId)
                             .orElseThrow(() -> new BillNotFoundException(billId))
                             .toDto();
    }

    public List<BillDto> findBillsByClientId(BigDecimal clientId) {
        return billRepository.findBillByClientId(clientId)
                             .stream()
                             .map(Bill::toDto)
                             .collect(Collectors.toList());
    }

    public List<BillDto> findBillsByClientIdAndBillState(BigDecimal clientId, String billState) {
        return billRepository.findBillByClientIdAndBillState(clientId, BillState.valueOf(billState))
                             .stream()
                             .map(Bill::toDto)
                             .collect(Collectors.toList());
    }

    @Override
    protected Bill findEntity(BillActionDto billActionDto) {
        return billRepository.findByBillId(billActionDto.getBillId())
                             .orElseThrow(() -> new BillNotFoundException(billActionDto.getBillId()));
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
