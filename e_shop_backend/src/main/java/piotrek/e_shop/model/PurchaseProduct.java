package piotrek.e_shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_products")
public class PurchaseProduct {

    @Id
    @GeneratedValue(generator = "purchase_products_sequencer", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="purchase_products_sequencer", sequenceName = "purchase_products_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal id;

    @Column(name = "purchase_price", nullable = false, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "pieces_number", nullable = false)
    private Integer piecesNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
