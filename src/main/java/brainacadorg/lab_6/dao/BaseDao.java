package brainacadorg.lab_6.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import brainacadorg.lab_6.util.HibernateUtil;

public abstract class BaseDao<T> {
    private final Class<T> aClass;

    protected BaseDao(Class<T> aClass) {
        this.aClass = aClass;
    }

    public void save(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    public void update(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        }
    }

    public T findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(aClass, id);
        }
    }
}