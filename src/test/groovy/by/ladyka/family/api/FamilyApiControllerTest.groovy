package by.ladyka.family.api

import by.ladyka.family.BaseWebTest
import by.ladyka.family.data.PersonRelations
import by.ladyka.family.data.Persons
import by.ladyka.family.dto.PersonPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class FamilyApiControllerTest extends BaseWebTest {
    @Autowired
    Persons persons

    @Autowired
    PersonRelations personRelations

    def "GetById"() {
        given:
        def parents = personRelations.createCoupleHusbandAndWife()
        def child = persons.create(name: "Child",)
//        def fatherAndChild = personRelations.create(parent: parents.entity.parent,
//                child: child.entity,
//                relation: RelationType.PARENT_CHILD)
//
//        def matherAndChild = personRelations.create(parent: parents.entity.child,
//                child: child.entity,
//                relation: RelationType.PARENT_CHILD)

        when:
        def responseEntity = testRestUser.getForEntity("/api/person/$child.entity.id", PersonPage)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK
        def personDto = responseEntity.body.person
        personDto.id == child.entity.id
        personDto.name == child.entity.name
        personDto.surname == child.entity.surname
        personDto.fathername == child.entity.fathername
        personDto.gender == child.entity.gender
        personDto.email == child.entity.email
        personDto.phone == child.entity.phone
        personDto.wikilink == child.entity.wikilink


        cleanup:
        delete child
        delete parents
    }
}
