package fitloop.member.controller;

import fitloop.member.dto.request.EmailRequest;
import fitloop.member.dto.request.VerifyRequest;
import fitloop.member.service.AuthService;
import fitloop.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmailAuthController {
    private final EmailService emailService;
    private final AuthService authService;

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestBody EmailRequest req) {
        emailService.sendAuthEmail(req.getEmail());
        return ResponseEntity.ok("인증번호 발송 완료");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verify(@RequestBody VerifyRequest req) {
        if (authService.verifyCode(req.getEmail(), req.getCode())) {
            return ResponseEntity.ok("인증 성공");
        }
        return ResponseEntity.badRequest().body("인증번호 불일치 또는 만료");
    }
}
