package fyp.haircareAi.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class EmailVerificationRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String verificationCode;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}

