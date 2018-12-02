package piotrusha.e_shop.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(generator = "categories_sequencer", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="categories_sequencer", sequenceName = "categories_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
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
