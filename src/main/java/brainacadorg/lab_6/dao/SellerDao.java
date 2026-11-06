package brainacadorg.lab_6.dao;

import brainacadorg.lab_6.entity.Seller;
import brainacadorg.lab_6.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class SellerDao extends BaseDao<Seller> {

    public SellerDao() {
        super(Seller.class);
    }

    public List<Seller> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Seller", Seller.class).list();
        }
    }

    public Seller getMostSuccessfulSeller() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s.seller FROM Sale s GROUP BY s.seller ORDER BY SUM(s.salePrice) DESC", Seller.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}