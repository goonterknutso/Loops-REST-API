package edu.matc.loops.daos;

import edu.matc.loops.enitity.CoordinateObj;
import edu.matc.loops.persistance.SessionFactoryProvider;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 10/11/2016.
 */
public class CoordinateDao {

    public List<CoordinateObj> getAllCoordinateObj() {
        List<CoordinateObj> rms = new ArrayList<CoordinateObj>();
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        rms = session.createCriteria(CoordinateObj.class).list();
        return rms;
    }

    public CoordinateObj getCoordinateObj(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        CoordinateObj rm = (CoordinateObj) session.get(CoordinateObj.class, id);
        return rm;
    }

    public CoordinateObj insertUser(CoordinateObj rm) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(rm);
        session.getTransaction().commit();
        return rm;
    }

    public List<CoordinateObj> searchCoordinateObj(String fieldName, String searchVal) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(CoordinateObj.class);
        criteria.add(Restrictions.eq(fieldName, searchVal));
        return criteria.list();
    }

    public CoordinateObj deleteUser(int id) {
        CoordinateObj rm = getCoordinateObj(id);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(rm);
        session.getTransaction().commit();
        return rm;
    }

}
