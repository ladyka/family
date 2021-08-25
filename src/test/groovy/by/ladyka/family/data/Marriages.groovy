package by.ladyka.family.data

import by.ladyka.family.entity.Marriage
import by.ladyka.family.entity.MarriageType
import by.ladyka.family.repositories.MarriageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
class Marriages {
    @Autowired
    MarriageRepository marriageRepository

    Bundle<Marriage, Long> create(Map properties = [:]) {
        def bundle = new Bundle<Marriage, Long>(marriageRepository)
        bundle.entity = marriageRepository.saveAndFlush(new Marriage(
                husband: properties.husband,
                wife: properties.wife,
                registration: properties.registration ?: LocalDate.now(),
                divorce: properties.divorce,
                marriageType: properties.marriageType ?: MarriageType.CIVIL_MARRIAGE))
        bundle
    }
}
