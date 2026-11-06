package brainacadorg.lab_6.dao;

import brainacadorg.lab_6.entity.Customer;
import brainacadorg.lab_6.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class CustomerDao extends BaseDao<Customer> {

    public CustomerDao() {
        super(Customer.class);
    }

    public List<Customer> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        }
    }

    public Customer getMostSuccessfulCustomer() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s.customer FROM Sale s GROUP BY s.customer ORDER BY SUM(s.salePrice) DESC", Customer.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}