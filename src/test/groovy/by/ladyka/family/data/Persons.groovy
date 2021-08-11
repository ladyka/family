package by.ladyka.family.data

import by.ladyka.family.entity.Person
import by.ladyka.family.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDateTime

@Component
class Persons {
    @Autowired
    PersonRepository personRepository

    Bundle<Person, Long> create(Map properties = [:]) {
        def bundle = new Bundle<Person, Long>(personRepository)
        def gender = (properties.gender == null) ? true : properties.gender
        def person = new Person(
                name: properties.name ?: "default name",
                surname: properties.surname ?: "default surname",
                fathername: properties.fathername ?: "default fathername",
                birthday: properties.birthday ?: LocalDateTime.now().minusYears(18L),
                gender: gender,
                email: properties.email ?: "defaulemail@email.gom",
                phone: properties.phone ?: "+123456789",
                wikilink: properties.wikilink ?: "/wikilink",
                username: properties.username ?: "defaultUsername",
        )
        bundle.entity = personRepository.saveAndFlush(person)
        bundle
    }
}
