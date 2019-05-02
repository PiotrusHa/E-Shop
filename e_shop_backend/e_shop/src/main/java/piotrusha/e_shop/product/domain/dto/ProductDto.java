package piotrusha.e_shop.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private BigDecimal productId;
    private String name;
    private BigDecimal price;
    private Integer availablePiecesNumber;
    private Integer bookedPiecesNumber;
    private Integer soldPiecesNumber;
    private String description;
    private Set<String> categories;

}
