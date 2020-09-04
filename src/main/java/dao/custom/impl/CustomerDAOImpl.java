package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import org.hibernate.Session;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private Session session;
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Customer find(String key) throws Exception {
        return session.get(Customer.class,key);
    }

    @Override
    public void save(Customer customer)throws Exception {
        session.save(customer);
    }

    @Override
    public void update(Customer customer)throws Exception {
        session.update(customer);
    }

    @Override
    public void delete(String key) throws Exception{
        session.delete(session.load(Customer.class,key));
    }

    public String getLastCustomerId()throws Exception{
       return (String) session.createNativeQuery("SELECT id FROM Customer ORDER BY id DESC  LIMIT 1").uniqueResult();
    }

    @Override
    public List<Customer> findAll()throws Exception{
        return session.createQuery("FROM entity.Customer",Customer.class).list();
    }
}


