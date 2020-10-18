package by.ladyka.family.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String fathername;
    @Column(nullable = false)
    private LocalDateTime birthday;
    @Column(name = "dead_day")
    private LocalDateTime deadDay;

    private Boolean gender;
    // man -> true; female -> false

    private String username;

    @OneToMany(mappedBy = "parent")
    private List<PersonRelation> relationsParent = new ArrayList<>();
    @OneToMany(mappedBy = "child")
    private List<PersonRelation> relationsChild = new ArrayList<>();

    @ManyToMany(mappedBy = "persons")
    private List<Photo> photos = new ArrayList<>();

    public boolean isMan() {
        return gender;
    }

    public boolean isWoman() {
        return !gender;
    }
}
