package by.ladyka.family.services;

import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.PersonRelation;
import by.ladyka.family.entity.RelationType;
import by.ladyka.family.repositories.PersonRelationRepository;
import by.ladyka.family.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonRelationService {

    private final PersonRelationRepository personRelationRepository;
    private final PersonRepository personRepository;


    public void createRelation(Long id1, Long id2, RelationType relation) {
        PersonRelation personRelation = new PersonRelation();

        Person two = this.personRepository.getOne(id2);
        Person one = this.personRepository.getOne(id1);

        if (RelationType.MARRIAGE.equals(relation)) {
            if (one.getGender().compareTo(two.getGender()) > 0) {
                personRelation.setParent(one);
                personRelation.setChild(two);
            } else {
                personRelation.setParent(two);
                personRelation.setChild(one);
            }
        } else if (two.getBirthday().compareTo(one.getBirthday()) > 0) {
            personRelation.setParent(one);
            personRelation.setChild(two);
        } else {
            personRelation.setParent(two);
            personRelation.setChild(one);
        }
        personRelation.setRelation(relation);
        this.personRelationRepository.save(personRelation);
    }

    public List<Person> parentRelations(Long id) {
        return getChildRelation(id, RelationType.PARENT_CHILD);
    }

    public List<Person> husbands(Long id) {
        return getChildRelation(id, RelationType.MARRIAGE);
    }

    public List<Person> childRelation(Long id) {
        return geParentRelation(id, RelationType.PARENT_CHILD);
    }

    public List<Person> wives(Long id) {
        return geParentRelation(id, RelationType.MARRIAGE);
    }

    private List<Person> geParentRelation(Long id, RelationType parentChild) {
        Person person = this.personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person was not found"));
        List<Person> parentPersons = new ArrayList<>();
        List<PersonRelation> parents = this.personRelationRepository.findAllByParentAndRelation(person, parentChild);
        for (PersonRelation p : parents) {
            Person parent = p.getChild();
            parentPersons.add(parent);
        }
        return parentPersons;
    }

    private List<Person> getChildRelation(Long id, RelationType parentChild) {
        Person person = this.personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person was not found"));
        List<Person> childPersons = new ArrayList<>();
        List<PersonRelation> children = this.personRelationRepository.findAllByChildAndRelation(person, parentChild);
        for (PersonRelation c : children) {
            Person child = c.getParent();
            childPersons.add(child);
        }
        return Collections.unmodifiableList(childPersons);
    }
}
