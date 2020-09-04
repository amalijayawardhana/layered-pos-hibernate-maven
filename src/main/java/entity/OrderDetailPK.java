package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class OrderDetailPK implements SuperEntity {
    private String orderId;
    private String itemCode;


}
