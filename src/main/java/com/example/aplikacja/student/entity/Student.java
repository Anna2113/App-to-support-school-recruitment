package com.example.aplikacja.student.entity;

import com.example.aplikacja.appuser.AppUser;
import com.example.aplikacja.appuser.AppUserRole;
import com.example.aplikacja.student.enums.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@ToString
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
    private Double pointsBiolChem;
    private Double pointsMAN;
    private Double pointsArt;
    private Double pointsS;
    private Double pointsFIZ;
    private String classForStudent;
    private String firstClassification;
    private Double punktyOlimpijskieMatGeoInf;
    private Double punktyOlimpijskieHuman;
    private Double punktyOlimpijskieBiolChem;
    private Double punktyOlimpijskieMatAngNiem;
    private Double punktyOlimpijskieSportowa;
    private Double punktyOlimpijskieArtystyczna;
    private Double punktyOlimpijskieFizChemFranc;
    //    private Double punkty;
//    private Double punktyOlimpijskie;
    @Enumerated(EnumType.STRING)
    private StudentStatus status;
    private Double classificationPoints;
    @OneToOne
    private AppUser appUser;


    //    @ManyToOne
//    @JoinColumn(name = "klasa")
//    private Klasa newKlasa;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Exam exams;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Grade grades;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Olympiad olympiads;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ExtraParameters extraParameters;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Klasa klasa;
    @OneToMany(mappedBy = "student")
    private List<ClassStudentResult> listaPom;


    public Student(String firstName, String lastName, String email, LocalDate dateOfBirth, Sex sex, Align align,
                   LanguagePolish languagePolish, Boolean locked, Boolean enabled, Double pointsMatGeoInf,
                   Double pointsHuman, Double pointsBiolChem, Double pointsMAN, Double pointsArt, Double pointsS,
                   Double pointsFIZ, String classForStudent, String firstClassification,
                   Double punktyOlimpijskieMatGeoInf, Double punktyOlimpijskieHuman,
                   Double punktyOlimpijskieBiolChem, Double punktyOlimpijskieMatAngNiem,
                   Double punktyOlimpijskieSportowa, Double punktyOlimpijskieArtystyczna,
                   Double punktyOlimpijskieFizChemFranc, StudentStatus status, Double classificationPoints,
                   AppUser appUser, Exam exams, Grade grades, Olympiad olympiads, ExtraParameters extraParameters,
                   Klasa klasa, List<ClassStudentResult> listaPom) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.align = align;
        this.languagePolish = languagePolish;
        this.locked = locked;
        this.enabled = enabled;
        this.pointsMatGeoInf = pointsMatGeoInf;
        this.pointsHuman = pointsHuman;
        this.pointsBiolChem = pointsBiolChem;
        this.pointsMAN = pointsMAN;
        this.pointsArt = pointsArt;
        this.pointsS = pointsS;
        this.pointsFIZ = pointsFIZ;
        this.classForStudent = classForStudent;
        this.firstClassification = firstClassification;
        this.punktyOlimpijskieMatGeoInf = punktyOlimpijskieMatGeoInf;
        this.punktyOlimpijskieHuman = punktyOlimpijskieHuman;
        this.punktyOlimpijskieBiolChem = punktyOlimpijskieBiolChem;
        this.punktyOlimpijskieMatAngNiem = punktyOlimpijskieMatAngNiem;
        this.punktyOlimpijskieSportowa = punktyOlimpijskieSportowa;
        this.punktyOlimpijskieArtystyczna = punktyOlimpijskieArtystyczna;
        this.punktyOlimpijskieFizChemFranc = punktyOlimpijskieFizChemFranc;
        this.status = status;
        this.classificationPoints = classificationPoints;
        this.appUser = appUser;
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
