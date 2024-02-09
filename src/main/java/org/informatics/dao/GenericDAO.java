package org.informatics.dao;


import org.informatics.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.informatics.entity.DML;
import org.hibernate.Transaction;

import java.util.List;

public class GenericDAO {

    public static void upsertObject(Object object, DML dml) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            if (dml == DML.INSERT) {
                session.save(object);
            } else if (dml == DML.UPDATE) {
                session.update(object);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to perform upsert operation. Check logs for details.");
        }
    }


    public static void deleteObjects(List<?> objects) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            for (int i = 0; i < objects.size(); i++) {
                session.remove(objects.get(i));
                clearSessionStorage(i, session);
            }
            transaction.commit();
        }
    }

    public static <Type> Type getObjectById(Class<Type> entityType, long id) {
        Type object;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            object = session.get(entityType, id);
            transaction.commit();
        }
        return object;
    }


    private static void clearSessionStorage(Integer i, Session session) {
        if (i % 20 == 0) {
            session.flush();
            session.clear();
        }
    }
}

