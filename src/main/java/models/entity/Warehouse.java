package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "warehouse")
public class Warehouse {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="warehouse_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="warehouse_id_seq",sequenceName="warehouse_id_seq", allocationSize=1)
    private long id;

    @Column(name = "num_space")
    @Getter
    @Setter
    private int numberOfSpace;

    @Getter
    @Setter
    @OneToMany(mappedBy = "warehouse")
    private List<Cargo> cargoList;
}
