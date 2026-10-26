package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.CoverType;
import entity.Notebook;
import entity.PageStyleType;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class NotebookDao {
    // Додавання нового блокнота
    public void save(Notebook notebook) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(notebook);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // Пошук за ID
    public Optional<Notebook> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Notebook.class, id));
        }
    }

    // Оновлення
    public void update(Notebook notebook) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(notebook);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // Видалення блокнота за ID
    public void deleteById(Long id  ) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Notebook notebook = session.get(Notebook.class, id);
            if (notebook != null) {
                session.remove(notebook);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // Показати усі блокноти
    public List<Notebook> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Notebook", Notebook.class).list();
        }
    }

    // Усі країни виробники із зазначенням кількості блокнотів
    public List<Object[]> countNotebooksByCountry() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT n.country, COUNT(n) FROM Notebook n GROUP BY n.country";
            return session.createQuery(hql, Object[].class).list();
        }
    }

    // Назва виробника та кількість блокнотів кожного виробника
    public List<Object[]> countNotebooksByManufacturer() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT n.manufacturerName, COUNT(n) FROM Notebook n GROUP BY n.manufacturerName";
            return session.createQuery(hql, Object[].class).list();
        }
    }

    // Країна з найбільшою кількістю блокнотів
    public Object[] findCountryWithMaxNotebooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT n.country, COUNT(n) FROM Notebook n GROUP BY n.country ORDER BY COUNT(n) DESC";
            return session.createQuery(hql, Object[].class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }

    // Країна з найменшою кількістю блокнотів
    public Object[] findCountryWithMinNotebooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT n.country, COUNT(n) FROM Notebook n GROUP BY n.country ORDER BY COUNT(n) ASC";
            return session.createQuery(hql, Object[].class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }

    // Виробник з найбільшою кількістю блокнотів
    public Object[] findManufacturerWithMaxNotebooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT n.manufacturerName, COUNT(n) FROM Notebook n GROUP BY n.manufacturerName ORDER BY COUNT(n) DESC";
            return session.createQuery(hql, Object[].class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }

    // Виробник з найменшою кількістю блокнотів
    public Object[] findManufacturerWithMinNotebooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT n.manufacturerName, COUNT(n) FROM Notebook n GROUP BY n.manufacturerName ORDER BY COUNT(n) ASC";
            return session.createQuery(hql, Object[].class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }

    // Фільтр за типом обкладинки
    public List<Notebook> findByCoverType(CoverType coverType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Notebook n WHERE n.coverType = :coverType";
            return session.createQuery(hql, Notebook.class)
                    .setParameter("coverType", coverType)
                    .list();
        }
    }

    // Фільтр за конкретною країною
    public List<Notebook> findByCountry(String country) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Notebook n WHERE LOWER(n.country) = LOWER(:country)";
            return session.createQuery(hql, Notebook.class)
                    .setParameter("country", country)
                    .list();
        }
    }

    // Фільтр по зовнішньому вигляду сторінки
    public List<Notebook> findByPageStyle(PageStyleType pageStyle) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Notebook n WHERE n.pageStyle = :pageStyle";
            return  session.createQuery(hql, Notebook.class)
                    .setParameter("pageStyle", pageStyle)
                    .list();
        }
    }

    // Фільтр по кількості сторінок
    public List<Notebook> findByPageCountRange(int minPages, int maxPages) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Notebook n WHERE n.pageCount BETWEEN :minPages AND :maxPages";
            return session.createQuery(hql, Notebook.class)
                    .setParameter("minPages", minPages)
                    .setParameter("maxPages", maxPages)
                    .list();
        }
    }

    // Фільтр по тиражу
    public List<Notebook> findByMinCirculation(long minCirculation) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Notebook n WHERE n.circulation >= :minCirculation";
            return session.createQuery(hql, Notebook.class)
                    .setParameter("minCirculation", minCirculation)
                    .list();
        }
    }
}