package istad.co.darambbankingapi.features.media;

import istad.co.darambbankingapi.features.media.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;

public interface MediaService {

    // Handle upload single file
    MediaResponse uploadSingle(MultipartFile file, String folderName);
}
