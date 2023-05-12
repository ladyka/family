package by.ladyka.family.repositories;

import by.ladyka.family.entity.ChildrenEntity;
import by.ladyka.family.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildrenRepository extends JpaRepository<ChildrenEntity, String> {
    Optional<ChildrenEntity> findByChild(Person child);
}
