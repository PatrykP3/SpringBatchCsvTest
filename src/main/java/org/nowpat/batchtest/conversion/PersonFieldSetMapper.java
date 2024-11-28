package org.nowpat.batchtest.conversion;

import java.time.Instant;
import java.time.ZoneId;
import org.nowpat.batchtest.model.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class PersonFieldSetMapper implements FieldSetMapper<Person> {

    @Override
    public Person mapFieldSet(FieldSet fieldSet) {
        return Person.builder()
                .id(fieldSet.readInt("id"))
                .name(fieldSet.readString(1))
                .dob(Instant.ofEpochMilli(fieldSet.readDate("dob", "dd.MM.yyyy").getTime()).atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
    }
}
