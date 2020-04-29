package models.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "excursion")
public class Excursion {
    @Id
    @Getter
    @Setter
    private Long id;

   // @Column(name = "id_agency")
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_agency")
    private Agency agency;

    @Getter
    @Setter
    @Column(name = "num_orders")
    private int numOrders;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /*
    @Getter
    @Setter
    @OneToOne

    private Transaction transaction;
*/
    @Getter
    @Setter
    private String title;

    /*
    @Getter
    @Setter
    @ManyToMany(mappedBy = "excursions")
    private List<Tourist> participatingTourists;

     */
    @Getter
    @Setter
    @ManyToMany(mappedBy = "excursions")
    private List<Trip> participatingTourists;
}


 /*@JoinTable(name = "agency_excursion",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_agency", referencedColumnName = "id")
    )*/