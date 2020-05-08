package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "trip")
public class Trip {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="trip_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="trip_id_seq",sequenceName="trip_id_seq", allocationSize=1)
    private int id;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private Timestamp date_in;

    @Getter
    @Setter
    private Timestamp date_out;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "hotel_room_id", referencedColumnName = "id")
    private Room room;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_tourist", referencedColumnName = "id")
    private Tourist tourist;

    @Column(name = "id_group")
    @Getter
    @Setter
    private int group;


    @Getter
    @Setter
    @OneToMany(mappedBy = "ownerTrip")
    private List<Cargo> cargos;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rest_tourist",
            joinColumns = @JoinColumn(name = "id_trip", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_excursion", referencedColumnName = "id")
    )
    private List<Excursion> excursions;
}
