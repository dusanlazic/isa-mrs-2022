package com.team4.isamrs.service;

import com.team4.isamrs.model.advertisement.Photo;
import com.team4.isamrs.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {
    /*
    Placeholder before supporting file uploads.
     */
    @Autowired
    private PhotoRepository photoRepository;

    public Optional<Photo> findById(UUID uuid) {
        return photoRepository.findById(uuid);
    }

    public UUID createPhoto() {
        /* Note:
        Photo.uploader should be set to the current logged-in user.
         */
        Photo photo = new Photo();
        try {
            photoRepository.save(photo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return photo.getId();
    }
}
