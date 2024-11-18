package org.nowpat.batchtest.conversion;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import org.nowpat.batchtest.model.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
@AllArgsConstructor
public class PersonFieldSetMapper implements FieldSetMapper<Person> {

    StringToLocalDateConverterDayFirst stringToLocalDateConverterDayFirst;

    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        return Person.builder()
                .id(fieldSet.readInt("id"))
                .name(fieldSet.readString(1))
                .dob(stringToLocalDateConverterDayFirst.convert(fieldSet.readString("dob")))
//                .dob(Instant.ofEpochMilli(fieldSet.readDate("dob", "dd.MM.yyyy").getTime()).atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
    }
}
