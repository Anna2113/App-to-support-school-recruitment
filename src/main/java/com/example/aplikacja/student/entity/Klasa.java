package com.example.aplikacja.student.entity;


import com.example.aplikacja.student.enums.NameOfClass;
import com.example.aplikacja.student.enums.Skills;
import com.example.aplikacja.student.enums.Subject;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
public class Klasa {

    @SequenceGenerator(
            name = "sequenceClass",
            sequenceName = "sequenceClass",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceClass"
    )

    @Column
    private Long id;
    @Enumerated(EnumType.STRING)
    private NameOfClass nameOfClass;
    private String symbol;
    private String liczba;
    private Boolean locked = false;
    private Boolean enabled = true;
    private Double minAvgGrade;
    private Double numberOfPointsForOlimp;
    private Double numberOfPointsForFinalist;
    private Double minAmountOfPointsFromExams;
    @OneToMany(mappedBy = "klasa", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<WeightOfGrade> weightOfGrade;
    @Enumerated
    @ElementCollection(targetClass = Skills.class)
    private List<Skills> umiejetnosci;
    @Enumerated
    @ElementCollection(targetClass = Skills.class)
    private List<Subject> przedmioty;
    @OneToOne
    private Student student;
    private String first;
    private String second;
    private String third;



    public Klasa(NameOfClass nameOfClass, String symbol, String liczba, Boolean locked, Boolean enabled, Double minAvgGrade, Double numberOfPointsForOlimp, Double numberOfPointsForFinalist, Double minAmountOfPointsFromExams, List<WeightOfGrade> weightOfGrade, List<Skills> umiejetnosci) {
        this.nameOfClass = nameOfClass;
        this.symbol = symbol;
        this.liczba = liczba;
        this.locked = locked;
        this.enabled = enabled;
        this.minAvgGrade = minAvgGrade;
        this.numberOfPointsForOlimp = numberOfPointsForOlimp;
        this.numberOfPointsForFinalist = numberOfPointsForFinalist;
        this.minAmountOfPointsFromExams = minAmountOfPointsFromExams;
        this.weightOfGrade = weightOfGrade;
        this.umiejetnosci = umiejetnosci;
    }

    @Override
    public String toString() {
        return "Klasa{" +
                "id=" + id +
                ", nameOfClass=" + nameOfClass +
                ", symbol='" + symbol + '\'' +
                ", liczba='" + liczba + '\'' +
                ", locked=" + locked +
                ", enabled=" + enabled +
                ", minAvgGrade=" + minAvgGrade +
                ", numberOfPointsForOlimp=" + numberOfPointsForOlimp +
                ", numberOfPointsForFinalist=" + numberOfPointsForFinalist +
                ", minAmountOfPointsFromExams=" + minAmountOfPointsFromExams +
                ", weightOfGrade=" + weightOfGrade +
                ", umiejetnosci=" + umiejetnosci +
                '}';
    }

}
