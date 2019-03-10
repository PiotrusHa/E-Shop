package piotrusha.e_shop.core.bill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class CreateBillDto {

    private BigDecimal clientId;
    private List<CreateBillRecordDto> records;

    @Getter
    @AllArgsConstructor
    public static class CreateBillRecordDto {
        private BigDecimal productId;
        private Integer piecesNumber;
    }

}
