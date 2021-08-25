package by.ladyka.family.controllers

import by.ladyka.family.BaseWebTest
import by.ladyka.family.data.Marriages
import by.ladyka.family.data.Persons
import by.ladyka.family.dto.MarriageDto
import by.ladyka.family.entity.MarriageType
import by.ladyka.family.mappers.PersonMapper
import by.ladyka.family.repositories.MarriageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import java.time.LocalDate

class MarriageControllerTest extends BaseWebTest {
    @Autowired
    MarriageRepository marriageRepository
    @Autowired
    Persons persons
    @Autowired
    PersonMapper personMapper
    @Autowired
    Marriages marriages

    def "поиск всех браков"() {
        given:
        def man = persons.create(name: "Man")
        def firstWife = persons.create(name: "Wife1", surname: "Surname4FirstWife", gender: false)
        def marriage1 = marriages.create(husband: man.entity, wife: firstWife.entity)
        def secondWife = persons.create(name: "Wife2", gender: false)
        def marriage2 = marriages.create(husband: man.entity, wife: secondWife.entity)

        when:
        def response = testRestUser.getForEntity("/api/marriage?personId=$man.entity.id", Object)
        then:
        response.statusCode == HttpStatus.OK
        def body = response.body
        body.size == 2
        def marriageFirst = body.get(0)
        marriageFirst.id == marriage1.entity.id
        marriageFirst.husbandId == marriage1.entity.husband.id
        marriageFirst.wifeId == marriage1.entity.wife.id
        LocalDate.parse(marriageFirst.registration) == marriage1.entity.registration
        marriageFirst.divorce == marriage1.entity.divorce
        marriageFirst.marriageType == marriage1.entity.marriageType as String
        def marriageSecond = body.get(1)
        marriageSecond.id == marriage2.entity.id
        marriageSecond.husbandId == marriage2.entity.husband.id
        marriageSecond.wifeId == marriage2.entity.wife.id
        LocalDate.parse(marriageSecond.registration) == marriage2.entity.registration
        marriageSecond.divorce == marriage2.entity.divorce
        marriageSecond.marriageType == marriage2.entity.marriageType as String

        cleanup:
        delete marriage2
        delete secondWife
        delete marriage1
        delete firstWife
        delete man
    }

    def "добавление"() {
        given:
        def man = persons.create(name: "Man")
        def secondWife = persons.create(name: "Wife2", gender: false)
        def dto = new MarriageDto(husbandId: man.entity.id, wifeId: secondWife.entity.id, registration: LocalDate.now(), marriageType: MarriageType.OFFICIAL)

        when:
        def responseEntity = testRestUser.postForEntity("/api/marriage/add", dto, Object)

        then:
        responseEntity.statusCode == HttpStatus.OK
        def marriage = marriageRepository.findAll().get(0)

        cleanup:
        marriageRepository.delete(marriage)
        delete secondWife
        delete man
    }

    def "обновление"() {
        given:
        def man = persons.create(name: "Man")
        def secondWife = persons.create(name: "Wife2", gender: false)
        def create = marriages.create(husband: man.entity, wife: secondWife.entity)
        def dto = new MarriageDto(husbandId: man.entity.id, wifeId: secondWife.entity.id, registration: LocalDate.now().minusYears(1L), divorce: LocalDate.now(), marriageType: MarriageType.OFFICIAL)

        when:
        testRestUser.put("/api/marriage/$create.entity.id", dto)

        then:
        def marriage = marriageRepository.findAll().get(0)
        marriage.divorce == dto.divorce
        marriage.marriageType == dto.marriageType

        cleanup:
        delete create
        delete secondWife
        delete man
    }

}
