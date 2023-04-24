package com.example.aplikacja.student.service;

import com.example.aplikacja.student.dto.*;
import com.example.aplikacja.student.entity.Klasa;
import com.example.aplikacja.student.entity.Student;
import com.example.aplikacja.student.entity.WeightOfGrade;
import com.example.aplikacja.student.enums.Subject;
import com.example.aplikacja.student.repository.KlasaRepository;
import com.example.aplikacja.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KlassService {

    private final KlasaRepository klassRepository;
    private final StudentRepository studentRepository;

    public Optional<Klasa> findClassBySymbol(String symbol) {
        return klassRepository.findBySymbol(symbol);
    }

    public Optional<Klasa> findClassById(Long id) {
        return klassRepository.findById(id);
    }

    public Optional<Student> findUserByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Optional<WeightOfGradeDTO> findWeightById(Long id) {
        return klassRepository.findWById(id);
    }

    public Optional<Student> findStudentById(Long id){return studentRepository.findById(id);}

    public Optional<KlasaDTO> findClassDTOById(Long id){return klassRepository.findKDById(id);}

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public Klasa addClass(KlasaDTO cl) {
        Klasa klasaToAdd = findClassBySymbol(cl.getSymbol()).orElse(null);
        if (klasaToAdd == null) {
            Klasa klasa1 = new Klasa();
            klasa1.setNameOfClass(cl.getNameOfClass());
            klasa1.setSymbol(cl.getSymbol());
            klasa1.setLiczba(cl.getLiczba());
            klasa1.setYear(java.sql.Date.valueOf(cl.getYear()));

            klasa1.setWeightOfGrade(new ArrayList<>());
            for (Subject sub : cl.getPrzedmioty()) {
                klasa1.getWeightOfGrade().add(new WeightOfGrade(sub, klasa1));
            }
            klasa1 = klassRepository.save(klasa1);


            return klasa1;
        } else {
            return klasaToAdd;
        }
    }

    public List<Klasa> getAllKlass() {
        return klassRepository.findAll();
    }

    public Klasa updateKlasa(KlasaDTO klasa) {
        Klasa klasaToUpdate = findClassById(klasa.getId()).orElse(null);
        if (klasaToUpdate != null) {
            klasaToUpdate.setNameOfClass(klasa.getNameOfClass());
            klasaToUpdate.setLiczba(klasa.getLiczba());
//            klasaToUpdate.setSymbol(klasaToUpdate.getSymbol());
//            klasaToUpdate.setYear(java.sql.Date.valueOf(klasa.getYear()));

            return klassRepository.save(klasaToUpdate);
        } else {
            return klasaToUpdate;
        }
    }

    public Klasa countParameters(Klasa klasa) {
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        double minLiczEgz; //Minimalna liczba punktów z egzaminu
        if (klasaToUpdate != null) {
            double f = Double.parseDouble(klasaToUpdate.getFirst());
            double s = Double.parseDouble(klasaToUpdate.getSecond());
            double t = Double.parseDouble(klasaToUpdate.getThird());

            minLiczEgz = 100 * f + 50 * s + 25 * t;

            double result = round(minLiczEgz);

            klasaToUpdate.setMinAmountOfPointsFromExams(result);
            klassRepository.save(klasaToUpdate);

            return klasaToUpdate;
        } else {
            return klasaToUpdate;
        }
    }

    public Klasa minSrKier(Klasa klasa, List<WeightOfGrade> lista){
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        if (klasaToUpdate != null) {
            double minimum = lista.stream().mapToDouble(w -> ((w.getWartosc() * 6
                    + w.getWartosc() * 2)*0.70)/10.00).sum();

            double minimum2 = round(minimum - 1.30);

            klasaToUpdate.setMinAvgGrade(minimum2);
            klassRepository.save(klasaToUpdate);

            return klasaToUpdate;
        }else {
            return klasaToUpdate;
        }
    }

    public int enableClass(String symbol) {
        return klassRepository.enableClass(symbol);
    }

    public int diableClass(String symbol) {
        return klassRepository.disableClass(symbol);
    }

    public void deleteOurClass(Klasa klasa) {
        klassRepository.delete(klasa);
    }


    public Klasa updateSubjectOfKlass(KlasaDTO klasa, WeightOfGradeDTO wog) {
        Klasa klasaToUpdate = findClassById(klasa.getId()).orElse(null);
//        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        if (klasaToUpdate != null) {
            klasaToUpdate.setEnabled(klasa.getEnabled());

            klasaToUpdate.getWeightOfGrade().stream().filter(
                            w -> Objects.equals(w.getId(), wog.getId1()))
                    .findFirst().get()
                    .setWartosc(wog.getWartosc1());

            klasaToUpdate.getWeightOfGrade().stream().filter(
                            w -> Objects.equals(w.getId(), wog.getId2()))
                    .findFirst().get()
                    .setWartosc(wog.getWartosc2());

            klasaToUpdate.getWeightOfGrade().stream().filter(
                            w -> Objects.equals(w.getId(), wog.getId3()))
                    .findFirst().get()
                    .setWartosc(wog.getWartosc3());

            return klassRepository.save(klasaToUpdate);
        } else {
            return klasaToUpdate;
        }
    }

    public Klasa updateSkillsOfKlass(KlasaDTO klasa) {
        Klasa klasaToUpdate = findClassById(klasa.getId()).orElse(null);
//        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        if (klasaToUpdate != null) {
            klasaToUpdate.setEnabled(klasa.getEnabled());
            klasaToUpdate.setUmiejetnosci(new ArrayList<>());
            klasaToUpdate.getUmiejetnosci().addAll(klasa.getUmiejetnosci());
            klasaToUpdate.setMinAvgGrade(klasa.getMinAvgGrade());
            return klassRepository.save(klasaToUpdate);
        }
        return klasaToUpdate;
    }

    public Klasa addNewParameters(KlasaDTO klasa) {
//        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        Klasa klasaToUpdate = findClassById(klasa.getId()).orElse(null);
        if (klasaToUpdate != null) {
            klasaToUpdate.setMinAvgGrade(klasa.getMinAvgGrade());
            klasaToUpdate.setNumberOfPointsForOlimp(klasa.getNumberOfPointsForOlimp());
            klasaToUpdate.setNumberOfPointsForFinalist(klasa.getNumberOfPointsForFinalist());
            klasaToUpdate.setMinAmountOfPointsFromExams(klasa.getMinAmountOfPointsFromExams());
            klasaToUpdate.setFirst(String.valueOf(klasa.getFirst()));
            klasaToUpdate.setSecond(String.valueOf(klasa.getSecond()));
            klasaToUpdate.setThird(String.valueOf(klasa.getThird()));
            klasaToUpdate.setWeightExamMath(String.valueOf(klasa.getWeightExamMath()));
            klasaToUpdate.setWeightExamPolish(String.valueOf(klasa.getWeightExamPolish()));
            klasaToUpdate.setWeightExamEnglish(String.valueOf(klasa.getWeightExamEnglish()));
            return klassRepository.save(klasaToUpdate);
        }
        return klasaToUpdate;
    }

    public double round(double value){
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}