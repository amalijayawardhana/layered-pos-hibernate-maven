package dao.custom.impl;

import dao.custom.QueryDAO;
import entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class QueryDAOImpl implements QueryDAO {

    private Session session;
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public CustomEntity getCustomerDetail1(String orderId)throws Exception {
        return (CustomEntity) session.createNativeQuery("SELECT o.OrderID,c.CustomerName, c.customerId \n" +
                "FROM customer c INNER JOIN `order` o on c.CustomerID = o.CustomerID\n" +
                " WHERE o.OrderID =?",orderId).setResultTransformer(Transformers.aliasToBean(CustomEntity.class)).uniqueResult();
    }

    @Override
    public CustomEntity getCustomerDetail2(String customerId)throws Exception {

/*        return session.createQuery("SELECT new entity.CustomEntity(o.id,c.name,c.id) FROM entity.Customer c INNER JOIN " +
                "entity.Order o WITH c=o.customer WHERE o.customer.id =?",customerId).uniqueResult();*/
        return null;
    }

    @Override
    public CustomEntity getOrderDetail(String orderId)throws Exception {
        return (CustomEntity) session.createNativeQuery("SELECT o.orderId, o.orderDate, c.customerId, c.customerName, SUM(od.orderQty *od.unitprice) AS total\n" +
                "FROM `order` o INNER JOIN customer c ON o.CustomerID = c.CustomerID\n" +
                "                INNER JOIN orderdetail od on o.OrderID = od.OrderID WHERE o.OrderID=?", orderId).uniqueResult();
    }
}
