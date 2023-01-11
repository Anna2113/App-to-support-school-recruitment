package com.example.aplikacja.student.entity;

import com.example.aplikacja.appuser.AppUserRole;
import com.example.aplikacja.student.dto.WeightOfGradeDTO;
import com.example.aplikacja.student.enums.Align;
import com.example.aplikacja.student.enums.LanguagePolish;
import com.example.aplikacja.student.enums.Sex;
import com.example.aplikacja.student.enums.Skills;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
public class Student {

    @SequenceGenerator(
            name = "sequenceStudent",
            sequenceName = "sequenceStudent",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceStudent"
    )


    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = AppUserRole.class)
    private List<AppUserRole> appUserRole;
    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Align align;
    @Enumerated(EnumType.STRING)
    private LanguagePolish languagePolish;
    private Boolean locked = false;
    private Boolean enabled = true;
    private Double pointsMatGeoInf;
    private Double pointsHuman;
    private Double pointsBiolChemAng;
    private Double pointsMatAngNiem;
    private Double pointsMuzyczna;
    private Double pointsAktorska;
    private Double pointsSportowa;

//    @Enumerated
//    @ElementCollection(targetClass = Klasa.class)
//    private List<Klasa> listaKlas;
    //    private String className;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Exam exams;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Grade grades;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Olympiad olympiads;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ExtraParameters extraParameters;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Klasa klasa;
    @OneToMany(mappedBy = "student")
    private List<ClassStudentResult> listaPom;


    public Student(String firstName, String lastName, String email, List<AppUserRole> appUserRole, LocalDate dateOfBirth, Sex sex, Align align, LanguagePolish languagePolish, Boolean locked, Boolean enabled, Double pointsMatGeoInf, Double pointsHuman, Exam exams, Grade grades, Olympiad olympiads, ExtraParameters extraParameters, Klasa klasa, List<ClassStudentResult> listaPom) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.appUserRole = appUserRole;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.align = align;
        this.languagePolish = languagePolish;
        this.locked = locked;
        this.enabled = enabled;
        this.pointsMatGeoInf = pointsMatGeoInf;
        this.pointsHuman = pointsHuman;
        this.exams = exams;
        this.grades = grades;
        this.olympiads = olympiads;
        this.extraParameters = extraParameters;
        this.klasa = klasa;
        this.listaPom = listaPom;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", appUserRole=" + appUserRole +
                ", dateOfBirth=" + dateOfBirth +
                ", sex=" + sex +
                ", align=" + align +
                ", languagePolish=" + languagePolish +
                ", locked=" + locked +
                ", enabled=" + enabled +
                ", pointsMatGeoInf=" + pointsMatGeoInf +
                ", pointsHuman=" + pointsHuman +
                ", exams=" + exams +
                ", grades=" + grades +
                ", olympiads=" + olympiads +
                ", extraParameters=" + extraParameters +
                ", klasa=" + klasa +
                ", listaPom=" + listaPom +
                '}';
    }
}
