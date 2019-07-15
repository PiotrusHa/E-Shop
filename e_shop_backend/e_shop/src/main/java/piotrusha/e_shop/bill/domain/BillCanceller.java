package piotrusha.e_shop.bill.domain;

import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.ProductFacade;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;

import java.util.List;
import java.util.stream.Collectors;

class BillCanceller {

    private final ProductFacade productFacade;

    BillCanceller(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    Either<AppError, Bill> cancelBill(Bill bill) {
        return validateCanCancel(bill)
                .flatMap(this::cancelProductBooking)
                .map(this::markBillAsCancelled);
    }

    private Either<AppError, Bill> validateCanCancel(Bill bill) {
        if (!bill.canCancel()) {
            return Either.left(AppError.cannotCancelBill(bill.getBillState().toString()));
        }
        return Either.right(bill);
    }

    private Either<AppError, Bill> cancelProductBooking(Bill bill) {
        List<CancelProductBookingDto> cancelDtos = createCancelProductBookingDto(bill);
        return productFacade.cancelBooking(cancelDtos)
                            .map(x -> bill);
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
