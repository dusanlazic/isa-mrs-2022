package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.exception.PhotoNotFoundException;
import com.team4.isamrs.exception.PhotoPathTraversalException;
import com.team4.isamrs.exception.PhotoStorageException;
import com.team4.isamrs.exception.PhotoUploadException;
import com.team4.isamrs.model.advertisement.Photo;
import com.team4.isamrs.repository.PhotoRepository;
import com.team4.isamrs.util.StorageConfig;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final Tika tika = new Tika();
    private final MimeTypes mimeTypes = TikaConfig.getDefaultConfig().getMimeRepository();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    public PhotoService(StorageConfig config) {
        this.uploadsLocation = Paths.get(config.getUploadsLocation());
        this.allowedContentTypes = config.getAllowedContentTypes();
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

    public <T extends DisplayDTO> T findById(UUID uuid, Class<T> returnType) {
        Photo photo = photoRepository.findById(uuid).orElseThrow();

        return modelMapper.map(photo, returnType);
    }

    public Photo store(MultipartFile file) {
        String contentType = detectContentType(file);
        Photo photo = createPhotoFromMultipartFile(file, contentType);
        Path uploadedFilePath = createDestinationPath(photo);

        try (InputStream is = file.getInputStream()) {
            Files.copy(is, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);
            photoRepository.save(photo);
        } catch (IOException e) {
            throw new PhotoStorageException("Failed to store file.", e);
        }

        return photo;
    }

    public ResponseEntity<Resource> serve(String filename) throws IOException {
        Path uploadedFilePath = uploadsLocation.resolve(filename);
        validatePath(uploadedFilePath);

        Resource resource = new UrlResource(uploadedFilePath.toUri());
        if (!resource.exists() || !resource.isReadable())
            throw new PhotoNotFoundException();

        String contentType = detectContentType(resource.getFile());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    private Photo createPhotoFromMultipartFile(MultipartFile file, String contentType) {
        Photo photo = new Photo();
        UUID uuid = UUID.randomUUID();

        photo.setId(uuid);
        photo.setOriginalFilename(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setStoredFilename(createStoredFileName(uuid, contentType));

        return photo;
    }

    private String createStoredFileName(UUID uuid, String contentType) {
        try {
            return uuid + mimeTypes.forName(contentType).getExtension(); // i.e. appends ".png"
        } catch (MimeTypeException e) {
            throw new PhotoUploadException("Invalid file format.", e);
        }
    }

    private Path createDestinationPath(Photo photo) {
        Path path = uploadsLocation.resolve(photo.getStoredFilename()).normalize().toAbsolutePath();
        validatePath(path);
        return path;
    }

    private String detectContentType(MultipartFile file) {
        /*
        Prevents non-image files from being uploaded and stored.
        Changing file extension, content-type header or manipulating
        magic bytes cannot pass this check.
         */
        if (file.isEmpty())
            throw new PhotoUploadException("File is empty.");
        if (!allowedContentTypes.contains(file.getContentType()))
            throw new PhotoUploadException("Invalid file format.");
        try {
            String detectedType = tika.detect(file.getBytes());
            if (!allowedContentTypes.contains(detectedType))
                throw new PhotoUploadException("Yeah, sure. It's totally an image file.");
            return detectedType;
        } catch (IOException e) {
            throw new PhotoUploadException("Invalid file format.", e);
        }
    }

    private String detectContentType(File file) {
        /*
        Prevents non-image files from being served. Performance of this check
        should be examined since application has to serve lots of photos.
        Uploading is not a performance concern since it is used less often.
         */
        try {
            String detectedType = tika.detect(file);
            if (!allowedContentTypes.contains(detectedType))
                throw new PhotoNotFoundException();
            return detectedType;
        } catch (IOException e) {
            throw new PhotoNotFoundException();
        }
    }

    private void validatePath(Path path) {
        if (path.isAbsolute()) {
            if (!path.getParent().equals(uploadsLocation.toAbsolutePath()))
                throw new PhotoPathTraversalException();
        } else {
            if (!path.normalize().toAbsolutePath().getParent().equals(uploadsLocation.toAbsolutePath()))
                throw new PhotoPathTraversalException();
        }
    }
}
