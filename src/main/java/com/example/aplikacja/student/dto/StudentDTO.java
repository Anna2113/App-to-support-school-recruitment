package com.example.aplikacja.student.dto;

import com.example.aplikacja.student.entity.Exam;
import com.example.aplikacja.student.entity.Klasa;
import com.example.aplikacja.student.enums.*;
import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private Sex sex;
    private Align align;
    private LanguagePolish languagePolish;
    private Double points;
    private Double pointsMatGeoInf;
    private Double pointsHuman;
    private Double pointsBiolChemAng;
    private Double pointsMAN;
    private Double pointsArt;
    private Double pointsS;
    private Double pointsFIZ;
    private NameOfClass classForStudent;
    private Double classificationPoints;
    private String firstClassification;
}
