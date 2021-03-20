package by.ladyka.family.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import javax.persistence.Column;
import java.time.Instant;

@Getter
@Setter
public abstract class AbstractAuditEntity {
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
    @Version
    @Column(name = "version")
    private Long version;
}
