package piotrusha.e_shop.bill.domain;

import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.ProductFacade;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

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
            return Either.left(AppError.cannotPayBill(bill.getBillState().toString()));
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
