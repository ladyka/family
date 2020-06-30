package by.ladyka.family.repositories;

import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.PersonRelation;
import by.ladyka.family.entity.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRelationRepository extends JpaRepository<PersonRelation, Long> {
    List<PersonRelation> findAllByParentAndRelation(Person parent, RelationType relation);
    List<PersonRelation> findAllByChildAndRelation(Person child, RelationType relation);
}
