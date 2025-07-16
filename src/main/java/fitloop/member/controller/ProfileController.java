package fitloop.member.controller;

import fitloop.member.dto.request.AccountNumberRequest;
import fitloop.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/account")
    public ResponseEntity<?> saveAccountNumber(
            @RequestBody AccountNumberRequest request,
            @AuthenticationPrincipal Object principal
    ) {
        return profileService.saveAccountNumber(request, principal);
    }
}
