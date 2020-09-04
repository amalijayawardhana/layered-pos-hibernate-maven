package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import db.HibernateUtil;
import entity.Customer;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.CustomerTM;
import util.ItemTM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);

    public List<ItemTM> getAllItems() throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        List<Item> allItems = null;
        try {
            tx = session.beginTransaction();
            allItems = itemDAO.findAll();
            tx.commit();
        }catch (Throwable t){
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }

        ArrayList<ItemTM> items = new ArrayList<>();
        for (Object i : allItems) {
            Item item = (Item) i;
            items.add(new ItemTM(item.getItemCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitprice().doubleValue()));
        }
        return items;
    }

    public String getNewitemCode()throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        String lastitemCode = null;
        try {
            tx = session.beginTransaction();
            lastitemCode = itemDAO.getLastitemCode();
            tx.commit();
        }catch (Throwable t){
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }
        if (lastitemCode == null){
            return "I001";
        }else{
            int maxId=  Integer.parseInt(lastitemCode.replace("I",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }

    }

    public void saveItem(String code, String description, int qtyOnHand, double unitPrice)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
            tx.commit();
        }catch (Throwable t){
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }
    }

    public void deleteItem(String itemCode) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            itemDAO.delete(itemCode);
            tx.commit();
        }catch (Throwable t){
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }
    }

    public void updateItem(String description, int qtyOnHand, double unitPrice, String itemCode)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            itemDAO.update(new Item(itemCode, description, BigDecimal.valueOf(unitPrice),qtyOnHand));
            tx.commit();
        }catch (Throwable t){
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }
    }
}
