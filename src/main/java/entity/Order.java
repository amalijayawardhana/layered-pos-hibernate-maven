package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString(exclude = "OrderDetails")
@Entity
@Setter
@Getter
@Table(name = "`Order`")
public class Order implements SuperEntity {
    @Id
    @Column(name = "id")
    private String orderId;
    private Date orderDate;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "customerID",referencedColumnName = "id",nullable = false)
    private Customer customer;
    @OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<OrderDetail> orderDetails;

    public Order(String orderId, Date orderDate, Customer customer) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
    }

    public Order(String orderId, Date orderDate, Customer customer, List<OrderDetail> orderDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(this);
        }
        this.orderDetails = orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(this);
        }
        this.orderDetails = orderDetails;
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setOrder(this);
        this.getOrderDetails().add(orderDetail);
    }
}
