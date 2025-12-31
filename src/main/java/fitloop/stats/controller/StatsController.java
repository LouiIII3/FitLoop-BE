package fitloop.stats.controller;

import fitloop.member.auth.Login;
import fitloop.member.auth.MemberIdentity;
import fitloop.member.auth.VerifiedMember;
import fitloop.stats.dto.StatsOverviewDto;
import fitloop.stats.service.StatsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class StatsController {

    private final StatsReader statsReader;

    @Login
    @GetMapping("/overview")
    public ResponseEntity<StatsOverviewDto> getOverview(
            @VerifiedMember MemberIdentity member
    ) {
        StatsOverviewDto dto = statsReader.getOverview(member.id());
        return ResponseEntity.ok(dto);
    }
}
