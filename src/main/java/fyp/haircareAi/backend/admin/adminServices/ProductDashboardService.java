package fyp.haircareAi.backend.admin.adminServices;

import fyp.haircareAi.backend.admin.cache.ProductCache;
import fyp.haircareAi.backend.user.entities.ProblemEntity;
import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.repositories.ProblemRepo;
import fyp.haircareAi.backend.user.repositories.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductDashboardService {

    @Autowired
    private ProductCache productCache;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProblemRepo problemRepo;
    public Map<String, Object> getProductDashboardData() {
        Map<String, Object> productData = new HashMap<>();

        // Populate product-related data
        productData.put("totalProducts", productCache.getTotalProducts());
        productData.put("averageProductRating", productCache.getAverageProductRating());
        productData.put("totalRecommendations", productCache.getTotalRecommendations());
        productData.put("averageRatingByProblem", productCache.getAverageRatingByProblem());
//        productData.put("allProducts", productCache.getAllProductsList());

        return productData;
    }



    @Transactional
    public ResponseEntity<ProductEntity> addProductInDb(ProductEntity product, MultipartFile imageFile){


        try {


            String path = imageService.insertImage(imageFile);
            if (path != null) {
                product.setImagePath(path);
                ProductEntity addedProduct =productRepo.save(product);
                Optional<ProblemEntity> problemOptional=problemRepo.findById(Math.toIntExact(addedProduct.getProblem().getProblemId()));

                if(problemOptional.isPresent()) {
                    addedProduct.setProblem(problemOptional.get());
                    return ResponseEntity.ok(addedProduct);
                }
            }
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

//get product by product id
    public ResponseEntity<ProductEntity> getProductById(long id){

        try {
            Optional<ProductEntity> product = productRepo.findById(id);
            if (product.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    public ResponseEntity<byte[]> getProductImage(long productId) {
        Optional<ProductEntity> product = productRepo.findById(productId);
        System.out.println(product);


        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> updateProduct(long productId,ProductEntity updatedProduct, MultipartFile imageFile) {
        try {

            Optional<ProductEntity> dbProductOpt = productRepo.findById(productId);

            if (dbProductOpt.isEmpty()) {
                return ResponseEntity.notFound().build(); // Return 404 if product is not found
            }

            ProductEntity existingProduct = dbProductOpt.get();

            System.out.println(updatedProduct);
            if(!imageFile.isEmpty()){

                boolean delete=imageService.deleteImage(existingProduct.getImagePath());
                if(delete){
                    String imagePath=imageService.insertImage(imageFile);
                    existingProduct.setImagePath(imagePath);
                }
                else{
                    ResponseEntity.notFound().build();
                }

            }






            // Update only non-null fields to avoid overwriting with null values
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setProblem(updatedProduct.getProblem());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setRating(updatedProduct.getRating());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setRecommendations(updatedProduct.getRecommendations());
            existingProduct.setStatus(updatedProduct.getStatus());

            ProductEntity newProduct=productRepo.save(existingProduct); // Save updated product

            return ResponseEntity.ok(newProduct); // Return updated product
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<ProductEntity>> allProducts(){
        List<ProductEntity> products = productRepo.findAll();
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
    public ResponseEntity<?> deleteProduct(long id){
        try{
            productRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("product  Deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }





}
