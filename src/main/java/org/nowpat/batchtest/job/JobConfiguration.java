package org.nowpat.batchtest.job;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.nowpat.batchtest.conversion.CustomConverterPersonMapper;
import org.nowpat.batchtest.conversion.DatePropertyEditor;
import org.nowpat.batchtest.conversion.PersonFieldSetMapper;
import org.nowpat.batchtest.conversion.StringToLocalDateConverterDayFirst;
import org.nowpat.batchtest.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
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
                                                           DatePropertyEditor datePropertyEditor,
                                                           PersonFieldSetMapper personFieldSetMapper,
                                                           CustomConverterPersonMapper customConverterPersonMapper) {
        return new FlatFileItemReaderBuilder<Person>()
                .name("flatFilePersonReader")
                .resource(inputFile)
                .delimited()
                .names("id", "name", "dob")

//                .customEditors(Map.of(LocalDate.class, datePropertyEditor))
//                .targetType(Person.class)

//                .fieldSetMapper(fieldSet -> Person.builder()
//                        .id(fieldSet.readInt("id"))
//                        .name(fieldSet.readString(1))
//                        .dob(Instant.ofEpochMilli(fieldSet.readDate("dob", "dd.MM.yyyy").getTime()).atZone(ZoneId.systemDefault()).toLocalDate())
//                        .build())

//                .fieldSetMapper(personFieldSetMapper)

                .fieldSetMapper(customConverterPersonMapper)

                .build();
    }
}
