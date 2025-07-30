package fitloop.member.controller;

import fitloop.member.auth.Login;
import fitloop.member.auth.MemberIdentity;
import fitloop.member.auth.VerifiedMember;
import fitloop.member.dto.response.AccountProfileResponse;
import fitloop.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SettingController {
    private final ProfileService profileService;

    @Login
    @GetMapping("/settings/account/personal")
    public ResponseEntity<AccountProfileResponse> getProfile(@VerifiedMember MemberIdentity member) {
        return ResponseEntity.ok(profileService.getProfile(member));
    }
}