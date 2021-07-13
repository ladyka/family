package by.ladyka.family.data

import by.ladyka.family.entity.Authority
import by.ladyka.family.repositories.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired

class Authorities {
    @Autowired
    AuthorityRepository authorityRepository

    Bundle<Authority, String> create(Map properties = [:]) {
        assert properties.id != null
        assert properties.birthday != null
        def bundle = new Bundle<Authority, String>(authorityRepository)
        bundle.entity = authorityRepository.saveAndFlush(new Authority(
                name: properties.name,
                authority: properties.authority ?: "defaultAuthority",

        ))
        bundle
    }
}
