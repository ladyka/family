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
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "persons")
public class Person implements BaseEntity<Long> {
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

    private Boolean gender; // man -> true; female -> false
    private String email;
    private String phone;
    private String wikilink;

    private String username;

    @OneToMany(mappedBy = "parent")
    private List<PersonRelation> relationsParent = new ArrayList<>();
    @OneToMany(mappedBy = "child")
    private List<PersonRelation> relationsChild = new ArrayList<>();
    @OneToMany(mappedBy = "husband")
    private List<Marriage> marriagesAsHusband;
    @OneToMany(mappedBy = "wife")
    private List<Marriage> marriagesAsWife;

    @ManyToMany(mappedBy = "persons")
    private List<Photo> photos = new ArrayList<>();

    public boolean isMan() {
        return gender;
    }

    public boolean isWoman() {
        return !gender;
    }

    public List<Marriage> getMarriages() {
        return isMan()
               ? marriagesAsHusband
               : marriagesAsWife;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id.equals(person.id)
                && Objects.equals(name, person.name)
                && Objects.equals(surname, person.surname)
                && Objects.equals(fathername, person.fathername)
                && Objects.equals(birthday, person.birthday)
                && Objects.equals(deadDay, person.deadDay)
                && Objects.equals(gender, person.gender)
                && Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, fathername, birthday, deadDay, gender, username);
    }
}
