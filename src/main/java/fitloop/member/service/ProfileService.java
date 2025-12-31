package fitloop.member.service;

import fitloop.member.auth.MemberIdentity;
import fitloop.member.dto.request.AccountNumberRequest;
import fitloop.member.dto.request.CustomUserDetails;
import fitloop.member.dto.response.AccountProfileResponse;
import fitloop.member.entity.ProfileEntity;
import fitloop.member.entity.UserEntity;
import fitloop.member.repository.ProfileRepository;
import fitloop.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public AccountProfileResponse getProfile(MemberIdentity member) {
        Long userId = member.id();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        ProfileEntity profile = profileRepository.findByUserId(user)
                .orElseThrow(() -> new IllegalArgumentException("프로필을 찾을 수 없습니다."));

        return AccountProfileResponse.builder()
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .accountNumber(profile.getAccountNumber())
                .detailAccountNumber(profile.getDetailAddress())
                .postalCode(profile.getPostalCode())
                .birthday(user.getBirthday())
                .address(profile.getAddress())
                .build();
    }

    public ResponseEntity<?> saveAccountNumber(AccountNumberRequest request, Object principal) {
        if (!(principal instanceof CustomUserDetails userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "인증이 필요합니다."));
        }

        Optional<ProfileEntity> profileOpt = userRepository.findByUsername(userDetails.getUsername())
                .flatMap(profileRepository::findByUserId);

        if (profileOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "사용자를 찾을 수 없습니다."));
        }

        ProfileEntity profile = profileOpt.get();

        profile.setBankName(request.getBankName());
        profile.setAccountNumber(request.getAccountNumber());
        profile.setDepositor(request.getDepositor());

        profileRepository.save(profile);

        return ResponseEntity.ok(Map.of("message", "계좌번호가 저장되었습니다."));
    }
}