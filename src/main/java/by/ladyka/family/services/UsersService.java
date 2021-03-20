package by.ladyka.family.services;

import by.ladyka.family.entity.Authority;
import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.UserData;
import by.ladyka.family.repositories.AuthorityRepository;
import by.ladyka.family.repositories.PersonRepository;
import by.ladyka.family.repositories.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserDataRepository userDataRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    @Deprecated
    public UserData singUp(String username, String password) {
        Person person = personRepository.findFirstByUsername(username).orElseThrow(EntityNotFoundException::new);

        Authority authority = new Authority();
        authority.setAuthority("USER");
        authority.setUsername(username);
        authorityRepository.save(authority);

        UserData user = new UserData();
        user.setId(person.getId());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        userDataRepository.save(user);
        return user;
    }
}
