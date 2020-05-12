package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "statement")
public class Statement {
    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="statement_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="statement_id_seq",sequenceName="statement_id_seq", allocationSize=1)
    private long id;

    @Getter
    @Setter
    private int count;

    @Getter
    @Setter
    private float weight;

    @Getter
    @Setter
    @Column(name = "cost_wrap")
    private float costWrap;

    @Getter
    @Setter
    @Column(name = "cost_insurance")
    private float costInsurance;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;

    @Getter
    @Setter
    @OneToMany(mappedBy = "statement")
    private List<Cargo> cargos;
}
