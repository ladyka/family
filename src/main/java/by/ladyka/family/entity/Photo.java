package by.ladyka.family.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="photo_id")
    private Long photoId;
    private String description;
    private String name; // path to reference

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name="person_has_photos",
            joinColumns=@JoinColumn(name="photo_id"),
            inverseJoinColumns=@JoinColumn(name="person_id"))
    private List<Person> persons = new ArrayList<>();
}
