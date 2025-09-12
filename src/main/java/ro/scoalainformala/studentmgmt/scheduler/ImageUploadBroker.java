package ro.scoalainformala.studentmgmt.scheduler;

import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ro.scoalainformala.studentmgmt.exceptions.UploadFailedException;

import java.util.Base64;

@Component
public class ImageUploadBroker {

    private static final String API_KEY = "fe39ddef7f23e48a86bea533d24f337c";
    private final RestTemplate restTemplate;

    public ImageUploadBroker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SneakyThrows
    public String uploadImage(final MultipartFile image) {
        final String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("key", API_KEY);
        body.add("expiration", "86400");
        body.add("image", base64Image);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<ImgbbResponse> response =
                restTemplate.postForEntity("https://api.imgbb.com/1/upload", request, ImgbbResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getData().getDisplayUrl();
        } else {
            throw new UploadFailedException("Unable to upload image");
        }
    }
}
