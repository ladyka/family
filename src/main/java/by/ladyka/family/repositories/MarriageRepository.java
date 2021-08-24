package by.ladyka.family.repositories;

import by.ladyka.family.entity.Marriage;
import by.ladyka.family.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarriageRepository extends JpaRepository<Marriage, Long> {
    Optional<List<Marriage>> findAllByWife(Person wife);

    Optional<List<Marriage>> findAllByHusband(Person husband);
}
