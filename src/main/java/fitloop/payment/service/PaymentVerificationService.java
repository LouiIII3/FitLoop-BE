package fitloop.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PaymentVerificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    @Value("${iamport.base-url}")
    private String baseUrl;

    @Value("${iamport.api.token-path}")
    private String tokenPath;

    @Value("${iamport.api.payments-path}")
    private String paymentsPath;

    public Long verifyPayment(String impUid) throws Exception {

        String tokenUrl = baseUrl + tokenPath;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String tokenBody = String.format("{\"imp_key\":\"%s\",\"imp_secret\":\"%s\"}", apiKey, apiSecret);
        HttpEntity<String> tokenRequest = new HttpEntity<>(tokenBody, headers);

        ResponseEntity<String> tokenResponse = restTemplate.postForEntity(tokenUrl, tokenRequest, String.class);
        log.info("토큰 응답 = {}", tokenResponse.getBody());

        JsonNode tokenJson = objectMapper.readTree(tokenResponse.getBody());
        String accessToken = tokenJson.path("response").path("access_token").asText();

        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalStateException("포트원 AccessToken 발급 실패");
        }

        String paymentUrl = baseUrl + paymentsPath + "/" + impUid;
        HttpHeaders paymentHeaders = new HttpHeaders();
        paymentHeaders.set("Authorization", accessToken);

        HttpEntity<Void> paymentRequest = new HttpEntity<>(paymentHeaders);
        ResponseEntity<String> paymentResponse = restTemplate.exchange(
                paymentUrl, HttpMethod.GET, paymentRequest, String.class);

        log.info("결제 조회 응답 = {}", paymentResponse.getBody());

        JsonNode paymentJson = objectMapper.readTree(paymentResponse.getBody());
        JsonNode response = paymentJson.path("response");

        if (response.isMissingNode()) {
            throw new IllegalArgumentException("결제 검증 실패: response가 비어있음");
        }

        String status = response.path("status").asText();
        if (!"paid".equals(status)) {
            throw new IllegalArgumentException("결제 검증 실패: 결제 상태 = " + status);
        }

        long amount = response.path("amount").asLong();
        log.info("결제 검증 성공: impUid = {}, 금액 = {}", impUid, amount);

        return amount;
    }
}
