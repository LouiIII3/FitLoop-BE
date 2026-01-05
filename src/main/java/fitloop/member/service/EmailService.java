package fitloop.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private static final long EXPIRE_MIN = 5;

    public void sendAuthEmail(String toEmail) {
        String code = createCode();
        redisTemplate.opsForValue().set("email:" + toEmail, code, Duration.ofMinutes(EXPIRE_MIN));
        try {
            sendHtmlEmail(toEmail, code);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

    private String createCode() {
        int num = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(num);
    }

    private void sendHtmlEmail(String toEmail, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("[fitloop] 이메일 인증번호 안내");

        String html = String.format("""
                <div style="font-family: 'Arial', sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #eee; padding: 30px; background-color: #ffffff;">
                  <h2 style="font-size: 20px; line-height: 1.4;">
                    <span style="color: #1a73e8;">[FITLOOP] </span><span style="color: #000000;">사이트의<span><br />
                    </span><span style="color: #000000;">이메일 인증번호를 안내해 드립니다.</span>
                  </h2>
                  <p style="font-size: 15px; color: #333;">
                    회원가입을 위해서는 본인확인을 위한 이메일 인증번호 확인이 필요합니다.<br />
                    아래의 인증번호를 복사하셔서 이메일 인증번호 입력란에 입력해주시기 바랍니다.<br />
                    인증번호는 발송된 시점부터 <strong style="color: #e53935;">%d분</strong>간만 유효하니<br />
                    확인 후 바로 입력해주시기 바랍니다.
                  </p>

                  <div style="margin: 30px 0; text-align: center;">
                    <div style="font-size: 36px; letter-spacing: 12px; font-weight: bold; color: #1a73e8; background-color: #f1f6ff; padding: 20px 0; border-radius: 10px;">
                      %s
                    </div>
                  </div>

                  <div style="text-align: center; margin-bottom: 20px;">
                    <a href="https://fitloop.co.kr" style="display: inline-block; background-color: #1a73e8; color: #fff; text-decoration: none; padding: 12px 24px; border-radius: 6px; font-weight: bold;">
                      사이트 이동하기
                    </a>
                  </div>

                  <p style="font-size: 12px; color: #999; border-top: 1px solid #eee; padding-top: 15px;">
                    본 메일은 발신전용입니다. 본 이메일로 회신하실 경우 답변이 되지 않으며,<br />
                    서비스 이용 안내메일로 수신동의 여부와 관계없이 발송되었습니다.
                  </p>

                  <p style="text-align: center; font-size: 12px; margin-top: 10px;">
                    <a href="https://fitloop.co.kr" style="color: #666; text-decoration: underline;">사이트 바로가기</a>
                  </p>
                </div>
                """, EXPIRE_MIN, code);

        helper.setText(html, true);
        mailSender.send(message);
    }
}