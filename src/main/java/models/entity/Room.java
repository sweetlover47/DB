package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="room_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="room_id_seq",sequenceName="room_id_seq", allocationSize=1)
    private long id;

    @Getter
    @Setter
    @Column(name = "room_number")
    private int roomNumber;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @Getter
    @Setter
    @OneToMany(mappedBy = "room")
    private List<Trip> tripList;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;
}
