package com.example.aplikacja.student.entity;


import lombok.*;

import javax.persistence.*;
@ToString
@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
public class Grade {

    @SequenceGenerator(
            name = "sequenceGrade",
            sequenceName = "sequenceGrade",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceGrade"
    )

    @Column
    private Long id;
    private String averageOfGrades;
    private String polishGrade;
    private String mathGrade;
    private String englishGrade;
    private String otherLanguageGrade;
    private String civicsGrade;
    private String historyGrade;
    private String physicsGrade;
    private String chemistryGrade;
    private String biologyGrade;
    private String geographyGrade;
    private String ITGrade;
    private String physicalEducationGrade;
    private String designAndTechnology;
    private String music;
    private String art;
    @OneToOne
    private Student student;


    public Grade(Long id, String averageOfGrades, String polishGrade,
                 String mathGrade, String englishGrade, String otherLanguageGrade,
                 String civicsGrade, String historyGrade, String physicsGrade,
                 String chemistryGrade, String biologyGrade, String geographyGrade,
                 String ITGrade, String physicalEducationGrade, String designAndTechnology,
                 String music, String art) {
        this.id = id;
        this.averageOfGrades = averageOfGrades;
        this.polishGrade = polishGrade;
        this.mathGrade = mathGrade;
        this.englishGrade = englishGrade;
        this.otherLanguageGrade = otherLanguageGrade;
        this.civicsGrade = civicsGrade;
        this.historyGrade = historyGrade;
        this.physicsGrade = physicsGrade;
        this.chemistryGrade = chemistryGrade;
        this.biologyGrade = biologyGrade;
        this.geographyGrade = geographyGrade;
        this.ITGrade = ITGrade;
        this.physicalEducationGrade = physicalEducationGrade;
        this.designAndTechnology = designAndTechnology;
        this.music = music;
        this.art = art;
    }

    public Grade(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", averageOfGrades='" + averageOfGrades + '\'' +
                ", polishGrade='" + polishGrade + '\'' +
                ", mathGrade='" + mathGrade + '\'' +
                ", englishGrade='" + englishGrade + '\'' +
                ", otherLanguageGrade='" + otherLanguageGrade + '\'' +
                ", civicsGrade='" + civicsGrade + '\'' +
                ", historyGrade='" + historyGrade + '\'' +
                ", physicsGrade='" + physicsGrade + '\'' +
                ", chemistryGrade='" + chemistryGrade + '\'' +
                ", biologyGrade='" + biologyGrade + '\'' +
                ", geographyGrade='" + geographyGrade + '\'' +
                ", ITGrade='" + ITGrade + '\'' +
                ", physicalEducationGrade='" + physicalEducationGrade + '\'' +
                ", designAndTechnology='" + designAndTechnology + '\'' +
                ", student=" + student +
                '}';
    }
}
