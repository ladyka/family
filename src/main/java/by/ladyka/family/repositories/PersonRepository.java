package by.ladyka.family.repositories;

import by.ladyka.family.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByNameAndSurnameAndFathername(String name, String surname,
                                             String fathername);
    Optional<Person> findById(Long id);

    Optional<Person> findFirstByUsername(String username);
}
