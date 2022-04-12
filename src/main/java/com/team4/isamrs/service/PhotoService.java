package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.advertisement.Photo;
import com.team4.isamrs.repository.PhotoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PhotoService {
    /*
    Placeholder before supporting file uploads.
     */
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> T findById(UUID uuid, Class<T> returnType) {
        Photo photo = photoRepository.findById(uuid).orElseThrow();

        return modelMapper.map(photo, returnType);
    }

    public Photo create() {
        /* Note:
        Photo.uploader should be set to the current logged-in user.
         */
        Photo photo = new Photo();

        photoRepository.save(photo);
        return photo;
    }
}
