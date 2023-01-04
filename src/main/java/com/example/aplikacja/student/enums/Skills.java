package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum Skills {

    Umiejętność_szybkiego_liczenia("fastCounting"),
    Umiejętność_szybkiego_czytania("fastReading"),
    Umiejętność_rozwiązywania_problemów("troubleshooting"),
    Umiejętność_szybkiego_zapamietywania("quickMemorization"),
    Umiejętności_aktorskie("actingSkills"),
    Umiejętności_wokalne("vocalSkills"),
    Umiejętności_taneczna("danceSkills"),
    Umiejętności_pisarskie("writingSkills"),
    Umiejętności_fotograficzne("photographicSkills"),
    Zdolności_lingwistyczne("linguisticSkills"),
    Posiadanie_certyfikatu_językowego("languageCertificate"),
    Zainteresowanie_polityką("interestInPolitics"),
    Umiejetności_komunikacyjne("communicationSkills"),
    Umiejętności_sportowe("sportSkills"),
    Wyczynowe_uprawianie_sportu("extremeSport"),
    Sprawność_fizyczna("physicalFitness"),
    Wytrzymałość_fizyczna("physicalEndurance"),
    Umiejętność_pracy_na_otwartym_terenie("workInTheOpenGround"),
    Umiejętność_posługiwania_się_mapą("abilityToUseAMap"),
    Znajmość_tablicy_Mendelejewa("periodicTable"),
    Tworzenie_własnnych_doświadczeń_chemicznych("chemicalExperiments"),
    Zainteresowania_biologiczno_chemiczne("biologicalAndNaturalInterests"),
    Zainterersowanie_technologią("interestInTechnology");


    private String label;

    Skills(String label) {
        this.label = label;
    }
}
