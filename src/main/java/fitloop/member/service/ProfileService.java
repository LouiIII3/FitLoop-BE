package fitloop.member.service;

import fitloop.member.auth.MemberIdentity;
import fitloop.member.dto.response.AccountProfileResponse;
import fitloop.member.entity.ProfileEntity;
import fitloop.member.entity.UserEntity;
import fitloop.member.repository.ProfileRepository;
import fitloop.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}