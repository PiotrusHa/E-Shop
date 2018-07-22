package piotrek.e_shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private BigDecimal id;

    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;

    public Category() {
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

}
