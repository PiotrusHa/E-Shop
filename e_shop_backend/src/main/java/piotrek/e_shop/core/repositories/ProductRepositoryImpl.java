package piotrek.e_shop.core.repositories;

import org.springframework.stereotype.Repository;
import piotrek.e_shop.api.repositories.ProductRepositoryCustom;
import piotrek.e_shop.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> findByCategoryName(String categoryName) {
        String query = "SELECT p FROM Product p "
                + "JOIN p.categories c "
                + "WHERE c.name = :categoryName";
        TypedQuery<Product> q = entityManager.createQuery(query, Product.class);
        q.setParameter("categoryName", categoryName);

        return q.getResultList();
    }

}
