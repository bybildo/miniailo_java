package brainacadorg.lab_6.dao;

import brainacadorg.lab_6.entity.Product;
import brainacadorg.lab_6.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ProductDao extends BaseDao<Product> {

    public ProductDao() {
        super(Product.class);
    }

    public List<Product> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Product", Product.class).list();
        }
    }

    public Product getMostPopularProduct() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s.product FROM Sale s GROUP BY s.product ORDER BY COUNT(s.id) DESC", Product.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}
