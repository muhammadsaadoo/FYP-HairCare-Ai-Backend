package fyp.haircareAi.backend.admin.cache;

import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Getter;
import java.time.temporal.IsoFields;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.*;

@Component
public class UserCache {

    @Autowired
    private AuthRepo signUpRepo;

    @Getter
    private List<UserEntity> users;

    @PostConstruct
    public void getAllUsers() {
        users = signUpRepo.findAll();
    }

    // Fetch total users
    public long getTotalUsers() {
        return users.size();
    }

    // Fetch active users
    public long getActiveUsers() {
        return users.stream()
                .filter(user -> "Active".equalsIgnoreCase(String.valueOf(user.getStatus())))
                .count();
    }

    // Fetch premium users
    public long getPremiumUsers() {
        return users.stream()
                .filter(user -> "Premium".equalsIgnoreCase(String.valueOf(user.getUsertype())))
                .count();
    }

    // Fetch regular users
    public long getRegularUsers() {
        return users.stream()
                .filter(user -> "Regular".equalsIgnoreCase(String.valueOf(user.getUsertype())))
                .count();
    }

    // Fetch users registered per week (bar chart)
    public Map<Integer, Long> getUsersRegisteredPerWeek() {
        return users.stream()
                .filter(user -> user.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        user -> user.getCreatedAt().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR),
                        Collectors.counting()
                ));
    }

    // Fetch new users registered in the last 24 hours
    public long getNewUsersLast24Hours() {
        LocalDateTime now = LocalDateTime.now();
        return users.stream()
                .filter(user -> user.getCreatedAt() != null &&
                        user.getCreatedAt().isAfter(now.minusHours(24)))
                .count();
    }

    // Get user distribution by country (pie chart)
    public Map<String, Long> getUsersByCountry() {
        return users.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getCountry() == null ? "Others" : user.getCountry(),
                        Collectors.counting()
                ));
    }

    // Get user activity in the last 24 hours (graph)
    public Map<LocalDateTime, Long> getUserActivityLast24Hours() {
        LocalDateTime now = LocalDateTime.now();
        return users.stream()
                .filter(user -> user.getLastLogin() != null &&
                        user.getLastLogin().isAfter(now.minusHours(24)))
                .collect(Collectors.groupingBy(
                        UserEntity::getLastLogin,
                        Collectors.counting()
                ));
    }
}
