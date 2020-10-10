package by.ladyka.family.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PartnerAndChildren {
    PersonDto partner = new PersonDto();
    List<PersonDto> children = new ArrayList<>();
}
