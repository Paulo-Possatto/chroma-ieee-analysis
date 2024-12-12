package com.chromamon.analysis.ieee.config;

import com.chromamon.analysis.ieee.model.AnalysisData;
import com.chromamon.analysis.ieee.model.DiagnosticData;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
@EnableBatchProcessing
public class IEEEBatchConfig {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job ieeeAnalysisJob(Step rogersAnalysisStep) {
        return new JobBuilder("rogersAnalysisJob", jobRepository)
                .start(rogersAnalysisStep)
                .build();
    }

    @Bean
    public Step ieeeAnalysisStep(ItemReader<AnalysisData> reader,
                                 ItemProcessor<AnalysisData, DiagnosticData> processor,
                                 ItemWriter<DiagnosticData> writer) {
        return new StepBuilder("ieeeAnalysisStep", jobRepository)
                .<AnalysisData, DiagnosticData>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}