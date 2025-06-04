package fitloop.batch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job userLikeJob;
    private final Job productLikeCountJob;

    @PostMapping("/user-likes")
    public String runUserLikeJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("runAt", new Date())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(userLikeJob, jobParameters);

            return "배치 실행됨 (상태: " + execution.getStatus() + ")";
        } catch (Exception e) {
            e.printStackTrace();
            return "실패: " + e.getMessage();
        }
    }

    @PostMapping("/product-like-count")
    public String runProductLikeCountJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(productLikeCountJob, params);
            return "상품별 좋아요 수 업데이트 실행됨";
        } catch (Exception e) {
            return "실패: " + e.getMessage();
        }
    }

}
