package by.ladyka.family.dto;

import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.Photo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private String surname;
    private String fathername;
    private String role;
    private LocalDateTime birthday;
    private LocalDateTime deadDay;
    private Boolean gender;
    // man -> true; female -> false
    private List<Photo> photos = new ArrayList<>();

    public PersonDto(Person p, FamilyRole familyRole) {
        id = p.getId();
        name = p.getName();
        surname = p.getSurname();
        fathername = p.getFathername();
        birthday = p.getBirthday();
        deadDay = p.getDeadDay();
        gender = p.getGender();
        photos = p.getPhotos();
        role = familyRole.getTitle(p.getGender());
    }
}
