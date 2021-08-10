package by.ladyka.family.data


import by.ladyka.family.entity.PersonRelation
import by.ladyka.family.entity.RelationType
import by.ladyka.family.repositories.PersonRelationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
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
                relation: RelationType.PARENT_CHILD
        ))
        bundle
    }

    Bundle<PersonRelation, Long> createCoupleHusbandAndWife(Map properties = [:]) {
        def bundle = new Bundle<PersonRelation, Long>(personRelationRepository)
        bundle.entity = personRelationRepository.saveAndFlush(new PersonRelation(
                parent: properties.parent ?: bundle.appendAndGet(persons.create(name: "Husband", gender: true)),
                child: properties.child ?: bundle.appendAndGet(persons.create(name: "Wife", gender: false)),
                relation: RelationType.MARRIAGE
        ))
        bundle
    }
}
