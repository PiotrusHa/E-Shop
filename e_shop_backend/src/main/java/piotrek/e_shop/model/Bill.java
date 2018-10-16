package piotrek.e_shop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(generator = "bills_sequencer", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="bills_sequencer", sequenceName = "bills_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal id;

    @Column(name = "price_sum", nullable = false, scale = 2)
    private BigDecimal priceSum;

    @Column(name = "state", nullable = false)
    private BillState state;

    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_expiration_date", nullable = false)
    private Date paymentExpirationDate;

    @Column(name = "client_id", nullable = false)
    private BigDecimal clientId;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id")
    private List<PurchaseProduct> purchaseProducts;

}
