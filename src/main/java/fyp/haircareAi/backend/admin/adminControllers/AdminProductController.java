package fyp.haircareAi.backend.admin.adminControllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import fyp.haircareAi.backend.admin.adminServices.AdminUserService;

import fyp.haircareAi.backend.admin.adminServices.ImageService;
import fyp.haircareAi.backend.admin.adminServices.ProductDashboardService;
import fyp.haircareAi.backend.admin.adminServices.UserDashboardService;
import fyp.haircareAi.backend.admin.cache.ProductCache;
import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/dashboard/productdashboard")
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminProductController {
    @Autowired
    private ProductDashboardService productDashboardService;

    @Autowired
    private ProductCache productCache;

    @Autowired
    private ImageService imageService;



//    @GetMapping
//    public ResponseEntity<Map<String, Object>>getAdminDashboardDataForProducts(){
//        Map<String, Object> userData=productDashboardService.getProductDashboardData();
//        return ResponseEntity.ok(userData);
//
//    }

//add product
@PostMapping(value = "/insertproduct", consumes = {"multipart/form-data"})
public ResponseEntity<?> insertProduct(
        @RequestPart("product") String productJson,
        @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
) {
    try {
        // Convert JSON string to ProductEntity
        ObjectMapper objectMapper = new ObjectMapper();
        ProductEntity product = objectMapper.readValue(productJson, ProductEntity.class);

        // Call service to handle saving
        return productDashboardService.addProductInDb(product, imageFile);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("Error while processing product: " + e.getMessage());
    }
}


//get all products
    @GetMapping("/allproducts")
    public ResponseEntity<List<ProductEntity>> getAlProducts() {

        return productDashboardService.allProducts();
    }
//    @GetMapping("/allAdmins")
//    public Object allAdmins() {
//
//        List<UserEntity> entries = adminUserService.getAllAdmins();
//        if ( entries !=null && !entries.isEmpty() ) {
//            return entries;
//        } else {
//            return "no user found";
//        }
//    }


//get product by product id
    @GetMapping("/getproductById/{id}")
    public ResponseEntity<ProductEntity> getUserByEmail(@PathVariable long id) {
        return productDashboardService.getProductById(id);
    }




//get product image
    @PostMapping("/getproductImage")
    public ResponseEntity<byte[]> getProductImage(@RequestBody Map<String, String> payload) {
        String imagePath = payload.get("imagePath");
        System.out.println(imagePath);
        return imageService.getImage(imagePath);
    }



//delete product
    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<?> deleteproduct(@PathVariable("id") long productId) {

        return productDashboardService.deleteProduct(productId);

    }

//update product
    @PostMapping(value = "/updateProduct/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(
            @PathVariable long productId,
            @RequestPart("product") ProductEntity product,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {

        return productDashboardService.updateProduct(productId,product,imageFile);


    }

//    @PostMapping(value = "/updateproduct/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> updateProduct(
//            @PathVariable long productId,
//            @RequestPart("product") ProductEntity product,
//            @RequestPart("imageFile") MultipartFile imageFile
//    ) {
//        System.out.println(product);
//
//
//       return productDashboardService.updateProduct(productId,product,imageFile);
//
//    }
//
//    @GetMapping("/activeusers")
//    public List<UserEntity> getAllActiveUsers() {
//        return adminUserService.activeUsers();
//
//    }



}
