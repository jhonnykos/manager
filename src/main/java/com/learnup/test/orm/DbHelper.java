package com.learnup.test.orm;

import com.learnup.test.orm.entities.Day;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class DbHelper {
    SessionFactory sessionFactory;

    public DbHelper() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        final Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        this.sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public List<Day> getAllDays() {
        try (Session session = sessionFactory.openSession()) {
            final Day day = session.find(Day.class, 1);
            final Query<Day> result = session.createQuery("from Day", Day.class);
            return result.getResultList();
        }
    }

    public Integer getStepsByDay(Integer dayInt) {
        try (Session session = sessionFactory.openSession()) {
            final Day day = session.find(Day.class, dayInt);
            if (day != null) {
                return day.getSteps();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean addDay(Day day) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(day);
            session.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDay(Day day) {
        int steps = getStepsByDay(day.getDay());
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(new Day(day.getDay(), day.getSteps() + steps));
            session.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDay(Integer dayInt) {
        try (Session session = sessionFactory.openSession()) {
            Day dayForDelete = session.load(Day.class, dayInt);
            session.delete(dayForDelete);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }
    }
}
