package by.ladyka.family.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonPage {
    PersonDto person = new PersonDto();
    PersonParentDto father = new PersonParentDto();
    PersonParentDto mother = new PersonParentDto();
    List<PersonDto> brothersAndSisters = new ArrayList<>();
    List<PartnerAndChildren> partnerAndChildren = new ArrayList<>();
}
