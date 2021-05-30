package com.example.scheduler.batch.job.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SampleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job sampleJob() {
        return jobBuilderFactory.get("sampleJob")
                .start(sampleStep1(null))
                    .on("FAILED") // FAILED 일 경우
                    .to(sampleStep3()) // step3으로 이동
                    .on("*") // step3의 결과 관계 없이
                    .end() // step3으로 이동하면 Flow 종료
                .from(sampleStep1(null))    // Step1로 부터
                    .on("*") //FAILED 외에 모든 경우
                    .to(sampleStep2()) // step2로 이동
                    .next(sampleStep3()) //step2가 정상 종료되면 step3으로 이동
                    .on("*") // step3의 결과 관계 없이
                    .end() //step3으로 이동하면 Flow가 종료
                .end() // Job 종료
                .build();
    }

    @Bean
    @JobScope
    public Step sampleStep1(@Value("#{jobParameters[requestDate]}")String requestDate) {
        return stepBuilderFactory.get("sampleStep1")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>> Sample step 1");
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step sampleStep2() {
        return stepBuilderFactory.get("sampleStep2")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>> Sample Step 2");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step sampleStep3() {
        return stepBuilderFactory.get("sampleStep3")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>> Sample Step 3");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
