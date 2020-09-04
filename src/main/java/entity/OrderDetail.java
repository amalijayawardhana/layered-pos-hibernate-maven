package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class OrderDetail implements SuperEntity {
    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    private int orderQty;
    private BigDecimal unitprice;
    @ManyToOne
    @JoinColumn(name = "OrderId",referencedColumnName = "id",insertable = false,updatable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "itemcode", referencedColumnName = "code",insertable = false,updatable = false)
    private Item item;


    public OrderDetail(String orderId, String itemCode, int orderQty, BigDecimal unitprice) {
        this.orderDetailPK = new OrderDetailPK(orderId,itemCode);
        this.orderQty = orderQty;
        this.unitprice = unitprice;
    }



}
