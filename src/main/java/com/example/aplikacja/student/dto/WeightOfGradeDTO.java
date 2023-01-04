package com.example.aplikacja.student.dto;


import com.example.aplikacja.student.entity.Klasa;
import com.example.aplikacja.student.entity.Student;
import com.example.aplikacja.student.entity.WeightOfGrade;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Data
public class WeightOfGradeDTO{
    private Long id1;
    private Double wartosc1;
    private Long id2;
    private Double wartosc2;
    private Long id3;
    private Double wartosc3;

}
