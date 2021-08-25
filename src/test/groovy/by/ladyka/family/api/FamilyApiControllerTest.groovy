package by.ladyka.family.api

import by.ladyka.family.BaseWebTest
import by.ladyka.family.data.Marriages
import by.ladyka.family.data.PersonRelations
import by.ladyka.family.data.Persons
import by.ladyka.family.dto.PersonPage
import by.ladyka.family.entity.MarriageType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import java.time.LocalDate

class FamilyApiControllerTest extends BaseWebTest {
    @Autowired
    Persons persons

    @Autowired
    PersonRelations personRelations
    @Autowired
    Marriages marriages

    def "GetById"() {
        given:
        def father = persons.create(name: "Husband")
        def mother = persons.create(name: "Wife", gender: false)

        def man = persons.create(name: "Man")
        def secondWife = persons.create(name: "Wife2", gender: false)


        def relationManWithSecondWife = marriages.create(husband: man.entity, wife: secondWife.entity,
                registration: LocalDate.now().minusYears(5L),
                marriageType: MarriageType.OFFICIAL)

        def fatherAndChild = personRelations.create(parent: father.entity,
                child: man.entity)

        def motherAndChild = personRelations.create(parent: mother.entity,
                child: man.entity)

        def mansSun = persons.create(name: "firsSun", fathername: man.entity.name + "ovich")

        def manRelationWithSunFromFirstWife = personRelations.create(parent: man.entity,
                child: mansSun.entity)

        def mansWifeRelationWithSun = personRelations.create(
                parent: secondWife.entity,
                child: mansSun.entity)


        def newHusband = persons.create(name: "hewHusbandSecindWife", fathername: "fathername4NewHusband", surname: "Newman")
        def firstWife = persons.create(name: "Wife1", surname: "Surname4FirstWife", gender: false)

        def relationFirstWifeAndHerNewHusband = marriages.create(husband: newHusband.entity, wife: firstWife.entity,
                registration: LocalDate.now().minusYears(6L),
                marriageType: MarriageType.OFFICIAL)

        def relationManWithFirstWife = marriages.create(husband: man.entity, wife: firstWife.entity,
                registration: LocalDate.now().minusYears(7L),
                divorce: LocalDate.now().minusYears(6L),
                marriageType: MarriageType.OFFICIAL)

        def childFromFirstWife = persons.create(name: "FirsChild-daughter",
                fathername: man.entity.name + "ovna",
                surname: firstWife.entity.surname, gender: false)

        def relationFirstDaughter4Man = personRelations.create(
                parent: man.entity,
                child: childFromFirstWife.entity)

        def relationFirstDaughter4ManAndHerMather = personRelations.create(
                parent: firstWife.entity, child: childFromFirstWife.entity)

//        def newHusbandsSun = persons.create(name: "Ashot",
//                fathername: relationFirstWifeAndHerNewHusband.entity.parent.name + "vish",
//                surname: relationFirstWifeAndHerNewHusband.entity.parent.surname)
//
//        def relationSunFistWifeAndHisFather = personRelations.create(
//                parent: relationFirstWifeAndHerNewHusband.entity.parent,
//                child: newHusbandsSun.entity)
//
//        def relationSunFistWifeAndHer = personRelations.create(
//                parent: relationFirstWifeAndHerNewHusband.entity.child,
//                child: newHusbandsSun.entity)

        when:
        def responseEntity = testRestUser.getForEntity("/api/person/$man.entity.id", PersonPage)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK
        def personDto = responseEntity.body.person
        personDto.id == man.entity.id
        personDto.name == man.entity.name
        personDto.surname == man.entity.surname
        personDto.fathername == man.entity.fathername
        personDto.gender == man.entity.gender
        personDto.email == man.entity.email
        personDto.phone == man.entity.phone
        personDto.wikilink == man.entity.wikilink

        def father4Man = responseEntity.body.father
        father4Man.id == father.entity.id
        father4Man.name == father.entity.name
        father4Man.surname == father.entity.surname
        father4Man.fathername == father.entity.fathername

        def mother4Man = responseEntity.body.mother
        mother4Man.id == mother.entity.id
        mother4Man.name == mother.entity.name
        mother4Man.surname == mother.entity.surname
        mother4Man.fathername == mother.entity.fathername


        def partnerAndChildren = responseEntity.body.partnerAndChildren
        partnerAndChildren.size() == 2
        partnerAndChildren.get(0).partner.name == secondWife.entity.name

        def children1 = partnerAndChildren.get(0).children
        children1.size() == 1
        children1.get(0).id == mansSun.entity.id
        partnerAndChildren.get(1).partner.id == firstWife.entity.id
        partnerAndChildren.get(1).children.size() == 1
        partnerAndChildren.get(1).children.get(0).id == childFromFirstWife.entity.id

        cleanup:
//        delete relationSunFistWifeAndHer
//        delete relationSunFistWifeAndHisFather
//        delete newHusbandsSun
        delete relationFirstDaughter4ManAndHerMather
        delete relationFirstDaughter4Man
        delete childFromFirstWife
        delete relationManWithFirstWife
        delete relationFirstWifeAndHerNewHusband
        delete firstWife
        delete newHusband
        delete mansWifeRelationWithSun
        delete manRelationWithSunFromFirstWife
        delete mansSun
        delete motherAndChild
        delete fatherAndChild
        delete relationManWithSecondWife
        delete secondWife
        delete man
        delete mother
        delete father
    }
}
