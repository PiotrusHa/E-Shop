package piotrusha.e_shop.core.bill.domain;

import io.vavr.control.Either;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.product.domain.ProductFacade;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;

import java.util.List;
import java.util.stream.Collectors;

class BillPayer {

    private final ProductFacade productFacade;

    BillPayer(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    Either<AppError, Bill> payBill(Bill bill) {
        return validateCanPay(bill)
                .flatMap(this::markProductsAsSold)
                .map(this::markBillAsPaid);
    }

    private Either<AppError, Bill> validateCanPay(Bill bill) {
        if (!bill.canPay()) {
            return Either.left(AppError.validation("Cannot pay bill with state " + bill.getBillState()));
        }
        return Either.right(bill);
    }

    private Either<AppError, Bill> markProductsAsSold(Bill bill) {
        List<SellProductDto> sellProductDtos = createSellProductDtos(bill);
        return productFacade.sellProducts(sellProductDtos)
                            .map(x -> bill);
    }

    private List<SellProductDto> createSellProductDtos(Bill bill) {
        return bill.getBillRecords()
                   .stream()
                   .map(billRecord -> new SellProductDto(billRecord.getProductId(), billRecord.getPiecesNumber()))
                   .collect(Collectors.toList());
    }

    private Bill markBillAsPaid(Bill bill) {
        bill.markBillAsPaid();
        return bill;
    }

}
