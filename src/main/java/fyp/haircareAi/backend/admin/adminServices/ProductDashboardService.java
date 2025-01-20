package fyp.haircareAi.backend.admin.adminServices;

import fyp.haircareAi.backend.admin.cache.ProductCache;
import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.repositories.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductDashboardService {

    @Autowired
    private ProductCache productCache;
    @Autowired
    private ProductRepo productRepo;

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
    public ProductEntity addProductInDb(ProductEntity product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());


        ProductEntity insertedproduct=productRepo.save(product);
        productCache.insertintolist(insertedproduct);
        return insertedproduct;
    }


    public ProductEntity getProductById(long id){

        Optional<ProductEntity> product=productRepo.findById(id);

        return product.orElse(null);
    }
    public ResponseEntity<byte[]> getProductImage(long productId) {
        Optional<ProductEntity> product = productRepo.findById(productId);
        System.out.println(product);

        if (product.isPresent() && product.get().getImageData() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(product.get().getImageType()))
                    .body(product.get().getImageData());
        }
        return ResponseEntity.notFound().build();
    }




}
