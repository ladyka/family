package by.ladyka.family.services;

import by.ladyka.family.entity.Person;
import by.ladyka.family.entity.Photo;
import by.ladyka.family.repositories.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final PersonService personService;

    public List<Photo> photos(Person person) {
        return person.getPhotos();

    }

    public void addPhoto(Long personId, String description, InputStream inputStream, String originalFilename) throws IOException {
        File filePhoto = new File("/ladyka/family/data" + File.separator
                + System.currentTimeMillis() + originalFilename);
        FileOutputStream os = new FileOutputStream(filePhoto);
        IOUtils.copy(inputStream, os);
        inputStream.close();
        os.close();

        Photo photo = new Photo();
        photo.setName(filePhoto.getAbsolutePath());
        photo.setDescription(description);
        photo.setPersons(Collections.singletonList(this.personService.findById(personId)));
        this.photoRepository.save(photo);
    }

    public String getPhotoById(Long photoId) {
        Photo photo = this.photoRepository.findById(photoId).orElseThrow(() -> new RuntimeException("Photo was not found"));
        return photo.getName();
    }
}
