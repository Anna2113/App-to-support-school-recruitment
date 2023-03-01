package com.example.aplikacja.student.entity;


import com.example.aplikacja.student.enums.Ability;
import lombok.*;

import javax.persistence.*;
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ExtraParameters {

    @SequenceGenerator(
            name = "sequenceExtraParameters",
            sequenceName = "sequenceExtraParameters",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceExtraParameters"
    )

    @Column
    private Long id;
    @Enumerated(EnumType.STRING)
    private Ability fastCounting;
    @Enumerated(EnumType.STRING)
    private Ability fastReading;
    @Enumerated(EnumType.STRING)
    private Ability troubleshooting;
    @Enumerated(EnumType.STRING)
    private Ability quickMemorization;
    @Enumerated(EnumType.STRING)
    private Ability actingSkills;
    @Enumerated(EnumType.STRING)
    private Ability vocalSkills;
    @Enumerated(EnumType.STRING)
    private Ability danceSkills;
    @Enumerated(EnumType.STRING)
    private Ability writingSkills;
    @Enumerated(EnumType.STRING)
    private Ability photographicSkills;
    @Enumerated(EnumType.STRING)
    private Ability linguisticSkills;
    @Enumerated(EnumType.STRING)
    private Ability languageCertificate;
    @Enumerated(EnumType.STRING)
    private Ability interestInPolitics;
    @Enumerated(EnumType.STRING)
    private Ability communicationSkills;
    @Enumerated(EnumType.STRING)
    private Ability sportSkills; //umiejętności sportowe
    @Enumerated(EnumType.STRING)
    private Ability extremeSport; //sport wyczynowy
    @Enumerated(EnumType.STRING)
    private Ability physicalFitness; //sprawność fizyczna
    @Enumerated(EnumType.STRING)
    private Ability physicalEndurance; //wytrzymałość fizyczna
    @Enumerated(EnumType.STRING)
    private Ability workInTheOpenGround; // praca na otwartym terenie
    @Enumerated(EnumType.STRING)
    private Ability abilityToUseAMap; //posługiwanie się mapą
    @Enumerated(EnumType.STRING)
    private Ability periodicTable; //układ okresowy pierwiastków
    @Enumerated(EnumType.STRING)
    private Ability chemicalExperiments;
    @Enumerated(EnumType.STRING)
    private Ability biologicalAndNaturalInterests;
    @Enumerated(EnumType.STRING)
    private Ability interestInTechnology;
    @OneToOne
    private Student student;

    public ExtraParameters(Long id, Ability fastCounting, Ability fastReading, Ability troubleshooting,
                           Ability quickMemorization, Ability actingSkills, Ability vocalSkills,
                           Ability danceSkills, Ability writingSkills, Ability photographicSkills,
                           Ability linguisticSkills, Ability languageCertificate,
                           Ability interestInPolitics, Ability communicationSkills, Ability sportSkills,
                           Ability extremeSport, Ability physicalFitness, Ability physicalEndurance,
                           Ability workInTheOpenGround, Ability abilityToUseAMap, Ability periodicTable,
                           Ability chemicalExperiments, Ability biologicalAndNaturalInterests,
                           Ability interestInTechnology, Student student) {
        this.id = id;
        this.fastCounting = fastCounting;
        this.fastReading = fastReading;
        this.troubleshooting = troubleshooting;
        this.quickMemorization = quickMemorization;
        this.actingSkills = actingSkills;
        this.vocalSkills = vocalSkills;
        this.danceSkills = danceSkills;
        this.writingSkills = writingSkills;
        this.photographicSkills = photographicSkills;
        this.linguisticSkills = linguisticSkills;
        this.languageCertificate = languageCertificate;
        this.interestInPolitics = interestInPolitics;
        this.communicationSkills = communicationSkills;
        this.sportSkills = sportSkills;
        this.extremeSport = extremeSport;
        this.physicalFitness = physicalFitness;
        this.physicalEndurance = physicalEndurance;
        this.workInTheOpenGround = workInTheOpenGround;
        this.abilityToUseAMap = abilityToUseAMap;
        this.periodicTable = periodicTable;
        this.chemicalExperiments = chemicalExperiments;
        this.biologicalAndNaturalInterests = biologicalAndNaturalInterests;
        this.interestInTechnology = interestInTechnology;
        this.student = student;
    }

    public ExtraParameters(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "ExtraParameters{" +
                "id=" + id +
                ", fastCounting=" + fastCounting +
                ", fastReading=" + fastReading +
                ", troubleshooting=" + troubleshooting +
                ", quickMemorization=" + quickMemorization +
                ", actingSkills=" + actingSkills +
                ", vocalSkills=" + vocalSkills +
                ", danceSkills=" + danceSkills +
                ", writingSkills=" + writingSkills +
                ", photographicSkills=" + photographicSkills +
                ", linguisticSkills=" + linguisticSkills +
                ", sportSkills=" + sportSkills +
                ", extremeSport=" + extremeSport +
                ", physicalFitness=" + physicalFitness +
                ", physicalEndurance=" + physicalEndurance +
                ", workInTheOpenGround=" + workInTheOpenGround +
                ", abilityToUseAMap=" + abilityToUseAMap +
                ", biologicalAndNaturalInterests=" + biologicalAndNaturalInterests +
                ", interestInTechnology=" + interestInTechnology +
                '}';
    }
}
