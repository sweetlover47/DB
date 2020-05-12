package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="passengers_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="passengers_id_seq",sequenceName="passengers_id_seq", allocationSize=1)
    private long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_flight")
    private Flight flight;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_tourist")
    private Tourist tourist;
}
