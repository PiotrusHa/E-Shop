package piotrusha.e_shop.core.bill.domain;

import io.vavr.Tuple2;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.exception.BillValidationException;
import piotrusha.e_shop.core.product.domain.ProductFacade;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;

import java.util.List;
import java.util.stream.Collectors;

class BillPayer {

    private final ProductFacade productFacade;

    BillPayer(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    Bill payBill(Tuple2<BillActionDto, Bill> billTuple) {
        Bill bill = billTuple._2;
        validateCanPay(bill);
        markProductsAsSold(bill);
        return markBillAsPaid(bill);
    }

    private void validateCanPay(Bill bill) {
        if (!bill.canPay()) {
            throw BillValidationException.cannotPay(bill.getBillState().toString());
        }
    }

    private void markProductsAsSold(Bill bill) {
        List<SellProductDto> sellProductDtos = createSellProductDtos(bill);
        productFacade.sellProducts(sellProductDtos);
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
