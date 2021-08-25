package by.ladyka.family.dto;

import by.ladyka.family.entity.MarriageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MarriageDto {
    private Long id;
    private Long husbandId;
    private Long wifeId;
    private LocalDate registration;
    private LocalDate divorce;
    private MarriageType marriageType;
}
