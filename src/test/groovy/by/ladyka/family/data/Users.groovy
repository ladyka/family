package by.ladyka.family.data


import by.ladyka.family.entity.UserData
import by.ladyka.family.repositories.UserDataRepository
import org.springframework.beans.factory.annotation.Autowired

class Users {
    @Autowired
    UserDataRepository dataRepository

    Bundle<UserData, Long> create(Map properties = [:]) {

        def bundle = new Bundle<UserData, Long>(dataRepository)
        bundle.entity = dataRepository.saveAndFlush(new UserData(
                username: properties.username,
                password: properties.password ?: "default surname",
                enabled: properties.enabled ?: Boolean.TRUE
        ))
        bundle
    }
}
