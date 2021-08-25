package by.ladyka.family.services;

import by.ladyka.family.dto.MarriageDto;
import by.ladyka.family.entity.Marriage;
import by.ladyka.family.entity.Person;
import by.ladyka.family.mappers.MarriageMapper;
import by.ladyka.family.repositories.MarriageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarriageService {
    private final MarriageRepository marriageRepository;
    private final MarriageMapper marriageMapper;
    private final PersonService personService;

    public List<Person> getHusbands(Person wife) {
        return marriageRepository.findAllByWife(wife).stream()
                .map(Marriage::getHusband)
                .collect(Collectors.toList());
    }

    public List<Person> getWifes(Person husband) {
        return marriageRepository.findAllByHusband(husband).stream()
                .map(Marriage::getWife)
                .collect(Collectors.toList());
    }

    public List<MarriageDto> findAllMarriages(Long personId) {
        List<Marriage> marriages = marriageRepository.findByHusbandIdEqualsOrWifeIdEquals(personId, personId);
        return marriages.stream().map(marriageMapper::toDto).collect(Collectors.toList());
    }

    public MarriageDto create(MarriageDto marriageDto) {
        Person husband = personService.findById(marriageDto.getHusbandId());
        Person wife = personService.findById(marriageDto.getWifeId());
        Marriage marriage = marriageRepository.save(marriageMapper.toEntity(new Marriage(), marriageDto, husband, wife));
        return marriageMapper.toDto(marriage);
    }

    public void update(Long marrigeId, MarriageDto dto) {
        Marriage marriage = marriageRepository.findById(marrigeId).orElseThrow(NoSuchElementException::new);
        Marriage updated = marriageMapper.toEntity(marriage, dto, marriage.getHusband(), marriage.getWife());
        marriageRepository.save(updated);
    }
}
