package com.example.aplikacja.student.entity;

import com.example.aplikacja.appuser.AppUserRole;
import com.example.aplikacja.student.enums.Sex;
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
    private Boolean align;
    private Boolean languagePolish;
    private Boolean locked = false;
    private Boolean enabled = true;
    //    private String className;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Exam exams;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Grade grades;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Olympiad olympiads;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ExtraParameters extraParameters;


    public Student(Long id, String firstName,
                   String lastName, String email, LocalDate dateOfBirth, List<AppUserRole> appUserRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.appUserRole = appUserRole;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id =" + id +
                ", imie ='" + firstName +
                ", nazwisko ='" + lastName +
                ", data urodzenia =" + dateOfBirth +
                ", płeć =" + sex +
                ", cudzoziemiec =" + align +
                ", czy mówi w języku polskim =" + languagePolish +
                '}';
    }
}