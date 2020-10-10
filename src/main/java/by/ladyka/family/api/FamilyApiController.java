package by.ladyka.family.api;

import by.ladyka.family.dto.PartnerAndChildren;
import by.ladyka.family.dto.PersonDto;
import by.ladyka.family.dto.PersonPage;
import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.PersonRelation;
import by.ladyka.family.services.PersonRelationService;
import by.ladyka.family.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FamilyApiController {

    private final PersonService personService;
    private final PersonRelationService personRelationService;

    @RequestMapping(value = "/person/{id}", method = GET)
    public PersonPage getById(@PathVariable Long id, Model model, Principal principal) {
        PersonPage personPage = new PersonPage();

        Person person = this.personService.findByIdOrUsername(id, principal.getName());
        personPage.setPerson(new PersonDto(person));

        List<Person> parents = this.personRelationService.parentRelations(person.getId());
        Optional<Person> father = parents.stream().filter(Person::getGender).findFirst();
        father.ifPresent(f -> {
            personPage.setFather(new PersonDto(f));
            List<Person> parentsFather = this.personRelationService.parentRelations(f.getId());
            Optional<Person> grandFather = parentsFather.stream().filter(Person::getGender).findFirst();
            grandFather.ifPresent(gf -> personPage.setFatherGrandFather(new PersonDto(gf)));
            Optional<Person> grandMother = parentsFather.stream().filter(parent -> !parent.getGender()).findFirst();
            grandMother.ifPresent(gm -> personPage.setFatherGrandMother(new PersonDto(gm)));
        });

        Optional<Person> mother = parents.stream().filter(parent -> !parent.getGender()).findFirst();
        mother.ifPresent(m -> {
            personPage.setMother(new PersonDto(m));
            List<Person> parentsMother = this.personRelationService.parentRelations(m.getId());
            Optional<Person> grandFather = parentsMother.stream().filter(Person::getGender).findFirst();
            grandFather.ifPresent(gf -> personPage.setMotherGrandFather(new PersonDto(gf)));
            Optional<Person> grandMother = parentsMother.stream().filter(parent -> !parent.getGender()).findFirst();
            grandMother.ifPresent(gm -> personPage.setMotherGrandMother(new PersonDto(gm)));
        });

        List<Person> brothersAndSisters = parents
                .stream()
                .map(Person::getRelationsParent)
                .flatMap(personRelations -> personRelations.stream().map(PersonRelation::getChild))
                .collect(Collectors.toList());

        brothersAndSisters.remove(person);
        personPage.setBrothersAndSisters(brothersAndSisters.stream().map(PersonDto::new).collect(Collectors.toList()));

        List<Person> husbands = this.personRelationService.husbands(person.getId());
        List<Person> wives = this.personRelationService.wives(person.getId());

        List<Person> partners = new ArrayList<>();
        partners.addAll(husbands);
        partners.addAll(wives);
        partners.forEach(partner -> {
            PartnerAndChildren partnerAndChildren = new PartnerAndChildren();
            partnerAndChildren.setPartner(new PersonDto(partner));
            List<PersonDto> children = personRelationService
                    .childRelation(partner.getId())
                    .stream()
                    .map(PersonDto::new)
                    .collect(Collectors.toList());
            partnerAndChildren.setChildren(children);
        });
        return personPage;
    }
}
