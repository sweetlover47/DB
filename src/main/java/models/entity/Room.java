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
    private int id;

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
    @OneToOne(mappedBy = "room")
    private Trip trip;

    @Getter
    @Setter
    @OneToMany
    @JoinColumn(name = "id_transaction")
    private List<Transaction> transactionList;
}
