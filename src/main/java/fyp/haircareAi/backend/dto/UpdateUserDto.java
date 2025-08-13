package fyp.haircareAi.backend.dto;


import lombok.Data;

@Data
public class UpdateUserDto {
    private String first_name;
    private String last_name;
    private String gender;
    private int age;
    private String country;
}
