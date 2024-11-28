package org.nowpat.batchtest.conversion;

import lombok.AllArgsConstructor;
import org.nowpat.batchtest.model.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonFieldSetMapper implements FieldSetMapper<Person> {

    StringToLocalDateConverterDMY stringToLocalDateConverterDMY;

    @Override
    public Person mapFieldSet(FieldSet fieldSet) {
        return Person.builder()
                .id(fieldSet.readInt("id"))
                .name(fieldSet.readString(1))
                .dob(stringToLocalDateConverterDMY.convert(fieldSet.readString("dob")))
                .build();
    }
}
