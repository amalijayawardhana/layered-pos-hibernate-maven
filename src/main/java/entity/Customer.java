package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;

}
