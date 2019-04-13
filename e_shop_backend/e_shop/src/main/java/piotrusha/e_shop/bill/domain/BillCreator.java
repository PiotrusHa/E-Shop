package piotrusha.e_shop.bill.domain;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.DateTimeProvider;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.product.domain.ProductFacade;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class BillCreator {

    private static final int PAYMENT_DAYS = 7;

    private final ProductFacade productFacade;
    private final DateTimeProvider dateTimeProvider;

    private final BillIdGenerator billIdGenerator;

    BillCreator(ProductFacade productFacade, DateTimeProvider dateTimeProvider, BillIdGenerator billIdGenerator) {
        this.productFacade = productFacade;
        this.dateTimeProvider = dateTimeProvider;
        this.billIdGenerator = billIdGenerator;
    }

    Either<AppError, Bill> createBill(CreateBillDto dto) {
        return bookProducts(dto.getRecords())
                .map(this::createBillRecords)
                .map(records -> createBill(dto.getClientId(), records));
    }

    private Either<AppError, List<Tuple2<CreateBillDto.CreateBillRecordDto, ProductDto>>> bookProducts(List<CreateBillDto.CreateBillRecordDto> records) {
        List<BookProductDto> bookProductDtos = records.stream()
                                                      .map(this::createBookProductDto)
                                                      .collect(Collectors.toList());
        return productFacade.bookProducts(bookProductDtos)
                            .map(productDtos -> zipLists(records, productDtos));
    }

    private BookProductDto createBookProductDto(CreateBillDto.CreateBillRecordDto record) {
        return new BookProductDto(record.getProductId(), record.getPiecesNumber());
    }

    private List<Tuple2<CreateBillDto.CreateBillRecordDto, ProductDto>> zipLists(List<CreateBillDto.CreateBillRecordDto> records, List<ProductDto> products) {
        return io.vavr.collection.List.ofAll(records)
                                      .zip(products)
                                      .asJava();
    }

    private Set<BillRecord> createBillRecords(List<Tuple2<CreateBillDto.CreateBillRecordDto, ProductDto>> records) {
        return records.stream()
                      .map(this::createBillRecord)
                      .collect(Collectors.toSet());
    }

    private BillRecord createBillRecord(Tuple2<CreateBillDto.CreateBillRecordDto, ProductDto> record) {
        return new BillRecord(record._1.getProductId(),
                              record._1.getPiecesNumber(),
                              record._2.getPrice());
    }

    private Bill createBill(BigDecimal clientId, Set<BillRecord> records) {
        BigDecimal generatedId = billIdGenerator.generate();
        BigDecimal priceSum = calculatePriceSum(records);

        return Bill.builder()
                   .billId(generatedId)
                   .clientId(clientId)
                   .priceSum(priceSum)
                   .purchaseDate(dateTimeProvider.currentDateTime())
                   .paymentExpirationDate(dateTimeProvider.currentDate().plusDays(PAYMENT_DAYS))
                   .billState(BillState.WAITING_FOR_PAYMENT)
                   .billRecords(records)
                   .build();
    }

    private BigDecimal calculatePriceSum(Set<BillRecord> billRecords) {
        return billRecords.stream()
                          .map(BillRecord::getPriceSum)
                          .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
