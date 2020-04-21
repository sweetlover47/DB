package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="hotel_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="hotel_id_seq",sequenceName="hotel_id_seq", allocationSize=1)
    private int id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
}
