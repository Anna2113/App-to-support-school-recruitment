package com.example.aplikacja.student.entity;

import lombok.*;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
public class ClassStudentResult {

    @SequenceGenerator(
            name = "sequenceClassStudentResult",
            sequenceName = "sequenceClassStudentResult",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceClassStudentResult"
    )

    private Long id;
    @ManyToOne
    private Klasa klasa;
    @ManyToOne
    private Student student;
    private Double pointsResult;

    public ClassStudentResult(Klasa klasa, Student student, AtomicReference<Double> pointsResult) {
        this.klasa = klasa;
        this.student = student;
        this.pointsResult = pointsResult.get();
    }

    @Override
    public String toString() {
        return "ClassStudentResult{" +
                "id=" + id +
                ", klasa=" + klasa +
                ", student=" + student +
                ", pointsResult=" + pointsResult +
                '}';
    }
}
