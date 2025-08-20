package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.admin.adminServices.ImageService;
import fyp.haircareAi.backend.user.services.ImageServiceExtra;
import fyp.haircareAi.backend.user.services.UserService;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

  @Autowired
  private ImageService imageService;


    @GetMapping("/getImage")
    public ResponseEntity<byte[]> getImage(@RequestParam String imagePath){
        System.out.println(imagePath);
         return imageService.getImage(imagePath);
    }

    @PostMapping(value = "/upload" +
            "Image", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadProfileImage(
            @RequestHeader("Authorization") String token,
            @RequestPart("imageFile") MultipartFile imageFile) {

        System.out.println("errroorrrrrrrrrrrrrrrrrrrrrrrrrrr");

        try {
            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file is missing");
            }
            String emil=jwtUtil.extractUsername(token.substring(7).trim());
            System.out.println(emil);
            return userService.insertImage(emil,imageFile);

            // Handle image saving logic (e.g., to DB, file system, or cloud storage)
//            String imageUrl = imageService.insertImage(imageFile); // hypothetical method

//            return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while uploading image: " + e.getMessage());
        }
    }

    @PostMapping(value = "/uploadHairImage" +
            "Image", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadHairImage(
            @RequestHeader("Authorization") String token,
            @RequestPart("imageFile") MultipartFile imageFile) {

        try {
            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file is missing");
            }
            String emil=jwtUtil.extractUsername(token.substring(7).trim());
            System.out.println(emil);
            return userService.AnalyseImage(emil,imageFile);

            // Handle image saving logic (e.g., to DB, file system, or cloud storage)
//            String imageUrl = imageService.insertImage(imageFile); // hypothetical method

//            return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while uploading image: " + e.getMessage());
        }
    }



//    @PostMapping("/Uploadprofileimage")
//    public ResponseEntity<byte[]> getUserImage(
//            @RequestHeader("Authorization") String token,
//            @RequestParam("image") MultipartFile image
//    ){
//
//        String emil=jwtUtil.extractUsername(token.substring(7).trim());
//        System.out.println(emil);
//        return userService.insertImage(emil,image);
//
//
//    }

}
