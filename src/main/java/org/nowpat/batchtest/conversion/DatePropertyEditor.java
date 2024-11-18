package org.nowpat.batchtest.conversion;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.batch.item.ParseException;
import org.springframework.stereotype.Component;

@Component
public class DatePropertyEditor extends PropertyEditorSupport {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(LocalDate.parse(text, FORMATTER));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Could not parse date", e);
        }
    }

    @Override
    public String getAsText() {
        return FORMATTER.format((LocalDate) getValue());
    }
}