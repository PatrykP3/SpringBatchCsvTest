package org.nowpat.batchtest.job;

import org.nowpat.batchtest.model.Person;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class PersonItemWriter implements ItemWriter<Person> {

    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception {
        for (Person person : chunk) {
            System.out.println(person);
        }
    }
}
