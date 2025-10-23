package fitloop.member.controller;

import fitloop.member.dto.request.ProfileRequest;
import fitloop.member.dto.response.ProfileResponse;
import fitloop.member.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Object principal) {
        return userService.getUserInfo(principal);
    }

    @PostMapping("/users/profile")
    public ResponseEntity<?> createProfile(
            @RequestBody @Valid ProfileRequest profileRequest,
            @AuthenticationPrincipal Object principal,
            @RequestHeader("access") String accessToken) {
        return userService.createProfile(profileRequest, principal, accessToken);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteMyAccount(@AuthenticationPrincipal Object principal,
                                             HttpServletResponse response) {
        return userService.deleteAccount(principal, response);
    }
}