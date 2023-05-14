package by.ladyka.family.api;

import by.ladyka.family.dto.FamilyRole;
import by.ladyka.family.dto.PartnerAndChildren;
import by.ladyka.family.dto.PersonDto;
import by.ladyka.family.dto.PersonPage;
import by.ladyka.family.dto.PersonParentDto;
import by.ladyka.family.entity.Person;
import by.ladyka.family.services.PersonRelationService;
import by.ladyka.family.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://treefamily-ui.herokuapp.com", allowCredentials = "true")
public class FamilyApiController {

    private final PersonService personService;
    private final PersonRelationService personRelationService;

    @RequestMapping(value = "/person/{id}", method = GET)
    public PersonPage getById(@PathVariable Long id, Principal principal) {
        PersonPage personPage = new PersonPage();

        Person person = this.personService.findByIdOrUsername(id, principal.getName());
        personPage.setPerson(new PersonDto(person, FamilyRole.ME));

        Set<Person> brothersAndSisters = new HashSet<>();

        personRelationService.getFather(person)
                .ifPresent(father -> {
                    PersonParentDto fatherParentDto = getPersonParentDto(father);
                    personPage.setFather(fatherParentDto);

                    List<Person> children = personRelationService.getChildren(father);
                    brothersAndSisters.addAll(children);
                });

        personRelationService.getMother(person)
                .ifPresent(mother -> {
                    PersonParentDto motherParentDto = getPersonParentDto(mother);
                    personPage.setMother(motherParentDto);

                    List<Person> children = personRelationService.getChildren(mother);
                    brothersAndSisters.addAll(children);
                });

        brothersAndSisters.remove(person);
        personPage.setBrothersAndSisters(brothersAndSisters
                        .stream()
                        .map(p -> new PersonDto(p, FamilyRole.BROTHER_SISTER))
                        .collect(Collectors.toList())
                                        );

        List<Person> partners = (person.getGender())
                                ? this.personRelationService.wives(person)
                                : this.personRelationService.husbands(person);

        partners.forEach(partner -> {
            PartnerAndChildren partnerAndChildren = new PartnerAndChildren();
            partnerAndChildren.setPartner(new PersonDto(partner, FamilyRole.HUSBAND_WIFE));
            List<PersonDto> children = personRelationService
                    .getChildren(partner)
                    .stream()
                    .map(p -> new PersonDto(p, FamilyRole.SON_DAUGHTER))
                    .collect(Collectors.toList());
            partnerAndChildren.setChildren(children);
            personPage.getPartnerAndChildren().add(partnerAndChildren);
        });
        return personPage;
    }

    private PersonParentDto getPersonParentDto(Person entity) {
        PersonParentDto parent = new PersonParentDto(entity, FamilyRole.FATHER_MOTHER);
        Optional<Person> grandFather = personRelationService.getFather(entity);
        grandFather.ifPresent(gf -> parent.setFather(new PersonDto(gf, FamilyRole.GRAND_FATHER_MOTHER)));
        Optional<Person> grandMother = personRelationService.getMother(entity);
        grandMother.ifPresent(gm -> parent.setMother(new PersonDto(gm, FamilyRole.GRAND_FATHER_MOTHER)));
        return parent;
    }
}
