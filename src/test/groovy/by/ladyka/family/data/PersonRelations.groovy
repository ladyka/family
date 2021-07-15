package by.ladyka.family.data


import by.ladyka.family.entity.PersonRelation
import by.ladyka.family.repositories.PersonRelationRepository
import org.springframework.beans.factory.annotation.Autowired

class PersonRelations {
    @Autowired
    PersonRelationRepository personRelationRepository

    @Autowired
    Persons persons

    Bundle<PersonRelation, Long> create(Map properties = [:]) {
        assert properties.relation != null
        def bundle = new Bundle<PersonRelation, Long>(personRelationRepository)
        bundle.entity = personRelationRepository.saveAndFlush(new PersonRelation(
                parent: properties.parent ?: bundle.appendAndGet(persons.create(name: "defParent")),
                child: properties.child ?: bundle.appendAndGet(persons.create(name: "defChaild")),
                relation: properties.relation
        ))
        bundle
    }
}
