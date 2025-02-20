package fyp.haircareAi.backend.admin.adminControllers;


import fyp.haircareAi.backend.admin.adminServices.AdminUserService;

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
public class AdminProductController {
    @Autowired
    private ProductDashboardService productDashboardService;

    @Autowired
    private ProductCache productCache;



    @GetMapping
    public ResponseEntity<Map<String, Object>>getAdminDashboardDataForProducts(){
        Map<String, Object> userData=productDashboardService.getProductDashboardData();
        return ResponseEntity.ok(userData);

    }
    @PostMapping(value = "/insertproduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestPart("product") ProductEntity product,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {

        try {
            ProductEntity productadded = productDashboardService.addProductInDb(product, imageFile);
            return new ResponseEntity<>(productadded, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build(); // Added .build()
        }
    }



    @GetMapping("/allproducts")
    public ResponseEntity<List<ProductEntity>> getAlProducts() {
        List<ProductEntity> products = productCache.getAllProductsList();
        if ( products !=null && !products.isEmpty() ) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return ResponseEntity.internalServerError().build();
        }
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
    @GetMapping("/getproductById/{id}")
    public Object getUserByEmail(@PathVariable long id) {
        ProductEntity product = productDashboardService.getProductById(id);
        if ( product !=null) {
            return product;
        } else {
            return "no user found of email :"+id;
        }
    }
    @GetMapping("/getImageByProductId/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable("id") long productId) {
        return productDashboardService.getProductImage(productId);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<?> deleteproduct(@PathVariable("id") long productId) {

        boolean isDeleted=productCache.deleteProduct(productId);

        if (isDeleted) {
             return ResponseEntity.ok().build();


        }
        return ResponseEntity.notFound().build();
    }
//
//    @GetMapping("/activeusers")
//    public List<UserEntity> getAllActiveUsers() {
//        return adminUserService.activeUsers();
//
//    }



}
