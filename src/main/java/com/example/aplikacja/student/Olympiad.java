package com.example.aplikacja.student;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity


public class Olympiad {

    @SequenceGenerator(
            name = "sequenceOlympiad",
            sequenceName = "sequenceOlympiad",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceOlympiad"
    )

    @Column
    private Long id;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist polishOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist mathOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist englishOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist germanOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist frenchOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist spanishOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist italianOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist historyOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist civicsOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist biologyOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist chemistryOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist physicsOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist geographyOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist historyOfMusicOlympiad;
    @Enumerated(EnumType.STRING)
    private LaureateOrFinalist historyOfArtOlympiad;
    @Enumerated(EnumType.STRING)
    @OneToOne
    private Student student;

    public Olympiad(Long id, LaureateOrFinalist polishOlympiad,
                    LaureateOrFinalist mathOlympiad,
                    LaureateOrFinalist englishOlympiad,
                    LaureateOrFinalist germanOlympiad,
                    LaureateOrFinalist frenchOlympiad, LaureateOrFinalist spanishOlympiad,
                    LaureateOrFinalist italianOlympiad, LaureateOrFinalist historyOlympiad,
                    LaureateOrFinalist civicsOlympiad, LaureateOrFinalist biologyOlympiad,
                    LaureateOrFinalist chemistryOlympiad, LaureateOrFinalist physicsOlympiad,
                    LaureateOrFinalist geographyOlympiad,
                    LaureateOrFinalist historyOfMusicOlympiad,
                    LaureateOrFinalist historyOfArtOlympiad, Student student) {
        this.id = id;
        this.polishOlympiad = polishOlympiad;
        this.mathOlympiad = mathOlympiad;
        this.englishOlympiad = englishOlympiad;
        this.germanOlympiad = germanOlympiad;
        this.frenchOlympiad = frenchOlympiad;
        this.spanishOlympiad = spanishOlympiad;
        this.italianOlympiad = italianOlympiad;
        this.historyOlympiad = historyOlympiad;
        this.civicsOlympiad = civicsOlympiad;
        this.biologyOlympiad = biologyOlympiad;
        this.chemistryOlympiad = chemistryOlympiad;
        this.physicsOlympiad = physicsOlympiad;
        this.geographyOlympiad = geographyOlympiad;
        this.historyOfMusicOlympiad = historyOfMusicOlympiad;
        this.historyOfArtOlympiad = historyOfArtOlympiad;
        this.student = student;
    }

    public Olympiad(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Olympiad{" +
                "id=" + id +
                ", polishOlympiad=" + polishOlympiad +
                ", mathOlympiad=" + mathOlympiad +
                ", englishOlympiad=" + englishOlympiad +
                ", germanOlympiad=" + germanOlympiad +
                ", frenchOlympiad=" + frenchOlympiad +
                ", spanishOlympiad=" + spanishOlympiad +
                ", italianOlympiad=" + italianOlympiad +
                ", historyOlympiad=" + historyOlympiad +
                ", civicsOlympiad=" + civicsOlympiad +
                ", biologyOlympiad=" + biologyOlympiad +
                ", chemistryOlympiad=" + chemistryOlympiad +
                ", physicsOlympiad=" + physicsOlympiad +
                ", geographyOlympiad=" + geographyOlympiad +
                ", historyOfMusicOlympiad=" + historyOfMusicOlympiad +
                ", historyOfArtOlympiad=" + historyOfArtOlympiad +
                '}';
    }
}
