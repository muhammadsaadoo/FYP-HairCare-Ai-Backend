package fyp.haircareAi.backend.dto;

import lombok.Data;

@Data
public class HairAnalysisRequest {
    private Long userId;
    private Long problemId;
    private String recommendedProduct;
    private String imagePath;
}

