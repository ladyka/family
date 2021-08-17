package by.ladyka.family

import by.ladyka.family.data.Bundle
import by.ladyka.family.data.DatabaseVerification
import by.ladyka.family.security.RandomizedOrder
import by.ladyka.family.security.WebSecurityConfigTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RandomizedOrder
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
abstract class BaseWebTest extends Specification {
    def uuid = UUID.randomUUID().toString()
    @Autowired
    protected TestRestTemplate testRestAnonymous
    protected TestRestTemplate testRestAdmin
    protected TestRestTemplate testRestUser

    @Autowired
    protected ObjectMapper objectMapper

    @Autowired
    private DatabaseVerification databaseVerification

    def startRowCounts
    def tableNames

    @Value('${enable-tables-check}')
    boolean enableTablesCheck

    def setup() {
        println("===Start test " + uuid + " =======================")
        testRestUser = testRestAnonymous.withBasicAuth(WebSecurityConfigTest.TEST_USER_EMAIL, WebSecurityConfigTest.TEST_PASSWORD_RAW)
        if (enableTablesCheck) {
            tableNames = databaseVerification.findTableNames()
                    .findAll { item -> 'user_log' != item }
            startRowCounts = databaseVerification.getRowCounts(tableNames)
        }
    }

    def cleanup() {
        if (enableTablesCheck) {
            def finishRowCounts = databaseVerification.getRowCounts(tableNames)
            finishRowCounts.forEach({ name, finishCount ->
                assert startRowCounts.get(name, 0) == finishCount: "Test data is not rolled back from table: \'$name\'"
            })
        }
        println("=== Finish test " + uuid + "======================")
    }

    protected static void assertLocalDateTime(actualValue, expectedValue) {
        if (expectedValue == null) {
            assert actualValue == null
        } else {
            LocalDateTime actualTime = actualValue instanceof String ? LocalDateTime.parse(actualValue, DateTimeFormatter.ISO_DATE_TIME) : actualValue
            LocalDateTime expectedTime = expectedValue instanceof String ? LocalDateTime.parse(expectedValue) : expectedValue
            assert Math.abs(actualTime.toEpochSecond(ZoneOffset.UTC) - expectedTime.toEpochSecond(ZoneOffset.UTC)) < 3
        }
    }

    def delete(Bundle bundle) {
        bundle && bundle.delete()
    }
}
