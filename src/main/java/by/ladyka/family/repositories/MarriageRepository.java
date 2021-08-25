package by.ladyka.family.repositories;

import by.ladyka.family.entity.Marriage;
import by.ladyka.family.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarriageRepository extends JpaRepository<Marriage, Long> {
    List<Marriage> findAllByWife(Person wife);

    List<Marriage> findAllByHusband(Person husband);

    List<Marriage> findByHusbandIdEqualsOrWifeIdEquals(Long husbandId, Long wifeId);
}
