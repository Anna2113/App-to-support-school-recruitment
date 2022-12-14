package com.example.aplikacja.student.dto;

import com.example.aplikacja.student.enums.Ability;
import lombok.Data;

@Data
public class ExtraParametersDTO {
    private Ability  fastCounting;
    private Ability  fastReading;
    private Ability  troubleshooting;
    private Ability  quickMemorization;
    private Ability  actingSkills;
    private Ability  vocalSkills;
    private Ability  danceSkills;
    private Ability writingSkills;
    private Ability  photographicSkills;
    private Ability  linguisticSkills;
    private Ability  sportSkills; //umiejętności sportowe
    private Ability  extremeSport; //sport wyczynowy
    private Ability  physicalFitness; //sprawność fizyczna
    private Ability  physicalEndurance; //wytrzymałość fizyczna
    private Ability  workInTheOpenGround; // praca na otwartym terenie
    private Ability  abilityToUseAMap; //posługiwanie się mapą
    private Ability  biologicalAndNaturalInterests;
    private Ability  interestInTechnology;
}
