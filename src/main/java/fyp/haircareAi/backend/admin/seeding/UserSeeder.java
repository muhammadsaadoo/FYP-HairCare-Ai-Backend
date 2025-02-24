package fyp.haircareAi.backend.admin.seeding;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/seeding")
public class UserSeeder {

    @Autowired
    private AuthRepo userRepository;

    private static final String[] FIRST_NAMES = {"John", "Emma", "Michael", "Sophia", "David", "Olivia"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Williams", "Jones", "Garcia"};
    private static final String[] COUNTRIES = {"USA", "UK", "Canada", "Germany", "Australia", "France"};
    private static final String PASSWORD = "$2a$10$abcdefghijklmnopqrstuv"; // Sample hashed password
    private static final String[] GENDERS = {"Male", "Female"};
    private static final Random RANDOM = new Random();

    @GetMapping
    public void seedUsers() {
        System.out.println("Seeding users...");

        LocalDateTime startDate = LocalDateTime.now().minusMonths(1); // Start from previous month
        int usersPerDay = 2000 / 30; // Approx. 67 users per day

        for (int day = 0; day < 30; day++) {
            LocalDateTime currentDate = startDate.plusDays(day);

            for (int i = 0; i < usersPerDay; i++) {
                UserEntity user = new UserEntity();
                user.setFirst_name(FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)]);
                user.setLast_name(LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)]);
                user.setEmail("seed_user" + (day * usersPerDay + i) + UUID.randomUUID().toString().substring(0, 5) + "@example.com");
                user.setPassword(PASSWORD);
                user.setGender(GENDERS[RANDOM.nextInt(GENDERS.length)]);
                user.setAge(RANDOM.nextInt(40) + 18);
                user.setCountry(COUNTRIES[RANDOM.nextInt(COUNTRIES.length)]);
                user.setRole(UserEntity.Role.USER);
                user.setVerify(false);
                user.setStatus(UserEntity.Status.InActive);
                user.setUsertype((day * usersPerDay + i) % 2 == 0 ? UserEntity.UserType.Premium : UserEntity.UserType.Regular);

                // Distribute users within the day (random hour/minute)
                LocalDateTime userCreationTime = currentDate.plusHours(RANDOM.nextInt(24)).plusMinutes(RANDOM.nextInt(60));
                user.setCreatedAt(userCreationTime);

                userRepository.save(user);
            }
        }

        System.out.println("User seeding completed.");
    }

}

