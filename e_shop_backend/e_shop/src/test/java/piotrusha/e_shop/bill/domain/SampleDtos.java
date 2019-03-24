package piotrusha.e_shop.bill.domain;

import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

class SampleDtos {

    static CreateBillDto createBillDtoWithEmptyClientId() {
        return createBillDto().setClientId(null);
    }

    static CreateBillDto createBillDtoWithEmptyRecords() {
        return createBillDto().setRecords(null);
    }

    static CreateBillDto createBillDtoWithEmptyRecordProductId() {
        return createBillDto(List.of(createCreateBillRecordDto(null)));
    }

    static CreateBillDto createBillDtoWithEmptyPiecesNumber() {
        return createBillDtoWithPiecesNumber(null);
    }

    static CreateBillDto createBillDtoWithZeroPiecesNumber() {
        return createBillDtoWithPiecesNumber(0);
    }

    static CreateBillDto createBillDtoWithNegativePiecesNumber() {
        return createBillDtoWithPiecesNumber(-7);
    }

    static CreateBillDto createBillDto() {
        List<CreateBillDto.CreateBillRecordDto> billRecords = List.of(new CreateBillDto.CreateBillRecordDto(BigDecimal.TEN, 2),
                                                                      new CreateBillDto.CreateBillRecordDto(BigDecimal.ONE, 4));
        return new CreateBillDto(BigDecimal.ONE, billRecords);
    }

    static CreateBillDto createBillDto(List<CreateBillDto.CreateBillRecordDto> billRecords) {
        return new CreateBillDto(BigDecimal.ONE, billRecords);
    }

    private static CreateBillDto createBillDtoWithPiecesNumber(Integer piecesNumber) {
        return createBillDto(List.of(new CreateBillDto.CreateBillRecordDto(BigDecimal.ONE, piecesNumber)));
    }

    static CreateBillDto.CreateBillRecordDto createCreateBillRecordDto(BigDecimal productId) {
        return new CreateBillDto.CreateBillRecordDto(productId, 100);
    }

    static ProductDto createProductDtoForBillRecord(CreateBillDto.CreateBillRecordDto billRecordDto, BigDecimal price) {
        return new ProductDto().setProductId(billRecordDto.getProductId())
                               .setPrice(price);
    }

    static ProductDto createProductDtoForBillRecord(CreateBillDto.CreateBillRecordDto billRecordDto) {
        return createProductDtoForBillRecord(billRecordDto, BigDecimal.TEN);
    }

    static BillActionDto billActionDtoWithEmptyBillId() {
        return new BillActionDto(null);
    }

    static BillActionDto billActionDtoWithBillId(BigDecimal id) {
        return new BillActionDto(id);
    }

}
