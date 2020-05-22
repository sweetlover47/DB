package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tourist")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "insertTouristWithRandomAge",
                procedureName = "insert_tourist",
                parameters = {
                        @StoredProcedureParameter(
                                name = "fio",
                                type = String.class
                        ),
                        @StoredProcedureParameter(
                                name = "pass",
                                type = String.class
                        ),
                        @StoredProcedureParameter(
                                name = "sx",
                                type = String.class
                        )
                }
        )
})
public class Tourist {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator = "tourist_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "tourist_id_seq", sequenceName = "tourist_id_seq", allocationSize = 1)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String passport;

    @Getter
    @Setter
    private String sex;

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children;

    @Getter
    @Setter
    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL)
    private List<Passenger> passengerList;

    @Getter
    @Setter
    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL)
    private List<Trip> tripList;

}
