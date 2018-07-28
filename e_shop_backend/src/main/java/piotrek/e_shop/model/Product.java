package piotrek.e_shop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(generator = "products_sequencer", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="products_sequencer", sequenceName = "products_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "available_pieces_number", nullable = false)
    private Integer availablePiecesNumber;

    @Column(name = "sold_pieces_number")
    private Integer soldPiecesNumber;

    @Column(name = "price", nullable = false, scale = 2)
    private BigDecimal price;

    @Column(name = "extra_info", length = 200)
    private String extraInfo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<Category> categories;

    public Product() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailablePiecesNumber() {
        return availablePiecesNumber;
    }

    public void setAvailablePiecesNumber(Integer availablePiecesNumber) {
        this.availablePiecesNumber = availablePiecesNumber;
    }

    public Integer getSoldPiecesNumber() {
        return soldPiecesNumber;
    }

    public void setSoldPiecesNumber(Integer soldPiecesNumber) {
        this.soldPiecesNumber = soldPiecesNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
