package brainacadorg.lab_6.util;

import brainacadorg.lab_6.entity.Customer;
import brainacadorg.lab_6.entity.Product;
import brainacadorg.lab_6.entity.Sale;
import brainacadorg.lab_6.entity.Seller;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Seller.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Product.class)
                    .addAnnotatedClass(Sale.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Помилка ініціалізації SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
