package fitloop.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean verifyCode(String email, String input) {
        String key = "email:" + email;
        String stored = redisTemplate.opsForValue().get(key);
        if (stored != null && stored.equals(input)) {
            redisTemplate.opsForValue().set(key + ":ok", "true", Duration.ofMinutes(5));
            return true;
        }
        return false;
    }

    public boolean isVerified(String email) {
        return "true".equals(redisTemplate.opsForValue().get("email:" + email + ":ok"));
    }
}