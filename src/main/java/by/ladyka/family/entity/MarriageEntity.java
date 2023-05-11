package by.ladyka.family.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "marriage")
public class MarriageEntity extends AbstractAuditEntity implements BaseEntity<String> {
    @Id
    private String id;
    private String husbandId;
    private String wifeId;
    private LocalDate relationStartDate;
    private LocalDate startDate;
    private LocalDate endDate;
}
