package fitloop.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountNumberRequest {
    private String bankName;
    private String accountNumber;
    private String depositor;
}
