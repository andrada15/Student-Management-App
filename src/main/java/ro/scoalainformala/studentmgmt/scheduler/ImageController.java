package ro.scoalainformala.studentmgmt.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageUploadBroker imageUploadBroker;

    @Autowired
    public ImageController(ImageUploadBroker imageUploadBroker) {
        this.imageUploadBroker = imageUploadBroker;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String imageUrl = imageUploadBroker.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            log.error("Failed to upload image", e);
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }
}
