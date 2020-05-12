package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cargo")
public class Cargo {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="cargo_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="cargo_id_seq",sequenceName="cargo_id_seq", allocationSize=1)
    private long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    private Warehouse warehouse;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_statement")
    private Statement statement;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_flight")
    private Flight flight;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_in;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_out;

    @Getter
    @Setter
    private String kind;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cargo_tourist",
            joinColumns = @JoinColumn(name = "id_cargo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_trip", referencedColumnName = "id")
    )
    private Trip ownerTrip;
}
