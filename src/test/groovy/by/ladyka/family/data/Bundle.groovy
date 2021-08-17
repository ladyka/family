package by.ladyka.family.data

import by.ladyka.family.entity.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository

class Bundle<EntityType, ID> implements Deletable {

    JpaRepository<EntityType, ID> repository
    BaseEntity<ID> entity
    List<Deletable> relatedEntities = []

    Bundle(JpaRepository<EntityType, ID> repository) {
        this.repository = repository
    }

    void append(Closure closure) {
        relatedEntities.add(0, new Deletable() {
            @Override
            def delete() {
                closure.call()
            }
        })
    }

    BaseEntity appendAndGet(Bundle bundle) {
        relatedEntities.add(0, bundle)
        bundle.entity
    }

    @Override
    def delete() {
        repository.deleteById(entity.getId())
        deleteRelatedEntities()
    }

    def deleteRelatedEntities() {
        relatedEntities.each {
            it.delete()
        }
    }
}
