package fyp.haircareAi.backend.admin.cache;

import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.repositories.ProductRepo;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductCache {

    @Autowired
    private ProductRepo productRepo;

    @Getter
    private List<ProductEntity> products;

    public void  insertintolist(ProductEntity productToInserted){
        this.products.add(productToInserted);
    }

    @PostConstruct
    public void getAllProducts() {
        // Load all products into cache at initialization
        products = productRepo.findAll();
    }

    // Total products
    public long getTotalProducts() {
        return products.size();
    }

    // Average product rating
    public double getAverageProductRating() {
        return products.stream()
                .filter(product -> product.getRating() != null) // Exclude null ratings
                .mapToDouble(ProductEntity::getRating)
                .average()
                .orElse(0.0); // Return 0.0 if no ratings available
    }

    // Total recommendations generated
    public int getTotalRecommendations() {
        return products.stream()
                .mapToInt(ProductEntity::getRecommendations)
                .sum();
    }

    // Bar chart: Average rating of products by problem_id
    public Map<String, Double> getAverageRatingByProblem() {
        return products.stream()
                .filter(product -> product.getRating() != null) // Exclude products with null ratings
                .collect(Collectors.groupingBy(
                        product -> product.getProblem().getProblemName(),
                        Collectors.averagingDouble(ProductEntity::getRating)
                ));
    }

    // Get a list of all products for display
    public List<ProductEntity> getAllProductsList() {
        return products;
    }

    // Filter products by rating
    public List<ProductEntity> filterProductsByRating(double minRating) {
        return products.stream()
                .filter(product -> product.getRating() != null && product.getRating() > minRating)
                .collect(Collectors.toList());
    }

    // Filter products by problem_id
    public List<ProductEntity> filterProductsByProblemId(long problemId) {
        return products.stream()
                .filter(product -> product.getProblem().getProblemId().equals(problemId))
                .collect(Collectors.toList());
    }

    // Add a new product
    public void addProduct(ProductEntity product) {
        product.setCreatedAt(LocalDateTime.now()); // Ensure createdAt is set
        products.add(product);
        productRepo.save(product); // Persist to database
    }

    // Edit an existing product
    public boolean editProduct(long productId, ProductEntity updatedProduct) {
        for (ProductEntity product : products) {
            if (product.getProductId() == productId) {  // Use == for primitive long comparison
                product.setName(updatedProduct.getName());
                product.setDescription(updatedProduct.getDescription());
                product.setPrice(updatedProduct.getPrice());
                product.setRating(updatedProduct.getRating());
                product.setRecommendations(updatedProduct.getRecommendations());
                product.setStatus(updatedProduct.getStatus());
                product.setProblem(updatedProduct.getProblem());

                productRepo.save(product);
                return true;
            }
        }
        return false;
    }

    // Delete a product
    public boolean deleteProduct(long productId) {  // Changed to primitive long
        ProductEntity productToDelete = products.stream()
                .filter(product -> product.getProductId() == productId)  // Use == for primitive long comparison
                .findFirst()
                .orElse(null);

        if (productToDelete != null) {
            products.remove(productToDelete);
            productRepo.delete(productToDelete);
            return true;
        }
        return false;
    }
}
