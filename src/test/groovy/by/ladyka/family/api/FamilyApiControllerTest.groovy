//package by.ladyka.family.api
//
//import by.ladyka.family.BaseWebTest
//import by.ladyka.family.data.Bundle
//import by.ladyka.family.data.Persons
//import by.ladyka.family.entity.Person
//import org.springframework.beans.factory.annotation.Autowired
//
//class FamilyApiControllerTest extends BaseWebTest {
//    @Autowired
//    Persons persons
//
//    def "получение person по id"() {
//        given:
//        def pers = persons.create()
//        def id = pers.getEntity().getId()
//
//        when:
//        testRestUser.getForEntity("api/person/$id",[])
//
//        then:
//
//    }
//}
