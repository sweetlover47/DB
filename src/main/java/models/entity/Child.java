package models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "child")
public class Child {
    @Id
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_parent")
    private Tourist parent;
}
