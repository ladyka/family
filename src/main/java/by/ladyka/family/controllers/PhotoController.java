package by.ladyka.family.controllers;

import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.Photo;
import by.ladyka.family.services.PersonService;
import by.ladyka.family.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PersonService personService;
    private final PhotoService photoService;


    @RequestMapping("/person/{personId}/photos")
    public String getPhotos(@PathVariable ("personId")Long personId, Model model){
        Person foundPerson = this.personService.findById(personId);
        List<Photo> photos = foundPerson.getPhotos();
        model.addAttribute("photos", photos);
        model.addAttribute("person", foundPerson);
        return "personPhotos";
    }

    @RequestMapping("/person/photos")
    public String addPhoto(Long personId, String description, @RequestParam ("fileToUpload")MultipartFile file, Model model) throws IOException {

        this.photoService.addPhoto(personId, description, file.getInputStream(), file.getOriginalFilename());
        return getPhotos(personId, model);
    }

    @RequestMapping("/photo/{photoId}")
    public ResponseEntity<Resource> findPhotos(@PathVariable Long photoId) throws MalformedURLException {
        String photoPath = this.photoService.getPhotoById(photoId);

        Resource file = new FileUrlResource(photoPath);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
