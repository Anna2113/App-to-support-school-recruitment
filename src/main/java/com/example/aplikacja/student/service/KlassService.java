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


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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


    public Klasa addClass(KlasaDTO cl) {
        Klasa klasaToAdd = findClassBySymbol(cl.getSymbol()).orElse(null);
        if (klasaToAdd == null) {
            Klasa klasa1 = new Klasa();
            klasa1.setNameOfClass(cl.getNameOfClass());
            klasa1.setSymbol(cl.getSymbol());
            klasa1.setLiczba(cl.getLiczba());
            klasa1.setYear(java.sql.Date.valueOf(cl.getYear()));
//            klasa1.setUmiejetnosci(new ArrayList<>());
//            klasa1.getUmiejetnosci().addAll(cl.getUmiejetnosci());

//            klasa1.setEnabled(cl.getEnabled());
//            klasa1.setPrzedmiot(new ArrayList<>());
//            klasa1.getPrzedmiot().addAll(cl.getPrzedmiot());

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
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        if (klasaToUpdate != null) {
            klasaToUpdate.setNameOfClass(klasa.getNameOfClass());
            klasaToUpdate.setSymbol(klasa.getSymbol());
            klasaToUpdate.setLiczba(klasa.getLiczba());
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

            klasaToUpdate.setMinAmountOfPointsFromExams((double) Math.round(minLiczEgz));
            klassRepository.save(klasaToUpdate);

            return klasaToUpdate;
        } else {
            return klasaToUpdate;
        }

    }

    public Klasa minSrKier(Klasa klasa, List<WeightOfGrade> lista){
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        if (klasaToUpdate != null) {
            double minimum = lista.stream().mapToDouble(w -> w.getWartosc() * 6 + w.getWartosc() * 2).sum();

            //            double miniSre;
//            minimum = 6 * weight.getWartosc1() + 2 * weight.getWartosc1() +
//                    6 * weight.getWartosc2() + 2 * weight.getWartosc2() +
//                    6 * weight.getWartosc3() + 2 * weight.getWartosc3();

//            miniSre = minimum.get() / 2*(weight.getWartosc1() + weight.getWartosc2() + weight.getWartosc3());

//            Minimalna średnia kierunkowa = (ocena.math * 0.4 + ocena.inf * 0.3 + ocena.geo * 0.2)/ 0.9.

            klasaToUpdate.setMinAvgGrade(minimum);
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
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
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
//        Klasa klasaToUpdate = findClassById(klasa.getId()).orElse(null);
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
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
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        if (klasaToUpdate != null) {
//            if (klasaToUpdate.getUmiejetnosci() != null && klasaToUpdate.getPrzedmiot() != null) {
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

}
