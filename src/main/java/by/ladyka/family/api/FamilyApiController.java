package by.ladyka.family.api;

import by.ladyka.family.dto.FamilyRole;
import by.ladyka.family.dto.PartnerAndChildren;
import by.ladyka.family.dto.PersonDto;
import by.ladyka.family.dto.PersonPage;
import by.ladyka.family.dto.PersonParentDto;
import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.PersonRelation;
import by.ladyka.family.entity.RelationType;
import by.ladyka.family.services.PersonRelationService;
import by.ladyka.family.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FamilyApiController {

    private final PersonService personService;
    private final PersonRelationService personRelationService;

    @RequestMapping(value = "/person/{id}", method = GET)
    public PersonPage getById(@PathVariable Long id, Principal principal) {
        PersonPage personPage = new PersonPage();

        Person person = this.personService.findByIdOrUsername(id, principal.getName());
        personPage.setPerson(new PersonDto(person, FamilyRole.ME));

        List<Person> parents = this.personRelationService.parentRelations(person.getId());
        parents.stream()
                .filter(Person::isMan)
                .findFirst()
                .map(this::getPersonParentDto)
                .ifPresent(personPage::setFather);

        parents.stream()
                .filter(Person::isWoman)
                .findFirst()
                .map(this::getPersonParentDto)
                .ifPresent(personPage::setMother);

        Set<Person> brothersAndSisters = parents
                .stream()
                .map(Person::getRelationsParent)
                .flatMap(personRelations -> personRelations
                        .stream()
                        .filter(personRelation -> RelationType.PARENT_CHILD.equals(personRelation.getRelation()))
                        .map(PersonRelation::getChild))
                .collect(Collectors.toSet());

        brothersAndSisters.remove(person);
        personPage.setBrothersAndSisters(brothersAndSisters
                .stream()
                .map(p -> new PersonDto(p, FamilyRole.BROTHER_SISTER))
                .collect(Collectors.toList())
        );

        List<Person> husbands = this.personRelationService.husbands(person.getId());
        List<Person> wives = this.personRelationService.wives(person.getId());

        List<Person> partners = new ArrayList<>();
        partners.addAll(husbands);
        partners.addAll(wives);
        partners.forEach(partner -> {
            PartnerAndChildren partnerAndChildren = new PartnerAndChildren();
            partnerAndChildren.setPartner(new PersonDto(partner, FamilyRole.HUSBAND_WIFE));
            List<PersonDto> children = personRelationService
                    .childRelation(partner.getId())
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
        List<Person> parents = this.personRelationService.parentRelations(entity.getId());
        Optional<Person> grandFather = parents.stream().filter(Person::isMan).findFirst();
        grandFather.ifPresent(gf -> parent.setFather(new PersonDto(gf, FamilyRole.GRAND_FATHER_MOTHER)));
        Optional<Person> grandMother = parents.stream().filter(Person::isWoman).findFirst();
        grandMother.ifPresent(gm -> parent.setMother(new PersonDto(gm, FamilyRole.GRAND_FATHER_MOTHER)));
        return parent;
    }
}
