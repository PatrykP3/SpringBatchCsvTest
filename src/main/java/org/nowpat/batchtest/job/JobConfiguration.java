package org.nowpat.batchtest.job;

import java.time.Instant;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import org.nowpat.batchtest.conversion.StringToLocalDateConverterDMY;
import org.nowpat.batchtest.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class JobConfiguration {

    private final JobRepository jobRepository;

    @Bean
    public Job importBarrierInformationJob(Step importPersonStep) {

        return new JobBuilder("test job", jobRepository)
                .start(importPersonStep)
                .build();
    }

    @Bean
    public Step importPersonStep(PlatformTransactionManager transactionManager,
                                 FlatFileItemReader<Person> flatFileItemReader,
                                 ItemWriter<Person> personItemWriter) {
        return new StepBuilder("importPersonStep", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(flatFileItemReader)
                .writer(personItemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<Person> flatFilePersonReader(@Value("${inputFile}") Resource inputFile,
                                                           StringToLocalDateConverterDMY stringToLocalDateConverterDMY) {
        return new FlatFileItemReaderBuilder<Person>()
                .name("flatFilePersonReader")
                .resource(inputFile)
                .delimited()
                .names("id", "name", "dob")
                .fieldSetMapper(fieldSet -> Person.builder()
                        .id(fieldSet.readInt("id"))
                        .name(fieldSet.readString(1))
                        .dob(stringToLocalDateConverterDMY.convert(fieldSet.readString("dob")))
                        .build())
                .build();
    }
}
