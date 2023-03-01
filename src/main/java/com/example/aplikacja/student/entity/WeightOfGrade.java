package com.example.aplikacja.student.entity;


import com.example.aplikacja.student.dto.WeightOfGradeDTO;
import com.example.aplikacja.student.enums.Subject;
import lombok.*;

import javax.persistence.*;
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
public class WeightOfGrade {

    @SequenceGenerator(
            name = "sequenceWeightOfGrade",
            sequenceName = "sequenceWeightOfGrade",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceWeightOfGrade"
    )

    @Column
    private Long id;
    @ManyToOne
    private Klasa klasa;
    @Enumerated(EnumType.STRING)
    private Subject subject;
    private Double wartosc = 1.0;

    public WeightOfGrade(Klasa klasa, Subject subject, Double wartosc) {
        this.klasa = klasa;
        this.subject = subject;
        this.wartosc = wartosc;
    }

    public WeightOfGrade(Subject subject, Klasa klasa1) {
        this.subject = subject;
        this.klasa = klasa1;
    }

    @Override
    public String toString() {
        return "WeightOfGrade{" +
                "id=" + id +
                ", klasa=" + klasa +
                ", subject=" + subject +
                ", wartosc=" + wartosc +
                '}';
    }
}
