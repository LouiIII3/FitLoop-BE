package fitloop.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job userLikeJob;
    private final Job productLikeCountJob;

    @Scheduled(cron = "0 56 21 * * *", zone = "Asia/Seoul")
    public void runBatchJobs() {
        try {
            // 유저별 좋아요 집계 작업 실행
            JobParameters userLikeParams = new JobParametersBuilder()
                    .addDate("runAt", new Date())
                    .toJobParameters();

            log.info("[배치 스케줄러] 유저별 좋아요 집계 작업 시작");
            jobLauncher.run(userLikeJob, userLikeParams);
            log.info("[배치 스케줄러] 유저별 좋아요 집계 작업 완료");

            // 상품별 좋아요 수 집계 작업 실행
            JobParameters productLikeParams = new JobParametersBuilder()
                    .addDate("runAt", new Date())
                    .toJobParameters();

            log.info("[배치 스케줄러] 상품별 좋아요 수 집계 작업 시작");
            jobLauncher.run(productLikeCountJob, productLikeParams);
            log.info("[배치 스케줄러] 상품별 좋아요 수 집계 작업 완료");

        } catch (Exception e) {
            log.error("[배치 스케줄러] 배치 작업 실행 중 오류 발생", e);
        }
    }
}
