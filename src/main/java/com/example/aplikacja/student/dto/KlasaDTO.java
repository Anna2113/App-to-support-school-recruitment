package com.example.aplikacja.student.dto;

import com.example.aplikacja.student.entity.WeightOfGrade;
import com.example.aplikacja.student.enums.NameOfClass;
import com.example.aplikacja.student.enums.Skills;
import com.example.aplikacja.student.enums.Subject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KlasaDTO {
    private Long id;
    private NameOfClass nameOfClass;
    private String symbol;
    private String liczba;
    private Boolean locked = false;
    private Boolean enabled = true;
    private Double minAvgGrade;
    private Double numberOfPointsForOlimp;
    private Double numberOfPointsForFinalist;
    private Double minAmountOfPointsFromExams;
    private Double wartosc;
    private List<WeightOfGrade> weightOfGrade;
    private List<Skills> umiejetnosci;
    private List<Subject> przedmioty;
    private String first;
    private String second;
    private String third;
    private String weightExamMath;
    private String weightExamPolish;
    private String weightExamEnglish;
}
