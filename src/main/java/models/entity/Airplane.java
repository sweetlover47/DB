package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airplane")
public class Airplane {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="airplane_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="airplane_id_seq",sequenceName="airplane_id_seq", allocationSize=1)
    private int id;

    @Getter
    @Setter
    private int seat_count;

    @Getter
    @Setter
    private float cargo_weight;

    @Getter
    @Setter
    private float volume_weight;

    @Getter
    @Setter
    private boolean is_cargoplane;

    @Getter
    @Setter
    @OneToMany(mappedBy = "airplane")
    private List<Flight> flightList;
}
