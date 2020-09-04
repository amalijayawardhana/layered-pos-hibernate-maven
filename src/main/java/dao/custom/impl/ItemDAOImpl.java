package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import org.hibernate.Session;

import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    private Session session;
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Item find(String key)throws Exception {
            return session.get(Item.class,key);
    }

    @Override
    public void save(Item item)throws Exception {
        session.save(item);
    }

    @Override
    public void update(Item item) throws Exception{
            session.update(item);
    }

    @Override
    public void delete(String key)throws Exception {
            session.delete(session.get(Item.class,key));
    }

    @Override
    public List<Item> findAll()throws Exception {
            return session.createQuery("FROM entity.Item",Item.class).list();
    }

    public String getLastitemCode() throws Exception {
       return (String) session.createQuery("SELECT i.itemCode FROM entity.Item i ORDER BY i.itemCode DESC").setMaxResults(1).list().get(0);

    }
}
