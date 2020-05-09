package models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;

@Entity(name = "trans")
public class Transaction {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="transaction_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="transaction_id_seq",sequenceName="transaction_id_seq", allocationSize=1)
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean is_income;

    @Getter
    @Setter
    private float sum;

    @Getter
    @Setter
    @OneToOne(mappedBy = "transaction")
    private Excursion excursion;

    @Getter
    @Setter
    @OneToOne(mappedBy = "transaction")
    private Room room;

    @Getter
    @Setter
    @OneToOne(mappedBy = "transaction")
    private Flight flight;

    @Getter
    @Setter
    @OneToOne(mappedBy = "transaction")
    private Statement statement;

}
