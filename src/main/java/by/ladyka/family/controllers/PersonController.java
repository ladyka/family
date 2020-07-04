package by.ladyka.family.controllers;

import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.PersonRelation;
import by.ladyka.family.entity.Photo;
import by.ladyka.family.entity.RelationType;
import by.ladyka.family.services.PersonRelationService;
import by.ladyka.family.services.PersonService;
import by.ladyka.family.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final PersonRelationService personRelationService;
    private final PhotoService photoService;

    @RequestMapping(value = "/", method = GET)
    public String persons(Model model) {
        List<Person> persons = this.personService.findAll();
        model.addAttribute("persons", persons);
        return "persons";
    }

    @RequestMapping(value = "/person", method = POST)
    public String createOrUpdate(Long id,
                                 String name,
                                 String surname,
                                 String fathername,
                                 boolean gender,
                                 String birthday,
                                 String deadDay,
                                 Model model) {
        LocalDateTime deadday = null;
        if (!StringUtils.isEmpty(deadDay)) {
            deadday = LocalDateTime.parse(deadDay);
        }
        Person person = this.personService.createOrUpdate(id, name, surname, fathername, LocalDateTime.parse(birthday), deadday, gender);
        return getById(person.getId(), model);
    }

    @RequestMapping(value = "/person/new", method = POST)
    public String create(Long id,
                         String name,
                         String surname,
                         String fathername,
                         boolean gender,
                         String birthday,
                         String deadDay,
                         int relationType,
                         Long personRelationId,
                         Model model) {
        LocalDateTime deadday = null;
        if (!StringUtils.isEmpty(deadDay)) {
            deadday = LocalDateTime.parse(deadDay);
        }
        Person person = this.personService.createOrUpdate(id, name, surname, fathername, LocalDateTime.parse(birthday), deadday, gender);
        this.personRelationService.createRelation(personRelationId, person.getId(), RelationType.of(relationType));
        return getById(person.getId(), model);
    }

    @RequestMapping(value = "/person/{id}", method = GET)
    public String getById(@PathVariable Long id, Model model) {
        Person person = this.personService.findById(id);

        List<Person> husbands = this.personRelationService.husbands(person.getId());
        List<Person> wives = this.personRelationService.wives(person.getId());
        List<Person> parents = this.personRelationService.parentRelations(person.getId());
        List<Person> children = this.personRelationService.childRelation(person.getId());

        List<Person> brothersAndSisters = parents
                .stream()
                .map(Person::getRelationsParent)
                .flatMap(personRelations -> personRelations.stream().map(PersonRelation::getChild))
                .collect(Collectors.toList());

        brothersAndSisters.remove(person);

        model.addAttribute("person", person);
        model.addAttribute("parents", parents);
        model.addAttribute("children", children);
        model.addAttribute("husbands", husbands);
        model.addAttribute("wives", wives);
        model.addAttribute("brothersAndSisters", brothersAndSisters);

        model.addAttribute("persons", this.personService.findAll());

        List<Photo> photos = photoService.getTopPersonPhotos(person.getId(), 7);
        model.addAttribute("photos", photos);
        return "person";
    }

    @RequestMapping(value = "/person/{id}/delete", method = GET)
    public String delete(@PathVariable Long id, Model model) {
        Person foundPerson = this.personService.findById(id);
        this.personService.delete(foundPerson.getId());
        return persons(model);
    }

    @RequestMapping(value = "/relation", method = POST)
    public String createRelation(Long personId, Long relationId, int relationType, Model model) {
        this.personRelationService.createRelation(personId, relationId, RelationType.of(relationType));
        return getById(personId, model);
    }

    @RequestMapping(method = GET)
    public @ResponseBody
    String g404() {
        return "404 not found. GET";
    }

    @RequestMapping(method = POST)
    public @ResponseBody
    String p404() {
        return "404 not found. POST";
    }

}

