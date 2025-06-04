package fitloop.member.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import fitloop.member.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Map;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository, RedisTemplate<String, String> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 로그아웃 API가 아니면 그냥 넘김
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/api/v1/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Refresh 토큰 가져오기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }

        if (refresh != null) {
            try {
                if (!jwtUtil.isExpired(refresh)) {
                    String category = jwtUtil.getCategory(refresh);
                    if ("refresh".equals(category)) {
                        Boolean isExist = refreshRepository.existsByRefresh(refresh);
                        if (isExist) {
                            refreshRepository.deleteByRefresh(refresh);
                        }
                    }
                }
            } catch (JwtException ignored) {
            }
        }

        // Access 토큰 Redis에서 삭제
        String accessToken = request.getHeader("access");
        if (accessToken != null) {
            try {
                String username = jwtUtil.getUsername(accessToken);
                String redisKey = "auth:access:" + username;
                String redisValue = redisTemplate.opsForValue().get(redisKey);

                if (redisValue != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, String> tokenInfo = objectMapper.readValue(redisValue, Map.class);

                    String storedToken = tokenInfo.get("token");
                    if (accessToken.equals(storedToken)) {
                        redisTemplate.delete(redisKey);
                    }
                }
            } catch (JwtException ignored) {
            }
        }

        // Refresh 토큰 쿠키 제거
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
