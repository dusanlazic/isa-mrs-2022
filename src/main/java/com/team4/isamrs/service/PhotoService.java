package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.exception.PhotoNotFoundException;
import com.team4.isamrs.exception.PhotoPathTraversalException;
import com.team4.isamrs.exception.PhotoStorageException;
import com.team4.isamrs.exception.PhotoUploadException;
import com.team4.isamrs.model.advertisement.Photo;
import com.team4.isamrs.repository.PhotoRepository;
import com.team4.isamrs.util.StorageConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class PhotoService {

    private final Path uploadsLocation;

    private final Set<String> allowedContentTypes;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    public PhotoService(StorageConfig config) {
        this.uploadsLocation = Paths.get(config.getUploadsLocation());
        this.allowedContentTypes = config.getAllowedContentTypes();
    }

    public <T extends DisplayDTO> T findById(UUID uuid, Class<T> returnType) {
        Photo photo = photoRepository.findById(uuid).orElseThrow();

        return modelMapper.map(photo, returnType);
    }

    public Photo store(MultipartFile file) {
        validateFileNotEmpty(file);
        validateContentType(file.getContentType());

        Photo photo = createPhotoFromMultipartFile(file);
        Path uploadedFilePath = uploadsLocation.resolve(
                Paths.get(getSecureStoredFilename(photo)))
                .normalize().toAbsolutePath();
        validatePath(uploadedFilePath);

        try (InputStream is = file.getInputStream()) {
            Files.copy(is, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);
            photoRepository.save(photo);
        } catch (IOException e) {
            throw new PhotoStorageException("Failed to store file.", e);
        }

        return photo;
    }

    public ResponseEntity<Resource> serve(UUID uuid) throws MalformedURLException {
        Photo photo = photoRepository.findById(uuid).orElseThrow(PhotoNotFoundException::new);
        Path uploadedFilePath = uploadsLocation.resolve(getSecureStoredFilename(photo));

        Resource resource = new UrlResource(uploadedFilePath.toUri());
        if (!resource.exists() || !resource.isReadable())
            throw new PhotoStorageException("Could not serve file.");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getContentType()))
                .body(resource);
    }

    public void init() {
        try {
            FileSystemUtils.deleteRecursively(uploadsLocation.toFile());
            Files.createDirectories(uploadsLocation);
        }
        catch (IOException e) {
            throw new PhotoStorageException("Could not initialize storage", e);
        }
    }

    private Photo createPhotoFromMultipartFile(MultipartFile file) {
        Photo photo = new Photo();
        UUID uuid = UUID.randomUUID();

        photo.setId(uuid);
        photo.setOriginalFilename(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setContentType(file.getContentType());
        return photo;
    }

    private String getSecureStoredFilename(Photo photo) {
        return photo.getId() + "." + extensionFromMediaType(photo.getContentType());
    }

    private String extensionFromMediaType(@Nullable String mediaType) {
        if (mediaType == null)
            throw new PhotoUploadException("Invalid file format.");
        if (mediaType.equals(MediaType.IMAGE_JPEG_VALUE))
            return "jpg";
        if (mediaType.equals(MediaType.IMAGE_PNG_VALUE))
            return "png";

        throw new PhotoUploadException("Invalid file format.");
    }

    private void validateFileNotEmpty(MultipartFile file) {
        if (file.isEmpty())
            throw new PhotoUploadException("File is empty.");
    }

    private void validateContentType(String mediaType) {
        if (!allowedContentTypes.contains(mediaType))
            throw new PhotoUploadException("Invalid file format.");
    }

    private void validatePath(Path path) {
        if (!path.getParent().equals(uploadsLocation.toAbsolutePath()))
            throw new PhotoPathTraversalException();
    }
}
