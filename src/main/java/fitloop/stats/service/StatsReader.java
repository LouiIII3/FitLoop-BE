package fitloop.stats.service;

import fitloop.stats.dto.StatsOverviewDto;
import fitloop.stats.entity.UserStats;
import fitloop.stats.repository.UserStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsReader {

    private final UserStatsRepository repo;

    public StatsOverviewDto getOverview(Long memberId) {
        return repo.findById(memberId)
                .map(StatsOverviewDto::from)
                .orElseGet(() -> StatsOverviewDto.zero(memberId));
    }

    public UserStats getOrNull(Long memberId) {
        return repo.findById(memberId).orElse(null);
    }
}
