package by.ladyka.family.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "marriage")
public class MarriageEntity extends AbstractAuditEntity implements BaseEntity<String> {
    @Id
    private String id;

    @JoinColumn(name = "husband_id")
    @ManyToOne
    private Person husband;

    @JoinColumn(name = "wife_id")
    @ManyToOne
    private Person wife;
    private LocalDate relationStartDate;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "marriage")
    private List<ChildrenEntity> children = new ArrayList<>();
}
