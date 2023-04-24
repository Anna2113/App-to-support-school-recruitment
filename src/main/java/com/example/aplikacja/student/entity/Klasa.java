package com.example.aplikacja.student.entity;


import com.example.aplikacja.student.enums.NameOfClass;
import com.example.aplikacja.student.enums.Skills;
import com.example.aplikacja.student.enums.Subject;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@ToString
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
    @Column(columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy")
    private Date year;
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
    private String weightExamMath;
    private String weightExamPolish;
    private String weightExamEnglish;


    public Klasa(NameOfClass nameOfClass, String symbol, String liczba, Date year,
                 Boolean locked,
                 Boolean enabled, Double minAvgGrade, Double numberOfPointsForOlimp,
                 Double numberOfPointsForFinalist, Double minAmountOfPointsFromExams,
                 List<WeightOfGrade> weightOfGrade, List<Skills> umiejetnosci, List<Subject> przedmioty,
                 List<Student> listOfStudent, Student student, String first, String second, String third, String weightExamMath,
                 String weightExamPolish, String weightExamEnglish) {
        this.nameOfClass = nameOfClass;
        this.symbol = symbol;
        this.liczba = liczba;
        this.year = year;
        this.locked = locked;
        this.enabled = enabled;
        this.minAvgGrade = minAvgGrade;
        this.numberOfPointsForOlimp = numberOfPointsForOlimp;
        this.numberOfPointsForFinalist = numberOfPointsForFinalist;
        this.minAmountOfPointsFromExams = minAmountOfPointsFromExams;
        this.weightOfGrade = weightOfGrade;
        this.umiejetnosci = umiejetnosci;
        this.przedmioty = przedmioty;
//        this.listOfStudent = listOfStudent;
        this.student = student;
        this.first = first;
        this.second = second;
        this.third = third;
        this.weightExamMath = weightExamMath;
        this.weightExamPolish = weightExamPolish;
        this.weightExamEnglish = weightExamEnglish;
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
                ", przedmioty=" + przedmioty +
                ", student=" + student +
                ", first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", third='" + third + '\'' +
                ", weightExamMath=" + weightExamMath +
                ", weightExamPolish=" + weightExamPolish +
                ", weightExamEnglish=" + weightExamEnglish +
                '}';
    }
}
