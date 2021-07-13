package by.ladyka.family.api

import by.ladyka.family.BaseWebTest

class HelloControllerTest extends BaseWebTest {
    def "Hello"() {
        when:
        def responseEntity = testRestAnonymous.getForEntity("/hello", String)
        then:
        responseEntity.getBody() == "Hello"

    }
}
