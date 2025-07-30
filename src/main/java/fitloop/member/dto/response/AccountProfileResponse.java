package fitloop.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AccountProfileResponse {
    private final String phoneNumber;
    private final String email;
    private final String accountNumber;
    private final String detailAccountNumber;
    private final String postalCode;
    private final LocalDate birthday;
    private final String address;

    @Builder
    public AccountProfileResponse(String phoneNumber, String email, String accountNumber, String detailAccountNumber,
                                  String postalCode, LocalDate birthday, String address) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accountNumber = accountNumber;
        this.detailAccountNumber = detailAccountNumber;
        this.postalCode = postalCode;
        this.birthday = birthday;
        this.address = address;
    }
}