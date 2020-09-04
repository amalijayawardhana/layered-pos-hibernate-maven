package dao.custom.impl;

import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import entity.OrderDetailPK;
import org.hibernate.Session;

import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    private Session session;
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public List<OrderDetail> findAll()throws Exception{
            return session.createQuery("FROM entity.OrderDetail",OrderDetail.class).list();
    }

    @Override
    public OrderDetail find(OrderDetailPK key)throws Exception {
       return session.get(OrderDetail.class,key);
    }

    @Override
    public void save(OrderDetail OrderDetail) throws Exception{
            session.save(OrderDetail);
    }

    @Override
    public void update(OrderDetail OrderDetail)throws Exception {
        session.update(OrderDetail);
    }

    @Override
    public void delete(OrderDetailPK orderDetailPK)throws Exception {
            session.delete(session.get(OrderDetail.class,orderDetailPK));
    }

}
