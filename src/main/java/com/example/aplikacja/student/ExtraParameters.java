package com.example.aplikacja.student;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
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

    private Long id;
    private Boolean fastCounting;
    private Boolean fastReading;
    private Boolean troubleshooting;
    private Boolean quickMemorization;
    private Boolean actingSkills;
    private Boolean vocalSkills;
    private Boolean danceSkills;
    private Boolean writingSkills;
    private Boolean photographicSkills;
    private Boolean linguisticSkills;
    private Boolean sportSkills; //umiejętności sportowe
    private Boolean extremeSport; //sport wyczynowy
    private Boolean physicalFitness; //sprawność fizyczna
    private Boolean physicalEndurance; //wytrzymałość fizyczna
    private Boolean workInTheOpenGround; // praca na otwartym terenie
    private Boolean abilityToUseAMap; //posługiwanie się mapą
    private Boolean biologicalAndNaturalInterests;
    private Boolean interestInTechnology;
    @OneToOne
    private Student student;

    public ExtraParameters(Long id, Boolean fastCounting, Boolean fastReading,
                           Boolean troubleshooting, Boolean quickMemorization,
                           Boolean actingSkills, Boolean vocalSkills, Boolean danceSkills,
                           Boolean writingSkills, Boolean photographicSkills,
                           Boolean linguisticSkills, Boolean sportSkills, Boolean extremeSport,
                           Boolean physicalFitness, Boolean physicalEndurance,
                           Boolean workInTheOpenGround, Boolean abilityToUseAMap,
                           Boolean biologicalAndNaturalInterests, Boolean interestInTechnology) {
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
        this.sportSkills = sportSkills;
        this.extremeSport = extremeSport;
        this.physicalFitness = physicalFitness;
        this.physicalEndurance = physicalEndurance;
        this.workInTheOpenGround = workInTheOpenGround;
        this.abilityToUseAMap = abilityToUseAMap;
        this.biologicalAndNaturalInterests = biologicalAndNaturalInterests;
        this.interestInTechnology = interestInTechnology;
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
