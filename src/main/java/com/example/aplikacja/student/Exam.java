package com.example.aplikacja.student;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
public class Exam {

    @SequenceGenerator(
            name = "sequenceExam",
            sequenceName = "sequenceExam",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceExam"
    )

    @Id
    private Long id;
    private String languagePolishResult;
    private String math;
    private String foreignLanguage;
    @OneToOne
    private Student student;

    public Exam(Long id, String languagePolishResult, String math, String foreignLanguage) {
        this.id = id;
        this.languagePolishResult = languagePolishResult;
        this.math = math;
        this.foreignLanguage = foreignLanguage;
    }

    public Exam(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "polish=" + languagePolishResult +
                ", math=" + math +
                ", foreignLanguage=" + foreignLanguage +
                '}';
    }
}
