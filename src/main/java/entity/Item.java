package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Item implements SuperEntity {
    @Id
    @Column(name = "code")
    private String itemCode;
    private String description;
    private BigDecimal unitprice;
    private int qtyOnHand;


}
