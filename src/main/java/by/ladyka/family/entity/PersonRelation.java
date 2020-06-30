package by.ladyka.family.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PersonRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Person parent;

    @JoinColumn(name = "child_id")
    @ManyToOne
    private Person child;

    private RelationType relation;


}
