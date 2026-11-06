package brainacadorg.lab_6.dao;

import brainacadorg.lab_6.entity.Sale;
import brainacadorg.lab_6.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class SaleDao extends BaseDao<Sale> {
    public SaleDao() {
        super(Sale.class);
    }

    public List<Sale> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s FROM Sale s JOIN FETCH s.seller JOIN FETCH s.customer JOIN FETCH s.product",
                    Sale.class
            ).list();
        }
    }

    public List<Sale> findByDate(LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s FROM Sale s JOIN FETCH s.seller JOIN FETCH s.customer JOIN FETCH s.product WHERE s.saleDate = :date", Sale.class)
                    .setParameter("date", date)
                    .list();
        }
    }

    public List<Sale> findByDateRange(LocalDate start, LocalDate end) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s FROM Sale s JOIN FETCH s.seller JOIN FETCH s.customer JOIN FETCH s.product WHERE s.saleDate BETWEEN :start AND :end", Sale.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .list();
        }
    }

    public List<Sale> findBySellerId(Long sellerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s FROM Sale s JOIN FETCH s.seller JOIN FETCH s.customer JOIN FETCH s.product WHERE s.seller.id = :sellerId", Sale.class)
                    .setParameter("sellerId", sellerId)
                    .list();
        }
    }

    public List<Sale> findByCustomerId(Long customerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT s FROM Sale s JOIN FETCH s.seller JOIN FETCH s.customer JOIN FETCH s.product WHERE s.customer.id = :customerId", Sale.class)
                    .setParameter("customerId", customerId)
                    .list();
        }
    }

    public Double getAverageSaleAmount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT AVG(s.salePrice) FROM Sale s", Double.class)
                    .uniqueResult();
        }
    }
}
