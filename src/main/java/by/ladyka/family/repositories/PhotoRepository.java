package by.ladyka.family.repositories;

import by.ladyka.family.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query(value = "select ph.* from person_has_photos " +
            " left join photos ph on person_has_photos.photo_id = ph.photo_id " +
            " where person_id = :personId limit :limit", nativeQuery = true)
    List<Photo> findTopByPerson(
            @Param("personId") Long personId,
            @Param("limit") int limit);



}
