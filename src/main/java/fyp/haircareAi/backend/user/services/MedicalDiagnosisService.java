package fyp.haircareAi.backend.user.services;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MedicalDiagnosisService {

    public String AI(MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode json = mapper.createObjectNode();

            // Basic information
            json.put("identified_disease", "Male Pattern Baldness");
            json.put("medical_name", "Androgenetic Alopecia");
            json.put("description", "Male Pattern Baldness is a common type of hair loss in men that occurs in a well-defined pattern, typically starting with a receding hairline and thinning crown. It is a progressive condition and can eventually lead to partial or complete baldness.");

            // Causes array
            ArrayNode causes = mapper.createArrayNode();
            causes.add(createCause(mapper, "Genetics", "The most significant factor, inherited from either parent"));
            causes.add(createCause(mapper, "Hormonal Changes", "Increased sensitivity to dihydrotestosterone (DHT), a byproduct of testosterone"));
            causes.add(createCause(mapper, "Age", "Most men experience some hair loss as they age"));
            causes.add(createCause(mapper, "Health Conditions", "Thyroid issues, nutritional deficiencies, or certain medications"));
            causes.add(createCause(mapper, "Lifestyle Factors", "Poor diet, stress, smoking, and lack of sleep can contribute"));
            json.set("causes", causes);

            // Symptoms array
            ArrayNode symptoms = mapper.createArrayNode();
            symptoms.add("Receding hairline");
            symptoms.add("Thinning on the crown");
            symptoms.add("Gradual hair loss forming an 'M' shape");
            symptoms.add("Excessive hair shedding");
            json.set("symptoms", symptoms);

            // Solutions array
            ArrayNode solutions = mapper.createArrayNode();
            solutions.add(createSolutionCategory(mapper, "Topical Treatments",
                    new String[]{"Minoxidil 5%"},
                    new String[]{"FDA-approved solution or foam applied to the scalp to slow hair loss and promote growth"}));
            solutions.add(createSolutionCategory(mapper, "Oral Medications",
                    new String[]{"Finasteride (Propecia)"},
                    new String[]{"Reduces DHT levels to prevent hair follicle shrinkage. Should be taken under medical supervision"}));
            solutions.add(createSolutionCategory(mapper, "Low-Level Laser Therapy (LLLT)",
                    new String[]{"LLLT"},
                    new String[]{"Stimulates hair follicles and increases blood flow to the scalp"}));
            solutions.add(createSolutionCategory(mapper, "Platelet-Rich Plasma (PRP) Therapy",
                    new String[]{"PRP Therapy"},
                    new String[]{"Involves injecting your own platelet-rich plasma into the scalp to rejuvenate follicles"}));
            solutions.add(createSolutionCategory(mapper, "Hair Transplant Surgery",
                    new String[]{"FUE or FUT procedures"},
                    new String[]{"Can restore hair by transplanting follicles from donor areas"}));
            solutions.add(createSolutionCategory(mapper, "Hair Growth Supplements",
                    new String[]{"Nutritional Supplements"},
                    new String[]{"Biotin, Zinc, Vitamin D, Iron, and Saw Palmetto can support overall hair health"}));
            solutions.add(createSolutionCategory(mapper, "Shampoo & Scalp Care",
                    new String[]{"DHT-blocking shampoos"},
                    new String[]{"Use DHT-blocking shampoos and avoid harsh chemicals or hot water"}));
            json.set("solutions", solutions);

            // Recommendations array
            ArrayNode recommendations = mapper.createArrayNode();
            recommendations.add("Start treatment early to preserve hair density");
            recommendations.add("Maintain a balanced diet rich in proteins and vitamins");
            recommendations.add("Avoid stress and manage it through exercise or meditation");
            recommendations.add("Sleep 7–8 hours daily");
            recommendations.add("Consult a dermatologist or trichologist for personalized advice");
            json.set("recommendations", recommendations);

            // Important note
            json.put("important_note", "There is no permanent cure, but early intervention can significantly slow down hair loss and promote regrowth in many cases.");

            return mapper.writeValueAsString(json);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to generate diagnosis\"}";
        }
    }

    private ObjectNode createCause(ObjectMapper mapper, String factor, String description) {
        ObjectNode cause = mapper.createObjectNode();
        cause.put("factor", factor);
        cause.put("description", description);
        return cause;
    }

    private ObjectNode createSolutionCategory(ObjectMapper mapper, String category, String[] names, String[] descriptions) {
        ObjectNode solution = mapper.createObjectNode();
        solution.put("category", category);

        ArrayNode treatments = mapper.createArrayNode();
        for (int i = 0; i < names.length; i++) {
            ObjectNode treatment = mapper.createObjectNode();
            treatment.put("name", names[i]);
            treatment.put("description", descriptions[i]);
            treatments.add(treatment);
        }
        solution.set("treatments", treatments);

        return solution;
    }
}
