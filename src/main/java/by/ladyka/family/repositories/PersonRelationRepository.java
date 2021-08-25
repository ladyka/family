package by.ladyka.family.repositories;

import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.PersonRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRelationRepository extends JpaRepository<PersonRelation, Long> {
    List<PersonRelation> findAllByParent(Person parent);

    List<PersonRelation> findAllByChild(Person child);
}
