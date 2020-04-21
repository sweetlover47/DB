package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cargo")
public class Cargo {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="cargo_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="cargo_id_seq",sequenceName="cargo_id_seq", allocationSize=1)
    private int id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    private Warehouse warehouse;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "id_statement")
    private Statement statement;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
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
}
