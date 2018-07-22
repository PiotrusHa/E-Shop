package piotrek.e_shop.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private BigDecimal id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "available_pieces_number", nullable = false)
    private Integer availablePiecesNumber;

    @Column(name = "sold_pieces_number")
    private Integer soldPiecesNumber;

    @Column(name = "price", nullable = false)
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

}
