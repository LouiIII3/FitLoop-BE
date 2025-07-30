package fitloop.member.service;

import fitloop.member.dto.request.AccountNumberRequest;
import fitloop.member.dto.request.CustomUserDetails;
import fitloop.member.entity.ProfileEntity;
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

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

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
