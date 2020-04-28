package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tourist")
public class Tourist {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="tourist_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="tourist_id_seq",sequenceName="tourist_id_seq", allocationSize=1)
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

    /*
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rest_tourist",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_excursion", referencedColumnName = "id")
    )
    private List<Excursion> excursions;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cargo_tourist",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_cargo", referencedColumnName = "id")
    )
    private List<Cargo> cargos;
*/
    @Getter
    @Setter
    @OneToMany(mappedBy = "tourist")
    private List<Passenger> passengerList;

    @Getter
    @Setter
    @OneToMany(mappedBy = "tourist")
    private List<Trip> tripList;

}
