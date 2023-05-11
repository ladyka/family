package by.ladyka.family.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "children")
public class ChildrenEntity extends AbstractAuditEntity implements BaseEntity<String> {
    @Id
    private String id;
    private String marriageId;
    private String childId;
}
