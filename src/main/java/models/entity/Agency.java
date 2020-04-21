package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


/*
Вопрос в AgencyNames
 */
@Entity
@Table(name = "agency")
public class Agency {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="agency_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="agency_id_seq",sequenceName="agency_id_seq", allocationSize=1)
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private List<Excursion> excursions;

}

  /*@Getter
    @Setter
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    /*@JoinTable(name = "agency_excursion",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_excursion", referencedColumnName = "id")
    )*/