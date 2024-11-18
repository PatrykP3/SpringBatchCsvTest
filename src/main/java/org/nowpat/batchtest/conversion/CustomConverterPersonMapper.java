package org.nowpat.batchtest.conversion;

import org.nowpat.batchtest.model.Person;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

@Component
public class CustomConverterPersonMapper extends BeanWrapperFieldSetMapper<Person> {

    public CustomConverterPersonMapper(StringToLocalDateConverterDayFirst stringToLocalDateConverterDayFirst) {
        super();
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(stringToLocalDateConverterDayFirst);
        this.setConversionService(conversionService);
        this.setTargetType(Person.class);
    }
}
