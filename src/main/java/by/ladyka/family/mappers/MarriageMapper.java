package by.ladyka.family.mappers;

import by.ladyka.family.dto.MarriageDto;
import by.ladyka.family.entity.Marriage;
import by.ladyka.family.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarriageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "husband", source = "husband")
    @Mapping(target = "wife", source = "wife")
    Marriage toEntity(@MappingTarget Marriage marriage, MarriageDto dto, Person husband, Person wife);

    @Mapping(target = "id", source = "marriage.id")
    @Mapping(target = "husbandId", source = "husband.id")
    @Mapping(target = "wifeId", source = "wife.id")
    MarriageDto toDto(Marriage marriage);
}
