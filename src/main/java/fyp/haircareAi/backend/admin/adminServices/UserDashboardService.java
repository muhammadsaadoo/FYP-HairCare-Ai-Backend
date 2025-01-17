package fyp.haircareAi.backend.admin.adminServices;

import fyp.haircareAi.backend.admin.cache.UserCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserDashboardService {
    @Autowired
    private UserCache userCache;

    public Map<String, Object> getUserDashboardData(){
        Map<String, Object> userData=new HashMap<>();
        userData.put("totalUsers", userCache.getTotalUsers());
        userData.put("activeUsers", userCache.getActiveUsers());
        userData.put("premiumUsers", userCache.getPremiumUsers());
        userData.put("regularUsers", userCache.getRegularUsers());
        userData.put("usersByWeek", userCache.getUsersRegisteredPerWeek());
        userData.put("newUsersLast24Hours", userCache.getNewUsersLast24Hours());
        userData.put("usersByCountry", userCache.getUsersByCountry());
        userData.put("userActivityLast24Hours", userCache.getUserActivityLast24Hours());

        return userData;

    }
}
