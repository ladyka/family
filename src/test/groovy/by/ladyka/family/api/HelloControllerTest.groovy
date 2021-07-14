package by.ladyka.family.api

import by.ladyka.family.BaseWebTest

class HelloControllerTest extends BaseWebTest {
    def "Hello"() {
        when:
        def responseEntity = testRestAnonymous.getForEntity("/hello", String)
//        def с = 5 + 1
        then:
//        с == 6
        responseEntity.getBody() == "Hello"

    }
}
