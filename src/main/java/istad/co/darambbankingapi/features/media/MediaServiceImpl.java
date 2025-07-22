package istad.co.darambbankingapi.features.media;


import istad.co.darambbankingapi.features.media.dto.MediaResponse;
import istad.co.darambbankingapi.util.MediaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadSingle(MultipartFile file, String folderName) {
        log.info(file.getContentType());

        // Generate new unique name for file upload
        String newName = UUID.randomUUID().toString();

        String extension = MediaUtil.extractExtension(file.getOriginalFilename());
        log.info("Extension: {}", extension);
        newName = newName + "." + extension;
        log.info("New name: {}", newName);

        // Copy file to server
        Path path = Paths.get(serverPath + folderName + "\\" + newName);
        try {
            Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
        return MediaResponse.builder()
                .name(newName)
                .contentType(file.getContentType())
                .extension(extension)
                .size(file.getSize())
                .uri(String.format("%s%s/%s", baseUri, folderName, newName))
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {

        // Create empty array list, wait for adding uploaded file
        List<MediaResponse> mediaResponses = new ArrayList<>();

        // Use loop to upload each file
        files.forEach(file -> {
            MediaResponse mediaResponse = this.uploadSingle(file,folderName);
            mediaResponses.add(mediaResponse);
        });
        return mediaResponses;
    }

    @Override
    public MediaResponse loadMediaByName(String mediaName, String folderName) {

        // Create absolute path of media
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try {
            Resource resource = new UrlResource(path.toUri());
            log.info("Load resource: {}", resource.getFilename());
            if (!resource.exists()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media not found");
            }
            return MediaResponse.builder()
                    .name(mediaName)
                    .contentType("")
                    .extension(MediaUtil.extractExtension(mediaName))
                    .size(resource.contentLength())
                    .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                    .build();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
    }

    @Override
    public List<MediaResponse> loadAllMedia(String folderName) {
        List<MediaResponse> mediaResponses = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(serverPath + folderName + "\\"))) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            String mediaName = path.getFileName().toString();

                            MediaResponse mediaResponse = MediaResponse.builder()
                                    .name(mediaName)
                                    .contentType(Files.probeContentType(path))
                                    .extension(MediaUtil.extractExtension(mediaName))
                                    .size(Files.size(path))
                                    .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                                    .build();

                            mediaResponses.add(mediaResponse);
                        } catch (IOException e) {
                            log.warn("Failed to load media file: {}", path, e);
                        }
                    });
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read media directory");
        }

        return mediaResponses;
    }


    @Override
    public MediaResponse deleteMediaByName(String mediaName, String folderName) {

        // Create absolute path of media
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try {
            if (Files.deleteIfExists(path)){
            return  MediaResponse.builder()
                    .name(mediaName)
                    .contentType("")
                    .extension(MediaUtil.extractExtension(mediaName))
                    .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                    .build();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Media not found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
    }

    @Override
    public Resource downloadMediaByName(String mediaName, String folderName) {

        // Create absolute path of media
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Media has been not found");
        }
    }
}
