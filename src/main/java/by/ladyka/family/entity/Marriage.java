package by.ladyka.family.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "marriges")
public class Marriage implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marrige_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "husband_id")
    private Person husband;

    @ManyToOne
    @JoinColumn(name = "wife_id")
    private Person wife;

    private LocalDate registration;

    private LocalDate divorce;

    @Enumerated(EnumType.STRING)
    private MarriageType marriageType;

}
