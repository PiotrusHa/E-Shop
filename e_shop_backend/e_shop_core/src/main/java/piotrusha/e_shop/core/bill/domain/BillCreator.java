package piotrusha.e_shop.core.bill.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto.CreateBillRecordDto;
import piotrusha.e_shop.core.product.domain.ProductFacade;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class BillCreator {

    private final ProductFacade productFacade;

    private final BillRepository billRepository;
    private final BillIdGenerator billIdGenerator;

    BillCreator(ProductFacade productFacade, BillRepository billRepository, BillIdGenerator billIdGenerator) {
        this.productFacade = productFacade;
        this.billRepository = billRepository;
        this.billIdGenerator = billIdGenerator;
    }

    Bill createBill(CreateBillDto dto) {
        Bill bill = create(dto);
        bookProducts(bill);
        save(bill);
        return bill;
    }

    private Bill create(CreateBillDto dto) {
        Set<BillRecord> billRecords = createBillRecords(dto.getRecords());

        BigDecimal generatedId = billIdGenerator.generate();

        BigDecimal priceSum = calculatePriceSum(billRecords);

        return new Bill().setBillId(generatedId)
                         .setClientId(dto.getClientId())
                         .setPriceSum(priceSum)
                         .setPurchaseDate(new Date())
                         .setPaymentExpirationDate(new Date())
                         .setBillState(BillState.WAITING_FOR_PAYMENT)
                         .setBillRecords(billRecords);
    }

    private Set<BillRecord> createBillRecords(List<CreateBillRecordDto> dtos) {
        return dtos.stream()
                   .map(this::createBillRecord)
                   .collect(Collectors.toSet());
    }

    private BillRecord createBillRecord(CreateBillRecordDto dto) {
        Tuple2<ProductDto, Integer> productAndPiecesNumber = Tuple.of(dto.getProductId(), dto.getPiecesNumber())
                                                                  .map1(productFacade::findProductByProductId)
                                                                  .map1(productOpt -> productOpt.getOrElseThrow(
                                                                          () -> new ProductNotFoundException(dto.getProductId())));

        return new BillRecord(productAndPiecesNumber._1.getProductId(), productAndPiecesNumber._2, productAndPiecesNumber._1.getPrice());
    }

    private BigDecimal calculatePriceSum(Set<BillRecord> billRecords) {
        return billRecords.stream()
                          .map(BillRecord::getPriceSum)
                          .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void bookProducts(Bill bill) {
        List<BookProductDto> bookProductDtos = createBookProductsDto(bill.getBillRecords());
        productFacade.bookProducts(bookProductDtos);
    }

    private List<BookProductDto> createBookProductsDto(Set<BillRecord> billRecords) {
        return billRecords.stream()
                          .map(record -> new BookProductDto(record.getProductId(), record.getPiecesNumber()))
                          .collect(Collectors.toList());
    }

    private void save(Bill bill) {
        billRepository.save(bill);
    }

}
