package ro.scoalainformala.studentmgmt.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.scoalainformala.studentmgmt.exceptions.FileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

    public String storeFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() +  "_" + file.getOriginalFilename();
        Path filePath = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public Resource loadFile(String fileName) throws MalformedURLException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }
}
