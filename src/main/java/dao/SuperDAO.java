package dao;

import db.HibernateUtil;
import entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface SuperDAO {

   public void setSession(Session session);

}
