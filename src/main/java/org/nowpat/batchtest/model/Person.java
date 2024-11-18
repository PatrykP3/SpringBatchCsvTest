package org.nowpat.batchtest.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    Integer id;
    String name;

//    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    LocalDate dob;
}
