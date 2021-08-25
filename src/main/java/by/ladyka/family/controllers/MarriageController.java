package by.ladyka.family.controllers;

import by.ladyka.family.dto.MarriageDto;
import by.ladyka.family.services.MarriageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/marriage")
public class MarriageController {
    private final MarriageService marriageService;

    @GetMapping
    public List<MarriageDto> findAllByPerson(Long personId) {
        return marriageService.findAllMarriages(personId);
    }

    @PostMapping("/add")
    public MarriageDto add(@RequestBody MarriageDto dto) {
        return marriageService.create(dto);
    }

    @PutMapping("/{marriageId}")
    public void update(@PathVariable long marriageId, @RequestBody MarriageDto dto) {
        marriageService.update(marriageId, dto);
    }
}
