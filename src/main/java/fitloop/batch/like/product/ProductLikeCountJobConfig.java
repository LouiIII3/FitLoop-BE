package fitloop.batch.like.product;

import fitloop.product.entity.ProductEntity;
import fitloop.product.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ProductLikeCountJobConfig {

    private final StringRedisTemplate redisTemplate;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    @Bean
    public Job productLikeCountJob(JobRepository jobRepository, Step productLikeCountStep) {
        return new JobBuilder("productLikeCountJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(productLikeCountStep)
                .build();
    }

    @Bean
    public Step productLikeCountStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     ItemReader<Map.Entry<Long, Long>> productLikeCountReader) {
        return new StepBuilder("productLikeCountStep", jobRepository)
                .<Map.Entry<Long, Long>, ProductEntity>chunk(100, transactionManager)
                .reader(productLikeCountReader)
                .processor(new ProductLikeCountProcessor(productRepository))
                .writer(new ProductLikeCountWriter(entityManager))
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Map.Entry<Long, Long>> productLikeCountReader() {
        return new RedisProductLikeCountReader(redisTemplate);
    }
}
