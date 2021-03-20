package by.ladyka.family.services;

import by.ladyka.family.entity.Person;
import by.ladyka.family.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person createOrUpdate(Long id, String name, String surname, String fathername,
                                 LocalDateTime birthday, LocalDateTime deadDay, boolean gender) {
        Person person = (id != null) ? personRepository.getOne(id) : new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setFathername(fathername);
        person.setBirthday(birthday);
        person.setDeadDay(deadDay);
        person.setGender(gender);
        return this.personRepository.save(person);
    }

    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    public Person findById(Long id) {
        return this.personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person " + id + " was not found"));
    }

    public Person findByIdOrUsername(Long personId, String username) {
        return personRepository
                .findById(personId)
                .orElseGet(() -> personRepository
                        .findFirstByUsername(username)
                        .orElseThrow(EntityNotFoundException::new));
    }

    public void delete(Long id) {
        Person person = this.personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person was not found"));
        this.personRepository.delete(person);
    }
}
