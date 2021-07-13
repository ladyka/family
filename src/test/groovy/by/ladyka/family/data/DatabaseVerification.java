package by.ladyka.family.data;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class DatabaseVerification {

    @PersistenceContext
    private EntityManager em;

    public List<String> findTableNames() {
        return em.createNativeQuery("SHOW TABLES")
                .getResultList();
    }

    public Map<String, Long> getRowCounts(List<String> tableNames) {
        return tableNames.stream()
                .map(tableName -> String.format("SELECT count(*), \"%s\" from %s", tableName, tableName))
                .map(sqlString -> em.createNativeQuery(sqlString))
                .map(Query::getSingleResult)
                .map(a -> (Object[]) a)
                .collect(toMap(a -> (String) a[1], a -> ((BigInteger) a[0]).longValue()));
    }
}
