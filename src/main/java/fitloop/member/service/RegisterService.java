package fitloop.member.service;

import fitloop.member.dto.request.RegisterRequest;
import fitloop.member.entity.UserEntity;
import fitloop.member.repository.UserRepository;
import fitloop.payment.entity.Wallet;
import fitloop.payment.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterService(UserRepository userRepository,
                           WalletRepository walletRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public UserEntity registerProcess(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();
        String name = registerRequest.getName();

        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .birthday(registerRequest.getBirthday())
                .name(name)
                .build();

        UserEntity savedUser = userRepository.save(user);

        Wallet wallet = Wallet.builder()
                .userId(savedUser.getId())
                .balance(0L)
                .build();
        walletRepository.save(wallet);

        return savedUser;
    }
}