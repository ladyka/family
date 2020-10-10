package by.ladyka.family.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonPage {
    PersonDto person = new PersonDto();
    PersonDto father = new PersonDto();
    PersonDto mother = new PersonDto();
    PersonDto motherGrandMother = new PersonDto();
    PersonDto motherGrandFather = new PersonDto();
    PersonDto fatherGrandMother = new PersonDto();
    PersonDto fatherGrandFather = new PersonDto();
    List<PersonDto> brothersAndSisters = new ArrayList<>();
    List<PartnerAndChildren> partnerAndChildren = new ArrayList<>();
}
