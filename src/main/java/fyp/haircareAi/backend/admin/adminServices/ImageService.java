package fyp.haircareAi.backend.admin.adminServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${spring.image.storage.path}")
    private String storagePath;

    String imagePath;

    public String insertImage(MultipartFile image){

        try {
            File directory=new File(storagePath);
            if(!directory.exists()){
                directory.mkdir();
            }

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath= Paths.get(storagePath,fileName);
            Files.write(filePath,image.getBytes());

            String contentType = image.getContentType();
            if (contentType == null) { // If content type is null, use a default
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }


            this.imagePath = filePath.toString().replace("\\", "/");
            return this.imagePath;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ResponseEntity<byte[]> getImage(String imagePath) {
        try {
            Path filePath = Paths.get(imagePath);

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    public boolean deleteImage(String imagePath) {
        try {
            Path filePath = Paths.get(imagePath);

            if (!Files.exists(filePath)) {
                return false; // File does not exist
            }

            Files.delete(filePath);
            return true; // Successfully deleted

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failed to delete
        }
    }




}
