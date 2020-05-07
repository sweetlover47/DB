package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="flight_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="flight_id_seq",sequenceName="flight_id_seq", allocationSize=1)
    long id;

    @Getter
    @Setter
    @OneToOne
    private Transaction transaction;

    @Getter
    @Setter
    private int id_group;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Getter
    @Setter
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Cargo> cargoList;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_airplane")
    private Airplane airplane;

    @Getter
    @Setter
    @OneToMany(mappedBy = "flight")
    private List<Passenger> passengerList;
}
