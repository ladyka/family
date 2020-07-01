package by.ladyka.family.services;

import by.ladyka.family.config.ApplicationProperties;
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
    private final ApplicationProperties applicationProperties;

    public void addPhoto(Long personId, String description, InputStream inputStream, String originalFilename) throws IOException {
        File filePhoto = new File(applicationProperties.getDatadir() + File.separator
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

    public List<Photo> getTopPersonPhotos(Long personId, int count) {
        return photoRepository.findTopByPerson(personId, count);
    }
}
