package fitloop.stats.service;

import fitloop.stats.entity.UserStats;
import fitloop.stats.repository.UserStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatsUpdater {

    private final UserStatsRepository repo;

    @Transactional
    public void ensureInit(Long memberId) {
        repo.findById(memberId).orElseGet(() -> repo.save(UserStats.zeroOf(memberId)));
    }

    @Transactional
    public void onPurchaseCreated(Long buyerId) {
        UserStats s = repo.findById(buyerId).orElseGet(() -> repo.save(UserStats.zeroOf(buyerId)));
        s.incPurchase();
    }

    @Transactional
    public void onSaleCreated(Long sellerId) {
        UserStats s = repo.findById(sellerId).orElseGet(() -> repo.save(UserStats.zeroOf(sellerId)));
        s.incSales();
    }

    @Transactional
    public void onReviewCreated(Long memberId) {
        UserStats s = repo.findById(memberId).orElseGet(() -> repo.save(UserStats.zeroOf(memberId)));
        s.incReview();
    }

    @Transactional
    public void addCoupons(Long memberId, long delta) {
        UserStats s = repo.findById(memberId).orElseGet(() -> repo.save(UserStats.zeroOf(memberId)));
        s.addCoupons(delta);
    }

    @Transactional
    public void addPoints(Long memberId, long delta) {
        UserStats s = repo.findById(memberId).orElseGet(() -> repo.save(UserStats.zeroOf(memberId)));
        s.addPoints(delta);
    }

    @Transactional
    public void addPointsAtomic(Long memberId, long delta, int maxRetry) {
        ensureInit(memberId);
        retry(maxRetry, () -> {
            int updated = repo.addPoints(memberId, delta);
            if (updated != 1) throw new OptimisticLockingFailureException("points update failed");
        });
    }

    @Transactional
    public void addCouponsAtomic(Long memberId, long delta, int maxRetry) {
        ensureInit(memberId);
        retry(maxRetry, () -> {
            int updated = repo.addCoupons(memberId, delta);
            if (updated != 1) throw new OptimisticLockingFailureException("coupons update failed");
        });
    }

    private void retry(int maxRetry, Runnable op) {
        int tries = 0;
        while (true) {
            try {
                op.run();
                return;
            } catch (OptimisticLockingFailureException e) {
                if (++tries > maxRetry) throw e;
            }
        }
    }
}
