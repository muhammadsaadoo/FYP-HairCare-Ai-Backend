package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserPrimaryDetails")
@Data
@NoArgsConstructor
public class UserPrimaryDetailsEntity {
    //family history
    //        pashaircondition age
    //
    //                        gender
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long UserDetailsId;

    @Column
    private long userId;

    @Column
    private String family_history;

    @Column
    private String past_hair_condition;

    @Column
    private int age;

    @Column
    private String gender;


}
