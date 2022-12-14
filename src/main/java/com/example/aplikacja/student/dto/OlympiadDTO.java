package com.example.aplikacja.student.dto;

import com.example.aplikacja.student.enums.LaureateOrFinalist;
import lombok.Data;

@Data
public class OlympiadDTO {
    private LaureateOrFinalist polishOlympiad;
    private LaureateOrFinalist mathOlympiad;
    private LaureateOrFinalist englishOlympiad;
    private LaureateOrFinalist germanOlympiad;
    private LaureateOrFinalist frenchOlympiad;
    private LaureateOrFinalist spanishOlympiad;
    private LaureateOrFinalist italianOlympiad;
    private LaureateOrFinalist historyOlympiad;
    private LaureateOrFinalist civicsOlympiad;
    private LaureateOrFinalist biologyOlympiad;
    private LaureateOrFinalist chemistryOlympiad;
    private LaureateOrFinalist physicsOlympiad;
    private LaureateOrFinalist geographyOlympiad;
    private LaureateOrFinalist historyOfMusicOlympiad;
    private LaureateOrFinalist historyOfArtOlympiad;
    private LaureateOrFinalist ITOlympiad;
}
