package piotrusha.e_shop.core.bill.domain;

import io.vavr.Tuple2;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.exception.BillValidationException;
import piotrusha.e_shop.core.product.domain.ProductFacade;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;

import java.util.List;
import java.util.stream.Collectors;

class BillCanceller {

    private final ProductFacade productFacade;

    BillCanceller(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    Bill cancelBill(Tuple2<BillActionDto, Bill> billTuple) {
        Bill bill = billTuple._2;
        validateCanCancel(bill);
        cancelProductBooking(bill);
        return markBillAsCancelled(bill);
    }

    private void validateCanCancel(Bill bill) {
        if (!bill.canCancel()) {
            throw BillValidationException.cannotCancel(bill.getBillState().toString());
        }
    }

    private void cancelProductBooking(Bill bill) {
        List<CancelProductBookingDto> cancelDtos = createCancelProductBookingDto(bill);
        productFacade.cancelBooking(cancelDtos);
    }

    private List<CancelProductBookingDto> createCancelProductBookingDto(Bill bill) {
        return bill.getBillRecords()
                   .stream()
                   .map(billRecord -> new CancelProductBookingDto(billRecord.getProductId(), billRecord.getPiecesNumber()))
                   .collect(Collectors.toList());
    }

    private Bill markBillAsCancelled(Bill bill) {
        bill.markBillAsCancelled();
        return bill;
    }

}
