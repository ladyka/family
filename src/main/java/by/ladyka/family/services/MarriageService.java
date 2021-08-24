package by.ladyka.family.services;

import by.ladyka.family.dto.MarriageDto;
import by.ladyka.family.dto.PersonDto;
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
        return marriageRepository.findAllByWife(wife).orElseThrow(() -> new NoSuchElementException("не была замужем")).stream()
                .map(Marriage::getHusband)
                .collect(Collectors.toList());
    }

    public List<Person> getWifes(Person husband) {
        return marriageRepository.findAllByHusband(husband).orElseThrow(() -> new NoSuchElementException("холост")).stream()
                .map(Marriage::getWife)
                .collect(Collectors.toList());
    }

    public MarriageDto create(MarriageDto marriageDto, PersonDto husbandDto, PersonDto wifeDto) {
        PersonDto husband = personService.create(husbandDto);
        PersonDto wife = personService.create(wifeDto);
        Marriage marriage = marriageRepository.save(marriageMapper.toEntity(new Marriage(), marriageDto, husband, wife));
        return marriageMapper.toDto(marriage);
    }
}
