package fitloop.batch.like.user;

import fitloop.like.entity.LikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class UserLikeJobConfig {

    private final StringRedisTemplate redisTemplate;

    @Bean
    public Job userLikeJob(JobRepository jobRepository, Step userLikeStep) {
        return new JobBuilder("userLikeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(userLikeStep)
                .build();
    }

    @Bean
    public Step userLikeStep(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager) {

        ItemReader<Map.Entry<Long, String>> reader = new RedisUserLikesReader(redisTemplate);
        ItemProcessor<Map.Entry<Long, String>, LikeEntity> processor = new UserLikeProcessor();
        ItemWriter<LikeEntity> writer = new UserLikeWriter();

        return new StepBuilder("userLikeStep", jobRepository)
                .<Map.Entry<Long, String>, LikeEntity>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
