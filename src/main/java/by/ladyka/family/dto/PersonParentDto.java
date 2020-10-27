package by.ladyka.family.dto;

import by.ladyka.family.entity.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonParentDto extends PersonDto {
    PersonDto father = new PersonDto();
    PersonDto mother = new PersonDto();

    public PersonParentDto(Person p, FamilyRole familyRole) {
        super(p, familyRole);
    }
}
