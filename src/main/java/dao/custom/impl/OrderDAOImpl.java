package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.Order;
import org.hibernate.Session;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {

   private Session session;
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public String getLastOrderId()throws Exception{
        return (String) session.createQuery("SELECT o.orderId FROM entity.Order o ORDER BY o.orderId DESC ").setMaxResults(1).list().get(0);

    }

    public List<Order> findAll()throws Exception{
           return session.createQuery("FROM entity.Order",Order.class).list();
    }

    @Override
    public Order find(String key)throws Exception {
            return session.get(Order.class,key);
    }

    @Override
    public void save(Order order)throws Exception {
            session.save(order);
    }

    @Override
    public void update(Order order) throws Exception{
            session.update(order);
    }

    @Override
    public void delete(String key)throws Exception {
            session.delete(session.get(Order.class,key));
    }
}
