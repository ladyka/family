package by.ladyka.family.repositories;

import by.ladyka.family.entity.MarriageEntity;
import by.ladyka.family.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarriageRepository extends JpaRepository<MarriageEntity, String> {
    List<MarriageEntity> findByHusband(Person husband);

    Optional<MarriageEntity> findByHusbandAndWife(Person husband, Person wife);

    List<MarriageEntity> findByWife(Person wife);
}
