package com.example.aplikacja.student.service;

import com.example.aplikacja.student.dto.*;
import com.example.aplikacja.student.entity.*;
import com.example.aplikacja.student.enums.*;
import com.example.aplikacja.student.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            " student with email %s not found";

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ExamRepository examRepository;
    private final GradeRepository gradeRepository;
    private final OlympiadRepository olympiadRepository;
    private final ExtraParametersRepository extraParameters;
    private final KlasaRepository klassRepository;
    private final ClassStudentResultRepository csrr;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return (UserDetails) studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public Optional<Student> findUserById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Klasa> findClassById(Long id) {
        return klassRepository.findById(id);
    }


    public Optional<Exam> findExamById(Long id) {
        return examRepository.findById(id);
    }

    public Optional<Grade> findGradeById(Long id) {
        return gradeRepository.findById(id);
    }

    public Optional<Olympiad> findOlympiadById(Long id) {
        return olympiadRepository.findById(id);
    }

    public Optional<ExtraParameters> findExParamById(Long id) {
        return extraParameters.findById(id);
    }


    public Optional<Klasa> findClassBySymbol(String symbol) {
        return klassRepository.findBySymbol(symbol);
    }


    public Optional<Student> findUserByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> listaStWKl(String nazwaKlasy) {
        return studentRepository.studenciWKlasie(nazwaKlasy);
    }

    public List<Student> listaRezerwowa() {
        return studentRepository.reserveList();
    }


    public Student addPointsMatGeoInf(Student student) {

        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);

        Klasa klasa1 = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.MatGeoInf)).findFirst().get();

        double wagaMath = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Matematyka)).findFirst()
                .get().getWartosc();
        double wagaGeo = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Geografia)).findFirst()
                .get().getWartosc();
        double wagaInf = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Informatyka)).findFirst()
                .get().getWartosc();

        String mathGrade = studentToUpdate.getGrades().getMathGrade();
        String geoGrade = studentToUpdate.getGrades().getGeographyGrade();
        String itGrade = studentToUpdate.getGrades().getITGrade();

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finMat = studentToUpdate.getOlympiads().getMathOlympiad();
        LaureateOrFinalist finGeo = studentToUpdate.getOlympiads().getGeographyOlympiad();
        LaureateOrFinalist finInf = studentToUpdate.getOlympiads().getITOlympiad();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();
        double punkty_fin = 0.0;
        double punktyMatGeoInf = 0.0;
        double pointsFromExams = 0.0;
        double punkty = 0.0;


        punktyMatGeoInf = wagaMath * Double.parseDouble(mathGrade)
                + wagaGeo * Double.parseDouble(geoGrade)
                + wagaInf * Double.parseDouble(itGrade);

        pointsFromExams = Double.parseDouble(mathExam) * Double.parseDouble(wagaMathExam)
                + Double.parseDouble(polExam) * Double.parseDouble(wagaPolExam)
                + Double.parseDouble(engExam) * Double.parseDouble(wagaEngExam);

        if (finMat == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finGeo == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finInf == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaMath;
        }
        if (finGeo == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaGeo;
        }
        if (finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaInf;
        }
        if (finMat == LaureateOrFinalist.Finalista && finGeo == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMath + wagaGeo);
        }
        if (finMat == LaureateOrFinalist.Finalista && finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMath + wagaInf);
        }
        if (finGeo == LaureateOrFinalist.Finalista && finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaGeo + wagaInf);
        }
        if (finMat == LaureateOrFinalist.Finalista && finGeo == LaureateOrFinalist.Finalista
                && finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMath + wagaGeo + wagaInf);
        }


        if (finMat == LaureateOrFinalist.Laureat && finGeo == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaGeo;
        }
        if (finMat == LaureateOrFinalist.Laureat && finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaInf;
        }
        if (finGeo == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMath;
        }
        if (finGeo == LaureateOrFinalist.Laureat && finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaInf;
        }
        if (finInf == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMath;
        }
        if (finInf == LaureateOrFinalist.Laureat && finGeo == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaGeo;
        }
        if (finInf == LaureateOrFinalist.Brak && finMat == LaureateOrFinalist.Brak && finGeo == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktyMatGeoInf + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsMatGeoInf(round(punkty));
        studentToUpdate.setPunktyOlimpijskieMatGeoInf(round(punkty_fin));

        return studentRepository.save(studentToUpdate);
    }

    public Student addPointsHuman(Student student) {

        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);

        Klasa klasa1 = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Humanistyczna)).findFirst().get();

        double wagaPol = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Polski)).findFirst()
                .get().getWartosc();
        double wagaHis = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Historia)).findFirst()
                .get().getWartosc();
        double wagaWos = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.WOS)).findFirst()
                .get().getWartosc();

        String polGrade = studentToUpdate.getGrades().getPolishGrade();
        String hisGrade = studentToUpdate.getGrades().getHistoryGrade();
        String wosGrade = studentToUpdate.getGrades().getCivicsGrade();

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finPol = studentToUpdate.getOlympiads().getPolishOlympiad();
        LaureateOrFinalist finHis = studentToUpdate.getOlympiads().getHistoryOlympiad();
        LaureateOrFinalist finWos = studentToUpdate.getOlympiads().getCivicsOlympiad();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();
        double punkty_fin = 0.0;
        double punktyHuman = 0.0;
        double pointsFromExams = 0.0;
        double punkty = 0.0;

        punktyHuman = wagaPol * Double.parseDouble(polGrade)
                + wagaHis * Double.parseDouble(hisGrade)
                + wagaWos * Double.parseDouble(wosGrade);

        pointsFromExams = Double.parseDouble(mathExam) * Double.parseDouble(wagaMathExam)
                + Double.parseDouble(polExam) * Double.parseDouble(wagaPolExam)
                + Double.parseDouble(engExam) * Double.parseDouble(wagaEngExam);

        if (finPol == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finHis == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finWos == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaPol;
        }
        if (finHis == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaHis;
        }
        if (finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaWos;
        }
        if (finPol == LaureateOrFinalist.Finalista && finHis == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaHis);
        }
        if (finPol == LaureateOrFinalist.Finalista && finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaWos);
        }
        if (finHis == LaureateOrFinalist.Finalista && finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaHis + wagaWos);
        }
        if (finPol == LaureateOrFinalist.Finalista && finHis == LaureateOrFinalist.Finalista
                && finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaHis + wagaWos);
        }


        if (finPol == LaureateOrFinalist.Laureat && finHis == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaHis;
        }
        if (finPol == LaureateOrFinalist.Laureat && finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaWos;
        }
        if (finHis == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaPol;
        }
        if (finHis == LaureateOrFinalist.Laureat && finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaWos;
        }
        if (finWos == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaPol;
        }
        if (finWos == LaureateOrFinalist.Laureat && finHis == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaHis;
        }
        if (finPol == LaureateOrFinalist.Brak && finHis == LaureateOrFinalist.Brak && finWos == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktyHuman + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsHuman(

                round(punkty));
        studentToUpdate.setPunktyOlimpijskieHuman(

                round(punkty_fin));

        return studentRepository.save(studentToUpdate);
    }

    public Student addPointsBiolChemAng(Student student) {

        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);

        Klasa klasa1 = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.BiolChem)).findFirst().get();

        double wagaBio = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Biologia)).findFirst()
                .get().getWartosc();
        double wagaChem = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Chemia)).findFirst()
                .get().getWartosc();
        double wagaAng = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Angielski)).findFirst()
                .get().getWartosc();

        String bioGrade = studentToUpdate.getGrades().getBiologyGrade();
        String chemGrade = studentToUpdate.getGrades().getChemistryGrade();
        String angGrade = studentToUpdate.getGrades().getEnglishGrade();

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finBio = studentToUpdate.getOlympiads().getBiologyOlympiad();
        LaureateOrFinalist finChem = studentToUpdate.getOlympiads().getChemistryOlympiad();
        LaureateOrFinalist finAng = studentToUpdate.getOlympiads().getEnglishOlympiad();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();
        double punkty_fin = 0.0;
        double punktyBiolChemAng = 0.0;
        double pointsFromExams = 0.0;
        double punkty = 0.0;

        punktyBiolChemAng = wagaBio * Double.parseDouble(bioGrade)
                + wagaChem * Double.parseDouble(chemGrade)
                + wagaAng * Double.parseDouble(angGrade);

        pointsFromExams = Double.parseDouble(mathExam) * Double.parseDouble(wagaMathExam)
                + Double.parseDouble(polExam) * Double.parseDouble(wagaPolExam)
                + Double.parseDouble(engExam) * Double.parseDouble(wagaEngExam);

        if (finBio == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finChem == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finAng == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finBio == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaBio;
        }
        if (finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaChem;
        }
        if (finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaAng;
        }
        if (finBio == LaureateOrFinalist.Finalista && finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaBio + wagaChem);
        }
        if (finBio == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaBio + wagaAng);
        }
        if (finChem == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaChem + wagaAng);
        }
        if (finBio == LaureateOrFinalist.Finalista && finChem == LaureateOrFinalist.Finalista
                && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaBio + wagaChem + wagaAng);
        }


        if (finBio == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaChem;
        }
        if (finBio == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finChem == LaureateOrFinalist.Laureat && finBio == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaBio;
        }
        if (finChem == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finAng == LaureateOrFinalist.Laureat && finBio == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaBio;
        }
        if (finAng == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaChem;
        }
        if (finBio == LaureateOrFinalist.Brak && finChem == LaureateOrFinalist.Brak && finAng == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktyBiolChemAng + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsBiolChem(round(punkty));
        studentToUpdate.setPunktyOlimpijskieBiolChem(round(punkty_fin));

        return studentRepository.save(studentToUpdate);
    }

    public Student addPointsMatAngNiem(Student student) {

        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);

        Klasa klasa1 = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.MatAngNiem)).findFirst().get();

        double wagaMat = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Matematyka)).findFirst()
                .get().getWartosc();
        double wagaAng = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Angielski)).findFirst()
                .get().getWartosc();
        double wagaNiem = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Niemiecki)).findFirst()
                .get().getWartosc();
        double wagaItal = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Niemiecki)).findFirst()
                .get().getWartosc();
        double wagaSpain = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Niemiecki)).findFirst()
                .get().getWartosc();
        double wagaFranc = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Niemiecki)).findFirst()
                .get().getWartosc();

        String matGrade = studentToUpdate.getGrades().getMathGrade();
        String angGrade = studentToUpdate.getGrades().getEnglishGrade();
        String niemGrade = studentToUpdate.getGrades().getOtherLanguageGrade();

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finMat = studentToUpdate.getOlympiads().getMathOlympiad();
        LaureateOrFinalist finAng = studentToUpdate.getOlympiads().getEnglishOlympiad();
        LaureateOrFinalist finNiem = studentToUpdate.getOlympiads().getGermanOlympiad();
        LaureateOrFinalist finItal = studentToUpdate.getOlympiads().getItalianOlympiad();
        LaureateOrFinalist finSpain = studentToUpdate.getOlympiads().getSpanishOlympiad();
        LaureateOrFinalist finFranc = student.getOlympiads().getFrenchOlympiad();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();
        double punkty_fin = 0.0;
        double punktyMatAngNiem = 0.0;
        double pointsFromExams = 0.0;
        double punkty = 0.0;

        punktyMatAngNiem = wagaMat * Double.parseDouble(matGrade)
                + wagaAng * Double.parseDouble(angGrade)
                + wagaNiem * Double.parseDouble(niemGrade);

        pointsFromExams = Double.parseDouble(mathExam) * Double.parseDouble(wagaMathExam)
                + Double.parseDouble(polExam) * Double.parseDouble(wagaPolExam)
                + Double.parseDouble(engExam) * Double.parseDouble(wagaEngExam);

        if (finMat == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finAng == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finNiem == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finItal == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finSpain == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finFranc == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaMat;
        }
        if (finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaAng;
        }
        if (finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaNiem;
        }
        if (finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaItal;
        }
        if (finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaSpain;
        }
        if (finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaFranc;
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng);
        }
        if (finMat == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaNiem);
        }
        if (finMat == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaItal);
        }
        if (finMat == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaSpain);
        }
        if (finMat == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaFranc);
        }
        if (finAng == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaNiem);
        }
        if (finAng == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaItal);
        }
        if (finAng == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaSpain);
        }
        if (finAng == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaFranc);
        }
        if (finNiem == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaNiem + wagaItal);
        }
        if (finNiem == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaNiem + wagaSpain);
        }
        if (finNiem == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaNiem + wagaFranc);
        }
        if (finItal == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaItal + wagaSpain);
        }
        if (finItal == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaItal + wagaFranc);
        }
        if (finSpain == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaSpain + wagaFranc);
        }

        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaNiem);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaItal);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaSpain);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaFranc);
        }
        if (finMat == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaNiem + wagaItal);
        }
        if (finMat == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaNiem + wagaSpain);
        }
        if (finMat == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaNiem + wagaFranc);
        }
        if (finMat == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaItal + wagaSpain);
        }
        if (finMat == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaItal + wagaFranc);
        }
        if (finMat == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaSpain + wagaFranc);
        }
        if (finAng == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaNiem + wagaItal);
        }
        if (finAng == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaNiem + wagaSpain);
        }
        if (finAng == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaNiem + wagaFranc);
        }
        if (finAng == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaItal + wagaSpain);
        }
        if (finAng == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaItal + wagaFranc);
        }
        if (finAng == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaSpain + wagaFranc);
        }
        if (finNiem == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaNiem + wagaItal + wagaSpain);
        }
        if (finNiem == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaNiem + wagaItal + wagaFranc);
        }
        if (finNiem == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaNiem + wagaSpain + wagaFranc);
        }
        if (finItal == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaItal + wagaSpain + wagaFranc);
        }

        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finNiem == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaNiem + wagaItal);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finNiem == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaNiem + wagaSpain);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finNiem == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaNiem + wagaFranc);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaItal + wagaSpain);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaItal + wagaFranc);
        }
        if (finMat == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaNiem + wagaItal + wagaFranc);
        }
        if (finMat == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaNiem + wagaItal + wagaSpain);
        }
        if (finAng == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaNiem + wagaItal + wagaSpain);
        }
        if (finAng == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finItal == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaNiem + wagaItal + wagaFranc);
        }
        if (finAng == LaureateOrFinalist.Finalista && finNiem == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaNiem + wagaSpain + wagaFranc);
        }
        if (finMat == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finNiem == LaureateOrFinalist.Finalista && finItal == LaureateOrFinalist.Finalista
                && finSpain == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaAng + wagaNiem + wagaItal + wagaSpain + wagaFranc);
        }


        if (finMat == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finMat == LaureateOrFinalist.Laureat && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaNiem;
        }
        if (finMat == LaureateOrFinalist.Laureat && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaItal;
        }
        if (finMat == LaureateOrFinalist.Laureat && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSpain;
        }
        if (finMat == LaureateOrFinalist.Laureat && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFranc;
        }
        if (finAng == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMat;
        }
        if (finAng == LaureateOrFinalist.Laureat && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaNiem;
        }
        if (finAng == LaureateOrFinalist.Laureat && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaItal;
        }
        if (finAng == LaureateOrFinalist.Laureat && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSpain;
        }
        if (finAng == LaureateOrFinalist.Laureat && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFranc;
        }
        if (finNiem == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMat;
        }
        if (finNiem == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finNiem == LaureateOrFinalist.Laureat && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaItal;
        }
        if (finNiem == LaureateOrFinalist.Laureat && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSpain;
        }
        if (finNiem == LaureateOrFinalist.Laureat && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFranc;
        }

        if (finItal == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMat;
        }
        if (finItal == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finItal == LaureateOrFinalist.Laureat && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaNiem;
        }
        if (finItal == LaureateOrFinalist.Laureat && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSpain;
        }
        if (finItal == LaureateOrFinalist.Laureat && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFranc;
        }

        if (finSpain == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMat;
        }
        if (finSpain == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finSpain == LaureateOrFinalist.Laureat && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaItal;
        }
        if (finSpain == LaureateOrFinalist.Laureat && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaItal;
        }
        if (finSpain == LaureateOrFinalist.Laureat && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFranc;
        }

        if (finFranc == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMat;
        }
        if (finFranc == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finFranc == LaureateOrFinalist.Laureat && finNiem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaItal;
        }
        if (finFranc == LaureateOrFinalist.Laureat && finItal == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaItal;
        }
        if (finFranc == LaureateOrFinalist.Laureat && finSpain == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSpain;
        }

        if (finMat == LaureateOrFinalist.Brak && finAng == LaureateOrFinalist.Brak
                && finNiem == LaureateOrFinalist.Brak && finItal == LaureateOrFinalist.Brak
        && finSpain == LaureateOrFinalist.Brak && finFranc == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktyMatAngNiem + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsMAN(round(punkty));
        studentToUpdate.setPunktyOlimpijskieMatAngNiem(round(punkty_fin));


        return studentRepository.save(studentToUpdate);
    }

    public Student addPointsArt(Student student) {

        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);

        Klasa klasa1 = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Artystyczna)).findFirst().get();

        double wagaPol = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Polski)).findFirst()
                .get().getWartosc();
        double wagaAng = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Angielski)).findFirst()
                .get().getWartosc();
        double wagaMuz = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Muzyka)).findFirst()
                .get().getWartosc();
        double wagaSzt = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Muzyka)).findFirst()
                .get().getWartosc();

        String polGrade = studentToUpdate.getGrades().getPolishGrade();
        String angGrade = studentToUpdate.getGrades().getEnglishGrade();
        String muzGrade = studentToUpdate.getGrades().getMusic();

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finPol = studentToUpdate.getOlympiads().getPolishOlympiad();
        LaureateOrFinalist finAng = studentToUpdate.getOlympiads().getEnglishOlympiad();
        LaureateOrFinalist finMus = studentToUpdate.getOlympiads().getHistoryOfMusicOlympiad();
        LaureateOrFinalist finSzt = studentToUpdate.getOlympiads().getHistoryOfArtOlympiad();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();
        double punkty_fin = 0.0;
        double punktyMusic = 0.0;
        double pointsFromExams = 0.0;
        double punkty = 0.0;

        punktyMusic = wagaPol * Double.parseDouble(polGrade)
                + wagaAng * Double.parseDouble(angGrade)
                + wagaMuz * Double.parseDouble(muzGrade);

        pointsFromExams = Double.parseDouble(mathExam) * Double.parseDouble(wagaMathExam)
                + Double.parseDouble(polExam) * Double.parseDouble(wagaPolExam)
                + Double.parseDouble(engExam) * Double.parseDouble(wagaEngExam);

        if (finPol == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finAng == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finMus == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finSzt == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaPol;
        }
        if (finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaAng;
        }
        if (finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaMuz;
        }
        if (finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaSzt;
        }
        if (finPol == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaAng);
        }
        if (finPol == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaMuz);
        }
        if (finPol == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaSzt);
        }
        if (finAng == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaMuz);
        }
        if (finAng == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaSzt);
        }
        if (finMus == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMuz + wagaSzt);
        }

        if (finPol == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaAng + wagaMuz);
        }
        if (finPol == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaAng + wagaSzt);
        }
        if (finPol == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista
                && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaMuz + wagaSzt);
        }
        if (finAng == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista
                && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaAng + wagaMuz + wagaSzt);
        }
        if (finPol == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista
                && finMus == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaPol + wagaAng + wagaSzt);
        }
        if (finPol == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finPol == LaureateOrFinalist.Laureat && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMuz;
        }
        if (finPol == LaureateOrFinalist.Laureat && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSzt;
        }
        if (finAng == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaPol;
        }
        if (finAng == LaureateOrFinalist.Laureat && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMuz;
        }
        if (finAng == LaureateOrFinalist.Laureat && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSzt;
        }
        if (finMus == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaPol;
        }
        if (finMus == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finMus == LaureateOrFinalist.Laureat && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaSzt;
        }
        if (finSzt == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaPol;
        }
        if (finSzt == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaAng;
        }
        if (finSzt == LaureateOrFinalist.Laureat && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMuz;
        }
        if (finPol == LaureateOrFinalist.Brak && finAng == LaureateOrFinalist.Brak
                && finMus == LaureateOrFinalist.Brak && finSzt == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktyMusic + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsArt(round(punkty));
        studentToUpdate.setPunktyOlimpijskieArtystyczna(round(punkty_fin));


        return studentRepository.save(studentToUpdate);
    }

    public Student addPointsSport(Student student) {

        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);

        Klasa klasa1 = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Sportowa)).findFirst().get();

        double wagaMat = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Matematyka)).findFirst()
                .get().getWartosc();
        double wagaBio = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Biologia)).findFirst()
                .get().getWartosc();
        double wagaWF = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.WF)).findFirst()
                .get().getWartosc();

        String matGrade = studentToUpdate.getGrades().getMathGrade();
        String bioGrade = studentToUpdate.getGrades().getBiologyGrade();
        String wfGrade = studentToUpdate.getGrades().getPhysicalEducationGrade();

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finMat = studentToUpdate.getOlympiads().getMathOlympiad();
        LaureateOrFinalist finBio = studentToUpdate.getOlympiads().getEnglishOlympiad();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();
        double punkty_fin = 0.0;
        double punktySport = 0.0;
        double pointsFromExams = 0.0;
        double punkty = 0.0;

        punktySport = wagaMat * Double.parseDouble(matGrade)
                + wagaBio * Double.parseDouble(bioGrade)
                + wagaWF * Double.parseDouble(wfGrade);

        pointsFromExams = Double.parseDouble(mathExam) * Double.parseDouble(wagaMathExam)
                + Double.parseDouble(polExam) * Double.parseDouble(wagaPolExam)
                + Double.parseDouble(engExam) * Double.parseDouble(wagaEngExam);

        if (finMat == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finBio == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaMat;
        }
        if (finBio == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaBio;
        }
        if (finMat == LaureateOrFinalist.Finalista && finBio == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaMat + wagaBio);
        }


        if (finMat == LaureateOrFinalist.Laureat && finBio == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaBio;
        }
        if (finBio == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaMat;
        }
        if (finMat == LaureateOrFinalist.Brak && finBio == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktySport + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsS(round(punkty));
        studentToUpdate.setPunktyOlimpijskieSportowa(round(punkty_fin));


        return studentRepository.save(studentToUpdate);
    }

    public Student addPointsFizChemFran(Student student) {

        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);

        Klasa klasa1 = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.FizChemFranc)).findFirst().get();

        double wagaFiz = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Fizyka)).findFirst()
                .get().getWartosc();
        double wagaChem = klasa1.getWeightOfGrade().stream().filter(w ->
                        w.getSubject().equals(Subject.Chemia)).findFirst()
                .get().getWartosc();
        double wagaFrac = klasa1.getWeightOfGrade().stream().filter(w ->
                w.getSubject().equals(Subject.Francuski)).findFirst().get().getWartosc();


        String fizGrade = studentToUpdate.getGrades().getPhysicsGrade();
        String chemGrade = studentToUpdate.getGrades().getChemistryGrade();

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finFiz = studentToUpdate.getOlympiads().getPhysicsOlympiad();
        LaureateOrFinalist finChem = studentToUpdate.getOlympiads().getChemistryOlympiad();
        LaureateOrFinalist finFranc = studentToUpdate.getOlympiads().getFrenchOlympiad();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();
        double punkty_fin = 0.0;
        double punktyFizChemFran = 0.0;
        double pointsFromExams = 0.0;
        double punkty = 0.0;


        punktyFizChemFran = wagaFiz * Double.parseDouble(fizGrade)
                + wagaChem * Double.parseDouble(chemGrade)
                + wagaFrac * (Double.parseDouble(fizGrade) + Double.parseDouble(chemGrade));

        pointsFromExams = Double.parseDouble(mathExam) * Double.parseDouble(wagaMathExam)
                + Double.parseDouble(polExam) * Double.parseDouble(wagaPolExam)
                + Double.parseDouble(engExam) * Double.parseDouble(wagaEngExam);

        if (finFiz == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finChem == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finFranc == LaureateOrFinalist.Laureat) {
            punkty_fin = waga_lau * 1000;
        }
        if (finFiz == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaFiz;
        }
        if (finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaChem;
        }
        if (finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * wagaFrac;
        }
        if (finFiz == LaureateOrFinalist.Finalista && finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaFiz + wagaChem);
        }
        if (finFiz == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaFiz + wagaFrac);
        }
        if (finChem == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaChem + wagaFrac);
        }
        if (finFiz == LaureateOrFinalist.Finalista && finChem == LaureateOrFinalist.Finalista
                && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = 6 * waga_fin * (wagaFiz + wagaChem + wagaFrac);
        }

        if (finFiz == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaChem;
        }
        if (finFiz == LaureateOrFinalist.Laureat && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFrac;
        }
        if (finChem == LaureateOrFinalist.Laureat && finFiz == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFiz;
        }
        if (finChem == LaureateOrFinalist.Laureat && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFrac;
        }
        if (finFranc == LaureateOrFinalist.Laureat && finFiz == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaFiz;
        }
        if (finFranc == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * wagaChem;
        }
        if (finFiz == LaureateOrFinalist.Brak && finChem == LaureateOrFinalist.Brak && finFranc == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktyFizChemFran + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsFIZ(round(punkty));
        studentToUpdate.setPunktyOlimpijskieFizChemFranc(round(punkty_fin));

        return studentRepository.save(studentToUpdate);
    }

//    public Student classification(Student studentToUpdate) {
//
//
//        Klasa klasaMatGeoInf = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass() == NameOfClass.MatGeoInf).findFirst().get();
//
//        Klasa klasaPol = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass().equals(NameOfClass.Humanistyczna)).findFirst().get();
//
//        Klasa klasaMatAngNiem = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass().equals(NameOfClass.MatAngNiem)).findFirst().get();
//
//        Klasa klasaBiolChem = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass().equals(NameOfClass.BiolChem)).findFirst().get();
//
//        Klasa klasaSportowa = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass().equals(NameOfClass.Sportowa)).findFirst().get();
//
//        Klasa klasaMuzyczna = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass().equals(NameOfClass.Muzyczna)).findFirst().get();
//
//        Klasa klasaAktorska = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass().equals(NameOfClass.Aktorska)).findFirst().get();
//
//
//        LaureateOrFinalist lauMat = studentToUpdate.getOlympiads().getMathOlympiad();
//        LaureateOrFinalist lauGeo = studentToUpdate.getOlympiads().getGeographyOlympiad();
//        LaureateOrFinalist lauInf = studentToUpdate.getOlympiads().getITOlympiad();
//        LaureateOrFinalist lauAng = studentToUpdate.getOlympiads().getEnglishOlympiad();
//        LaureateOrFinalist lauNiem = studentToUpdate.getOlympiads().getGermanOlympiad();
//        String lauPol = studentToUpdate.getOlympiads().getPolishOlympiad().getLabel();
//        String lauHist = studentToUpdate.getOlympiads().getHistoryOlympiad().getLabel();
//        String lauWOS = studentToUpdate.getOlympiads().getCivicsOlympiad().getLabel();
//        String lauBio = studentToUpdate.getOlympiads().getBiologyOlympiad().getLabel();
//        String lauChem = studentToUpdate.getOlympiads().getChemistryOlympiad().getLabel();
//        String lauMuzHist = studentToUpdate.getOlympiads().getHistoryOfMusicOlympiad().getLabel();
//        String lauItal = studentToUpdate.getOlympiads().getItalianOlympiad().getLabel();
//
//        String grMath = studentToUpdate.getGrades().getMathGrade();
//        String grGeo = studentToUpdate.getGrades().getGeographyGrade();
//        String grInf = studentToUpdate.getGrades().getITGrade();
//        String grAng = studentToUpdate.getGrades().getEnglishGrade();
//        String grNiem = studentToUpdate.getGrades().getOtherLanguageGrade();
//        String grWf = studentToUpdate.getGrades().getPhysicalEducationGrade();
//        String grBio = studentToUpdate.getGrades().getBiologyGrade();
//
////        String umSzLicz = String.valueOf(studentToUpdate.getExtraParameters().getFastCounting().equals("TAK"));
////        String umRozProb = String.valueOf(studentToUpdate.getExtraParameters().getTroubleshooting().equals("TAK"));
////        String umPrOtwTer = String.valueOf(studentToUpdate.getExtraParameters().getWorkInTheOpenGround().equals("TAK"));
////        String zaiTech = String.valueOf(studentToUpdate.getExtraParameters().getInterestInTechnology().equals("TAK"));
//
////        List<Olympiad> getOlimpiad = olympiadRepository.findAll();
//
//        if (lauMat == LaureateOrFinalist.Laureat) {
//            if (lauGeo == LaureateOrFinalist.Laureat || lauInf == LaureateOrFinalist.Laureat) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//            } else if (lauAng == LaureateOrFinalist.Laureat || lauNiem == LaureateOrFinalist.Laureat) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//            } else if (lauGeo == LaureateOrFinalist.Finalista || lauInf == LaureateOrFinalist.Finalista) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//            } else if (lauAng == LaureateOrFinalist.Finalista || lauNiem == LaureateOrFinalist.Finalista) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//            } else //(jest tylko laureatem z matematyki lub jest laureatem z więcej niż dwóch kierunkowych
//            //przedmiotów dla tej klasy )
//            {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//            }
//        } else if (lauMat == LaureateOrFinalist.Finalista) {
//            if (lauGeo == LaureateOrFinalist.Laureat || lauInf == LaureateOrFinalist.Laureat) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//            } else if (lauAng == LaureateOrFinalist.Laureat || lauNiem == LaureateOrFinalist.Laureat) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//            } else if (lauGeo == LaureateOrFinalist.Finalista || lauInf == LaureateOrFinalist.Finalista) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//            } else if (lauAng == LaureateOrFinalist.Finalista || lauNiem == LaureateOrFinalist.Finalista) {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//            } else //(Jest tylko finalistą) {
//                if (studentToUpdate.getPointsMatGeoInf() >= klasaMatGeoInf.getMinAmountOfPointsFromExams()) {
//                    if (grGeo.equals("1.0") || grInf.equals("1.0")) {
//                        if (studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
//                                || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK
//                                || studentToUpdate.getExtraParameters().getWorkInTheOpenGround() == Ability.TAK
//                                || studentToUpdate.getExtraParameters().getInterestInTechnology() == Ability.TAK) {
//                            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                        } else {
//                            //Student nie klasyfikuje się do tej klasy lub trafia na listę rezerwową.
//                            if (studentToUpdate.getPointsMAN() >= klasaMatAngNiem.getMinAmountOfPointsFromExams()) {
//                                if (grAng.equals("1.0") || grNiem.equals("1.0")) {
//                                    if (studentToUpdate.getExtraParameters().getLinguisticSkills() == Ability.TAK
//                                            || studentToUpdate.getExtraParameters().getLanguageCertificate() == Ability.TAK
//                                            || studentToUpdate.getExtraParameters().getQuickMemorization() == Ability.TAK
//                                            || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK) {
//                                        studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                                    } else {
//                                        if (studentToUpdate.getPointsS() >= klasaSportowa.getMinAmountOfPointsFromExams()) {
//                                            if (grWf.equals("1.0") || grBio.equals("1.0")) {
//                                                if (studentToUpdate.getExtraParameters().getSportSkills() == Ability.TAK
//                                                        || studentToUpdate.getExtraParameters().getExtremeSport() == Ability.TAK
//                                                        || studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
//                                                        || studentToUpdate.getExtraParameters().getBiologicalAndNaturalInterests() == Ability.TAK) {
//                                                    studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                                                }
//                                            } else if (!grWf.equals("1.0") || !grBio.equals("1.0")) {
//                                                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                                            }
//                                        } else if (studentToUpdate.getPointsS() < klasaSportowa.getMinAmountOfPointsFromExams()) {
//                                            if (grWf.equals("5.0") && grBio.equals("5.0")) {
//                                                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                                            } else if (grWf.equals("1.0") || grBio.equals("1.0")) {
//                                                studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
//                                            } else if (studentToUpdate.getExtraParameters().getSportSkills() == Ability.TAK
//                                                    || studentToUpdate.getExtraParameters().getExtremeSport() == Ability.TAK
//                                                    || studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
//                                                    || studentToUpdate.getExtraParameters().getBiologicalAndNaturalInterests() == Ability.TAK) {
//                                                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                                            } else {
//                                                studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
//                                            }
//                                        }
//                                    }
//                                } else if (!grAng.equals("1.0") && !grNiem.equals("1.0")) {
//                                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                                }
//                            } else if (studentToUpdate.getPointsMAN() < klasaMatAngNiem.getMinAmountOfPointsFromExams()) {
//                                if (grAng.equals("5.0") && grNiem.equals("5.0")) {
//                                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                                } else if (grAng.equals("1.0") || grNiem.equals("1.0")) {
//                                    studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
//                                } else if (studentToUpdate.getExtraParameters().getLinguisticSkills() == Ability.TAK
//                                        || studentToUpdate.getExtraParameters().getLanguageCertificate() == Ability.TAK
//                                        || studentToUpdate.getExtraParameters().getQuickMemorization() == Ability.TAK
//                                        || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK) {
//                                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                                } else {
//                                    studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
//                                }
//                            }
//                        }
//                    } else if (!grGeo.equals("1.0") && grInf.equals("1.0")) {
//                        studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                    }
//                } else if (studentToUpdate.getPointsMatGeoInf() < klasaMatGeoInf.getMinAmountOfPointsFromExams()) {
//                    if (grGeo.equals("5.0") && grInf.equals("5.0")) {
//                        studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                    } else if (grGeo.equals("1.0") || grInf.equals("1.0")) {
//                        studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
//                    } else {
//                        //Można zrobić, tak aby uczeń na tym poziomie musiał mieć wszystkie wymagane umiejętności
//                        //Czyli alternatywę zamienimy na koniunkcję.
//                        if (studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
//                                || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK
//                                || studentToUpdate.getExtraParameters().getWorkInTheOpenGround() == Ability.TAK
//                                || studentToUpdate.getExtraParameters().getInterestInTechnology() == Ability.TAK) {
//                            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                        } else {
//                            studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
//                        }
//                    }
//                }
//        }
//
//        //Zakładam, że jeżeli uczeń będzie miał tylko olimpiadę matematyczną lub będzie miał olimpiadę
//        //matematyczną i inną niż wyróżnione lub będzie innym finalistą poza wyróżnionymi
//        //to przydzieli go domyślnie do klasy matGeoInf.
//
//        // TODO: 18.02.2023 Co zrobić w wypadku kiedy w dwóch klasach jest ten sam przedmiot jako kierunkowy ?
//
////        if (lauMat.equals("Laureat") || lauGeo.equals("Laureat") || lauInf.equals("Laureat")) {
////            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
////        } else if (lauPol.equals("Laureat") || lauHist.equals("Laureat") || lauWOS.equals("Laureat")) {
////            studentToUpdate.setClassForStudent(String.valueOf(klasaPol.getNameOfClass()));
////        } else if (lauMat.equals("Laureat") || lauAng.equals("Laureat") || lauNiem.equals("Laureat")) {
////            studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
////        } else if (lauAng.equals("Laureat") || lauBio.equals("Laureat") || lauChem.equals("Laureat")) {
////            studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChem.getNameOfClass()));
////        } else if (lauMat.equals("Laureat") || lauBio.equals("Laureat")) {
////            studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
////        } else if (lauMat.equals("Laureat") || lauNiem.equals("Laureat") || lauMuzHist.equals("Laureat")) {
////            studentToUpdate.setClassForStudent(String.valueOf(klasaMuzyczna.getNameOfClass()));
////        } else if (lauAng.equals("Laureat") || lauItal.equals("Laureat") || lauMuzHist.equals("Laureat")) {
////            studentToUpdate.setClassForStudent(String.valueOf(klasaAktorska.getNameOfClass()));
////        }
//
//        return studentRepository.save(studentToUpdate);
//    }

    public Student updateStudentClass(Student student, NameOfClass name) {
        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
        if (studentToUpdate != null) {
            studentToUpdate.setClassForStudent(String.valueOf(name));
            return studentRepository.save(studentToUpdate);

            //TODO: Można spróbować dodać tutaj klasę i ustawić id studenta
        } else {
            return studentToUpdate;
        }
    }


//    public Student pointsOfStudent(Student student, Exam exam) {
//        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
//        double liczba = 0;
//        double powtorzeniaL = 0;
//        double powtorzeniaF = 0;
//        double punkty = 0;
//        double egzamin = 0;
//        double math = Double.parseDouble(studentToUpdate.getExams().getMath());
//        double polish = Double.parseDouble(studentToUpdate.getExams().getLanguagePolishResult());
//        double english = Double.parseDouble(studentToUpdate.getExams().getForeignLanguage());
//        if (studentToUpdate != null) {
//
//            egzamin = math * 0.8 + polish * 0.5 + english * 0.5;
//
//            Olympiad stu = studentToUpdate.getOlympiads();
//            boolean stuMathL = stu.getMathOlympiad().getLabel().equals("Laureat");
//            boolean stuMathF = stu.getMathOlympiad().getLabel().equals("Finalista");
//            boolean stuItL = stu.getITOlympiad().getLabel().equals("Laureat");
//            boolean stuItF = stu.getITOlympiad().getLabel().equals("Finalista");
//            boolean stuPolL = stu.getPolishOlympiad().getLabel().equals("Laureat");
//            boolean stuPolF = stu.getPolishOlympiad().getLabel().equals("Finalista");
//            boolean stuEngL = stu.getEnglishOlympiad().getLabel().equals("Laureat");
//            boolean stuEngF = stu.getEnglishOlympiad().getLabel().equals("Finalista");
//            boolean stuGerL = stu.getGermanOlympiad().getLabel().equals("Laureat");
//            boolean stuGerF = stu.getGermanOlympiad().getLabel().equals("Finalista");
//            boolean stuFreL = stu.getFrenchOlympiad().getLabel().equals("Laureat");
//            boolean stuFreF = stu.getFrenchOlympiad().getLabel().equals("Finalista");
//            boolean stuSpaL = stu.getSpanishOlympiad().getLabel().equals("Laureat");
//            boolean stuSpaF = stu.getSpanishOlympiad().getLabel().equals("Finalista");
//            boolean stuItaL = stu.getItalianOlympiad().getLabel().equals("Laureat");
//            boolean stuItaF = stu.getItalianOlympiad().getLabel().equals("Finalista");
//            boolean stuChemL = stu.getChemistryOlympiad().getLabel().equals("Laureat");
//            boolean stuChemF = stu.getChemistryOlympiad().getLabel().equals("Finalista");
//            boolean stuPhyL = stu.getPhysicsOlympiad().getLabel().equals("Laureat");
//            boolean stuPhyF = stu.getPhysicsOlympiad().getLabel().equals("Finalista");
//            boolean stuBioL = stu.getBiologyOlympiad().getLabel().equals("Laureat");
//            boolean stuBioF = stu.getBiologyOlympiad().getLabel().equals("Finalista");
//            boolean stuGeoL = stu.getGeographyOlympiad().getLabel().equals("Laureat");
//            boolean stuGeoF = stu.getGeographyOlympiad().getLabel().equals("Finalista");
//            boolean stuWosL = stu.getCivicsOlympiad().getLabel().equals("Laureat");
//            boolean stuWosF = stu.getCivicsOlympiad().getLabel().equals("Finalista");
//            boolean stuHisL = stu.getHistoryOlympiad().getLabel().equals("Laureat");
//            boolean stuHisF = stu.getHistoryOlympiad().getLabel().equals("Finalista");
//            boolean stuHisML = stu.getHistoryOfMusicOlympiad().getLabel().equals("Laureat");
//            boolean stuHisMF = stu.getHistoryOfMusicOlympiad().getLabel().equals("Finalista");
//            boolean stuHisAL = stu.getHistoryOfArtOlympiad().getLabel().equals("Laureat");
//            boolean stuHisAF = stu.getHistoryOfArtOlympiad().getLabel().equals("Finalista");
////Na potrzeby pracy zakładam, że uczeń nie może mieć więcej niż dwóch olimpiad
//// Zrobić warunek na max 2 olimpiady
//            //Matematyka
//            if (stuMathL) {
//                liczba = 100 * 0.20;
//                if ((stuMathL && stuPolL) || (stuMathL && stuEngL) || (stuMathL && stuItL) || (stuMathL && stuFreL)
//                        || (stuMathL && stuGerL) || (stuMathL && stuSpaL) || (stuMathL && stuItaL) || (stuMathL && stuChemL)
//                        || (stuMathL && stuPhyL) || (stuMathL && stuBioL) || (stuMathL && stuGeoL) || (stuMathL && stuWosL)
//                        || (stuMathL && stuHisL) || (stuMathL && stuHisML) || (stuMathL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuMathL && stuPolF) || (stuMathL && stuEngF) || (stuMathL && stuItF) || (stuMathL && stuFreF)
//                        || (stuMathL && stuGerF) || (stuMathL && stuSpaF) || (stuMathL && stuItaF) || (stuMathL && stuChemF)
//                        || (stuMathL && stuPhyF) || (stuMathL && stuBioF) || (stuMathL && stuGeoF) || (stuMathL && stuWosF)
//                        || (stuMathL && stuHisF) || (stuMathL && stuHisMF) || (stuMathL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuMathF) {
//                liczba = 100 * 0.10;
//                if ((stuMathF && stuPolF) || (stuMathF && stuEngF) || (stuMathF && stuItF) || (stuMathF && stuFreF)
//                        || (stuMathF && stuGerF) || (stuMathF && stuSpaF) || (stuMathF && stuItaF) || (stuMathF && stuChemF)
//                        || (stuMathF && stuPhyF) || (stuMathF && stuBioF) || (stuMathF && stuGeoF) || (stuMathF && stuWosF)
//                        || (stuMathF && stuHisF) || (stuMathF && stuHisMF) || (stuMathF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuMathF && stuPolL) || (stuMathF && stuEngL) || (stuMathF && stuItL) || (stuMathF && stuFreL)
//                        || (stuMathF && stuGerL) || (stuMathF && stuSpaL) || (stuMathF && stuItaL) || (stuMathF && stuChemL)
//                        || (stuMathF && stuPhyL) || (stuMathF && stuBioL) || (stuMathF && stuGeoL) || (stuMathF && stuWosL)
//                        || (stuMathF && stuHisL) || (stuMathF && stuHisML) || (stuMathF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//            //Polski
//            if (stuPolL) {
//                liczba = 100 * 0.20;
//                if ((stuPolL && stuMathL) || (stuPolL && stuEngL) || (stuPolL && stuItL) || (stuPolL && stuFreL)
//                        || (stuPolL && stuGerL) || (stuPolL && stuSpaL) || (stuPolL && stuItaL) || (stuPolL && stuChemL)
//                        || (stuPolL && stuPhyL) || (stuPolL && stuBioL) || (stuPolL && stuGeoL) || (stuPolL && stuWosL)
//                        || (stuPolL && stuHisL) || (stuPolL && stuHisML) || (stuPolL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuPolL && stuMathF) || (stuPolL && stuEngF) || (stuPolL && stuItF) || (stuPolL && stuFreF)
//                        || (stuPolL && stuGerF) || (stuPolL && stuSpaF) || (stuPolL && stuItaF) || (stuPolL && stuChemF)
//                        || (stuPolL && stuPhyF) || (stuPolL && stuBioF) || (stuPolL && stuGeoF) || (stuPolL && stuWosF)
//                        || (stuPolL && stuHisF) || (stuPolL && stuHisMF) || (stuPolL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuPolF) {
//                liczba = 100 * 0.10;
//                if ((stuPolF && stuMathF) || (stuPolF && stuEngF) || (stuPolF && stuItF) || (stuPolF && stuFreF)
//                        || (stuPolF && stuGerF) || (stuPolF && stuSpaF) || (stuPolF && stuItaF) || (stuPolF && stuChemF)
//                        || (stuPolF && stuPhyF) || (stuPolF && stuBioF) || (stuPolF && stuGeoF) || (stuPolF && stuWosF)
//                        || (stuPolF && stuHisF) || (stuPolF && stuHisMF) || (stuPolF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuPolF && stuMathL) || (stuPolF && stuEngL) || (stuPolF && stuItL) || (stuPolF && stuFreL)
//                        || (stuPolF && stuGerL) || (stuPolF && stuSpaL) || (stuPolF && stuItaL) || (stuPolF && stuChemL)
//                        || (stuPolF && stuPhyL) || (stuPolF && stuBioL) || (stuPolF && stuGeoL) || (stuPolF && stuWosL)
//                        || (stuPolF && stuHisL) || (stuPolF && stuHisML) || (stuPolF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Angielski
//            if (stuEngL) {
//                liczba = 100 * 0.20;
//                if ((stuEngL && stuMathL) || (stuEngL && stuPolL) || (stuEngL && stuItL) || (stuEngL && stuFreL)
//                        || (stuEngL && stuGerL) || (stuEngL && stuSpaL) || (stuEngL && stuItaL) || (stuEngL && stuChemL)
//                        || (stuEngL && stuPhyL) || (stuEngL && stuBioL) || (stuEngL && stuGeoL) || (stuEngL && stuWosL)
//                        || (stuEngL && stuHisL) || (stuEngL && stuHisML) || (stuEngL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuEngL && stuMathF) || (stuEngL && stuPolF) || (stuEngL && stuItF) || (stuEngL && stuFreF)
//                        || (stuEngL && stuGerF) || (stuEngL && stuSpaF) || (stuEngL && stuItaF) || (stuEngL && stuChemF)
//                        || (stuEngL && stuPhyF) || (stuEngL && stuBioF) || (stuEngL && stuGeoF) || (stuEngL && stuWosF)
//                        || (stuEngL && stuHisF) || (stuEngL && stuHisMF) || (stuEngL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuEngF) {
//                liczba = 100 * 0.10;
//                if ((stuEngF && stuMathF) || (stuEngF && stuPolF) || (stuEngF && stuItF) || (stuEngF && stuFreF)
//                        || (stuEngF && stuGerF) || (stuEngF && stuSpaF) || (stuEngF && stuItaF) || (stuEngF && stuChemF)
//                        || (stuEngF && stuPhyF) || (stuEngF && stuBioF) || (stuEngF && stuGeoF) || (stuEngF && stuWosF)
//                        || (stuEngF && stuHisF) || (stuEngF && stuHisMF) || (stuEngF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuEngF && stuMathL) || (stuEngF && stuPolL) || (stuEngF && stuItL) || (stuEngF && stuFreL)
//                        || (stuEngF && stuGerL) || (stuEngF && stuSpaL) || (stuEngF && stuItaL) || (stuEngF && stuChemL)
//                        || (stuEngF && stuPhyL) || (stuEngF && stuBioL) || (stuEngF && stuGeoL) || (stuEngF && stuWosL)
//                        || (stuEngF && stuHisL) || (stuEngF && stuHisML) || (stuEngF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Niemiecki
//            if (stuGerL) {
//                liczba = 100 * 0.20;
//                if ((stuGerL && stuMathL) || (stuGerL && stuPolL) || (stuGerL && stuItL) || (stuGerL && stuFreL)
//                        || (stuGerL && stuEngL) || (stuGerL && stuSpaL) || (stuGerL && stuItaL) || (stuGerL && stuChemL)
//                        || (stuGerL && stuPhyL) || (stuGerL && stuBioL) || (stuGerL && stuGeoL) || (stuGerL && stuWosL)
//                        || (stuGerL && stuHisL) || (stuGerL && stuHisML) || (stuGerL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuGerL && stuMathF) || (stuGerL && stuPolF) || (stuGerL && stuItF) || (stuGerL && stuFreF)
//                        || (stuGerL && stuEngF) || (stuGerL && stuSpaF) || (stuGerL && stuItaF) || (stuGerL && stuChemF)
//                        || (stuGerL && stuPhyF) || (stuGerL && stuBioF) || (stuGerL && stuGeoF) || (stuGerL && stuWosF)
//                        || (stuGerL && stuHisF) || (stuGerL && stuHisMF) || (stuGerL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuGerF) {
//                liczba = 100 * 0.10;
//                if ((stuGerF && stuMathF) || (stuGerF && stuPolF) || (stuGerF && stuItF) || (stuGerF && stuFreF)
//                        || (stuGerF && stuEngF) || (stuGerF && stuSpaF) || (stuGerF && stuItaF) || (stuGerF && stuChemF)
//                        || (stuGerF && stuPhyF) || (stuGerF && stuBioF) || (stuGerF && stuGeoF) || (stuGerF && stuWosF)
//                        || (stuGerF && stuHisF) || (stuGerF && stuHisMF) || (stuGerF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuGerF && stuMathL) || (stuGerF && stuPolL) || (stuGerF && stuItL) || (stuGerF && stuFreL)
//                        || (stuGerF && stuEngL) || (stuGerF && stuSpaL) || (stuGerF && stuItaL) || (stuGerF && stuChemL)
//                        || (stuGerF && stuPhyL) || (stuGerF && stuBioL) || (stuGerF && stuGeoL) || (stuGerF && stuWosL)
//                        || (stuGerF && stuHisL) || (stuGerF && stuHisML) || (stuGerF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Hiszpański
//            if (stuSpaL) {
//                liczba = 100 * 0.20;
//                if ((stuSpaL && stuMathL) || (stuSpaL && stuPolL) || (stuSpaL && stuItL) || (stuSpaL && stuFreL)
//                        || (stuSpaL && stuEngL) || (stuSpaL && stuGerL) || (stuSpaL && stuItaL) || (stuSpaL && stuChemL)
//                        || (stuSpaL && stuPhyL) || (stuSpaL && stuBioL) || (stuSpaL && stuGeoL) || (stuSpaL && stuWosL)
//                        || (stuSpaL && stuHisL) || (stuSpaL && stuHisML) || (stuSpaL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuSpaL && stuMathF) || (stuSpaL && stuPolF) || (stuSpaL && stuItF) || (stuSpaL && stuFreF)
//                        || (stuSpaL && stuEngF) || (stuSpaL && stuGerF) || (stuSpaL && stuItaF) || (stuSpaL && stuChemF)
//                        || (stuSpaL && stuPhyF) || (stuSpaL && stuBioF) || (stuSpaL && stuGeoF) || (stuSpaL && stuWosF)
//                        || (stuSpaL && stuHisF) || (stuSpaL && stuHisMF) || (stuSpaL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuSpaF) {
//                liczba = 100 * 0.10;
//                if ((stuSpaF && stuMathF) || (stuSpaF && stuPolF) || (stuSpaF && stuItF) || (stuSpaF && stuFreF)
//                        || (stuSpaF && stuEngF) || (stuSpaF && stuGerF) || (stuSpaF && stuItaF) || (stuSpaF && stuChemF)
//                        || (stuSpaF && stuPhyF) || (stuSpaF && stuBioF) || (stuSpaF && stuGeoF) || (stuSpaF && stuWosF)
//                        || (stuSpaF && stuHisF) || (stuSpaF && stuHisMF) || (stuSpaF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuSpaF && stuMathL) || (stuSpaF && stuPolL) || (stuSpaF && stuItL) || (stuSpaF && stuFreL)
//                        || (stuSpaF && stuEngL) || (stuSpaF && stuGerL) || (stuSpaF && stuItaL) || (stuSpaF && stuChemL)
//                        || (stuSpaF && stuPhyL) || (stuSpaF && stuBioL) || (stuSpaF && stuGeoL) || (stuSpaF && stuWosL)
//                        || (stuSpaF && stuHisL) || (stuSpaF && stuHisML) || (stuSpaF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Francuski
//            if (stuFreL) {
//                liczba = 100 * 0.20;
//                if ((stuFreL && stuMathL) || (stuFreL && stuPolL) || (stuFreL && stuItL) || (stuFreL && stuSpaL)
//                        || (stuFreL && stuEngL) || (stuFreL && stuGerL) || (stuFreL && stuItaL) || (stuFreL && stuChemL)
//                        || (stuFreL && stuPhyL) || (stuFreL && stuBioL) || (stuFreL && stuGeoL) || (stuFreL && stuWosL)
//                        || (stuFreL && stuHisL) || (stuFreL && stuHisML) || (stuFreL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuFreL && stuMathF) || (stuFreL && stuPolF) || (stuFreL && stuItF) || (stuFreL && stuSpaF)
//                        || (stuFreL && stuEngF) || (stuFreL && stuGerF) || (stuFreL && stuItaF) || (stuFreL && stuChemF)
//                        || (stuFreL && stuPhyF) || (stuFreL && stuBioF) || (stuFreL && stuGeoF) || (stuFreL && stuWosF)
//                        || (stuFreL && stuHisF) || (stuFreL && stuHisMF) || (stuFreL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuFreF) {
//                liczba = 100 * 0.10;
//                if ((stuFreF && stuMathF) || (stuFreF && stuPolF) || (stuFreF && stuItF) || (stuFreF && stuSpaF)
//                        || (stuFreF && stuEngF) || (stuFreF && stuGerF) || (stuFreF && stuItaF) || (stuFreF && stuChemF)
//                        || (stuFreF && stuPhyF) || (stuFreF && stuBioF) || (stuFreF && stuGeoF) || (stuFreF && stuWosF)
//                        || (stuFreF && stuHisF) || (stuFreF && stuHisMF) || (stuFreF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuFreF && stuMathL) || (stuFreF && stuPolL) || (stuFreF && stuItL) || (stuFreF && stuSpaL)
//                        || (stuFreF && stuEngL) || (stuFreF && stuGerL) || (stuFreF && stuItaL) || (stuFreF && stuChemL)
//                        || (stuFreF && stuPhyL) || (stuFreF && stuBioL) || (stuFreF && stuGeoL) || (stuFreF && stuWosL)
//                        || (stuFreF && stuHisL) || (stuFreF && stuHisML) || (stuFreF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Włoski
//            if (stuItaL) {
//                liczba = 100 * 0.20;
//                if ((stuItaL && stuMathL) || (stuItaL && stuPolL) || (stuItaL && stuItL) || (stuItaL && stuSpaL)
//                        || (stuItaL && stuEngL) || (stuItaL && stuGerL) || (stuItaL && stuFreL) || (stuItaL && stuChemL)
//                        || (stuItaL && stuPhyL) || (stuItaL && stuBioL) || (stuItaL && stuGeoL) || (stuItaL && stuWosL)
//                        || (stuItaL && stuHisL) || (stuItaL && stuHisML) || (stuItaL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuItaL && stuMathF) || (stuItaL && stuPolF) || (stuItaL && stuItF) || (stuItaL && stuSpaF)
//                        || (stuItaL && stuEngF) || (stuItaL && stuGerF) || (stuItaL && stuFreF) || (stuItaL && stuChemF)
//                        || (stuItaL && stuPhyF) || (stuItaL && stuBioF) || (stuItaL && stuGeoF) || (stuItaL && stuWosF)
//                        || (stuItaL && stuHisF) || (stuItaL && stuHisMF) || (stuItaL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuItaF) {
//                liczba = 100 * 0.10;
//                if ((stuItaF && stuMathF) || (stuItaF && stuPolF) || (stuItaF && stuItF) || (stuItaF && stuSpaF)
//                        || (stuItaF && stuEngF) || (stuItaF && stuGerF) || (stuItaF && stuFreF) || (stuItaF && stuChemF)
//                        || (stuItaF && stuPhyF) || (stuItaF && stuBioF) || (stuItaF && stuGeoF) || (stuItaF && stuWosF)
//                        || (stuItaF && stuHisF) || (stuItaF && stuHisMF) || (stuItaF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuItaF && stuMathL) || (stuItaF && stuPolL) || (stuItaF && stuItL) || (stuItaF && stuSpaL)
//                        || (stuItaF && stuEngL) || (stuItaF && stuGerL) || (stuItaF && stuFreL) || (stuItaF && stuChemL)
//                        || (stuItaF && stuPhyL) || (stuItaF && stuBioL) || (stuItaF && stuGeoL) || (stuItaF && stuWosL)
//                        || (stuItaF && stuHisL) || (stuItaF && stuHisML) || (stuItaF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Informatyka
//            if (stuItL) {
//                liczba = 100 * 0.20;
//                if ((stuItL && stuMathL) || (stuItL && stuPolL) || (stuItL && stuItaL) || (stuItL && stuSpaL)
//                        || (stuItL && stuEngL) || (stuItL && stuGerL) || (stuItL && stuFreL) || (stuItL && stuChemL)
//                        || (stuItL && stuPhyL) || (stuItL && stuBioL) || (stuItL && stuGeoL) || (stuItL && stuWosL)
//                        || (stuItL && stuHisL) || (stuItL && stuHisML) || (stuItL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuItL && stuMathF) || (stuItL && stuPolF) || (stuItL && stuItaF) || (stuItL && stuSpaF)
//                        || (stuItL && stuEngF) || (stuItL && stuGerF) || (stuItL && stuFreF) || (stuItL && stuChemF)
//                        || (stuItL && stuPhyF) || (stuItL && stuBioF) || (stuItL && stuGeoF) || (stuItL && stuWosF)
//                        || (stuItL && stuHisF) || (stuItL && stuHisMF) || (stuItL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuItF) {
//                liczba = 100 * 0.10;
//                if ((stuItF && stuMathF) || (stuItF && stuPolF) || (stuItF && stuItaF) || (stuItF && stuSpaF)
//                        || (stuItF && stuEngF) || (stuItF && stuGerF) || (stuItF && stuFreF) || (stuItF && stuChemF)
//                        || (stuItF && stuPhyF) || (stuItF && stuBioF) || (stuItF && stuGeoF) || (stuItF && stuWosF)
//                        || (stuItF && stuHisF) || (stuItF && stuHisMF) || (stuItF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuItF && stuMathL) || (stuItF && stuPolL) || (stuItF && stuItaL) || (stuItF && stuSpaL)
//                        || (stuItF && stuEngL) || (stuItF && stuGerL) || (stuItF && stuFreL) || (stuItF && stuChemL)
//                        || (stuItF && stuPhyL) || (stuItF && stuBioL) || (stuItF && stuGeoL) || (stuItF && stuWosL)
//                        || (stuItF && stuHisL) || (stuItF && stuHisML) || (stuItF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //chemia
//            if (stuChemL) {
//                liczba = 100 * 0.20;
//                if ((stuChemL && stuMathL) || (stuChemL && stuPolL) || (stuChemL && stuItaL) || (stuChemL && stuSpaL)
//                        || (stuChemL && stuEngL) || (stuChemL && stuGerL) || (stuChemL && stuFreL) || (stuChemL && stuItL)
//                        || (stuChemL && stuPhyL) || (stuChemL && stuBioL) || (stuChemL && stuGeoL) || (stuChemL && stuWosL)
//                        || (stuChemL && stuHisL) || (stuChemL && stuHisML) || (stuChemL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuChemL && stuMathF) || (stuChemL && stuPolF) || (stuChemL && stuItaF) || (stuChemL && stuSpaF)
//                        || (stuChemL && stuEngF) || (stuChemL && stuGerF) || (stuChemL && stuFreF) || (stuChemL && stuItF)
//                        || (stuChemL && stuPhyF) || (stuChemL && stuBioF) || (stuChemL && stuGeoF) || (stuChemL && stuWosF)
//                        || (stuChemL && stuHisF) || (stuChemL && stuHisMF) || (stuChemL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuChemF) {
//                liczba = 100 * 0.10;
//                if ((stuChemF && stuMathF) || (stuChemF && stuPolF) || (stuChemF && stuItaF) || (stuChemF && stuSpaF)
//                        || (stuChemF && stuEngF) || (stuChemF && stuGerF) || (stuChemF && stuFreF) || (stuChemF && stuItF)
//                        || (stuChemF && stuPhyF) || (stuChemF && stuBioF) || (stuChemF && stuGeoF) || (stuChemF && stuWosF)
//                        || (stuChemF && stuHisF) || (stuChemF && stuHisMF) || (stuChemF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuChemF && stuMathL) || (stuChemF && stuPolL) || (stuChemF && stuItaL) || (stuChemF && stuSpaL)
//                        || (stuChemF && stuEngL) || (stuChemF && stuGerL) || (stuChemF && stuFreL) || (stuChemF && stuItL)
//                        || (stuChemF && stuPhyL) || (stuChemF && stuBioL) || (stuChemF && stuGeoL) || (stuChemF && stuWosL)
//                        || (stuChemF && stuHisL) || (stuChemF && stuHisML) || (stuChemF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Fizyka
//            if (stuPhyL) {
//                liczba = 100 * 0.20;
//                if ((stuPhyL && stuMathL) || (stuPhyL && stuPolL) || (stuPhyL && stuItaL) || (stuPhyL && stuSpaL)
//                        || (stuPhyL && stuEngL) || (stuPhyL && stuGerL) || (stuPhyL && stuFreL) || (stuPhyL && stuItL)
//                        || (stuPhyL && stuChemL) || (stuPhyL && stuBioL) || (stuPhyL && stuGeoL) || (stuPhyL && stuWosL)
//                        || (stuPhyL && stuHisL) || (stuPhyL && stuHisML) || (stuPhyL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuPhyL && stuMathF) || (stuPhyL && stuPolF) || (stuPhyL && stuItaF) || (stuPhyL && stuSpaF)
//                        || (stuPhyL && stuEngF) || (stuPhyL && stuGerF) || (stuPhyL && stuFreF) || (stuPhyL && stuItF)
//                        || (stuPhyL && stuChemF) || (stuPhyL && stuBioF) || (stuPhyL && stuGeoF) || (stuPhyL && stuWosF)
//                        || (stuPhyL && stuHisF) || (stuPhyL && stuHisMF) || (stuPhyL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuPhyF) {
//                liczba = 100 * 0.10;
//                if ((stuPhyF && stuMathF) || (stuPhyF && stuPolF) || (stuPhyF && stuItaF) || (stuPhyF && stuSpaF)
//                        || (stuPhyF && stuEngF) || (stuPhyF && stuGerF) || (stuPhyF && stuFreF) || (stuPhyF && stuItF)
//                        || (stuPhyF && stuChemF) || (stuPhyF && stuBioF) || (stuPhyF && stuGeoF) || (stuPhyF && stuWosF)
//                        || (stuPhyF && stuHisF) || (stuPhyF && stuHisMF) || (stuPhyF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuPhyF && stuMathL) || (stuPhyF && stuPolL) || (stuPhyF && stuItaL) || (stuPhyF && stuSpaL)
//                        || (stuPhyF && stuEngL) || (stuPhyF && stuGerL) || (stuPhyF && stuFreL) || (stuPhyF && stuItL)
//                        || (stuPhyF && stuChemL) || (stuPhyF && stuBioL) || (stuPhyF && stuGeoL) || (stuPhyF && stuWosL)
//                        || (stuPhyF && stuHisL) || (stuPhyF && stuHisML) || (stuPhyF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//            //Biologia
//            if (stuBioL) {
//                liczba = 100 * 0.20;
//                if ((stuBioL && stuMathL) || (stuBioL && stuPolL) || (stuBioL && stuItaL) || (stuBioL && stuSpaL)
//                        || (stuBioL && stuEngL) || (stuBioL && stuGerL) || (stuBioL && stuFreL) || (stuBioL && stuItL)
//                        || (stuBioL && stuChemL) || (stuBioL && stuPhyL) || (stuBioL && stuGeoL) || (stuBioL && stuWosL)
//                        || (stuBioL && stuHisL) || (stuBioL && stuHisML) || (stuBioL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuBioL && stuMathF) || (stuBioL && stuPolF) || (stuBioL && stuItaF) || (stuBioL && stuSpaF)
//                        || (stuBioL && stuEngF) || (stuBioL && stuGerF) || (stuBioL && stuFreF) || (stuBioL && stuItF)
//                        || (stuBioL && stuChemF) || (stuBioL && stuPhyF) || (stuBioL && stuGeoF) || (stuBioL && stuWosF)
//                        || (stuBioL && stuHisF) || (stuBioL && stuHisMF) || (stuBioL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuBioF) {
//                liczba = 100 * 0.10;
//                if ((stuBioF && stuMathF) || (stuBioF && stuPolF) || (stuBioF && stuItaF) || (stuBioF && stuSpaF)
//                        || (stuBioF && stuEngF) || (stuBioF && stuGerF) || (stuBioF && stuFreF) || (stuBioF && stuItF)
//                        || (stuBioF && stuChemF) || (stuBioF && stuPhyF) || (stuBioF && stuGeoF) || (stuBioF && stuWosF)
//                        || (stuBioF && stuHisF) || (stuBioF && stuHisMF) || (stuBioF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuBioF && stuMathL) || (stuBioF && stuPolL) || (stuBioF && stuItaL) || (stuBioF && stuSpaL)
//                        || (stuBioF && stuEngL) || (stuBioF && stuGerL) || (stuBioF && stuFreL) || (stuBioF && stuItL)
//                        || (stuBioF && stuChemL) || (stuBioF && stuPhyL) || (stuBioF && stuGeoL) || (stuBioF && stuWosL)
//                        || (stuBioF && stuHisL) || (stuBioF && stuHisML) || (stuBioF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//            //Geografia
//            if (stuGeoL) {
//                liczba = 100 * 0.20;
//                if ((stuGeoL && stuMathL) || (stuGeoL && stuPolL) || (stuGeoL && stuItaL) || (stuGeoL && stuSpaL)
//                        || (stuGeoL && stuEngL) || (stuGeoL && stuGerL) || (stuGeoL && stuFreL) || (stuGeoL && stuItL)
//                        || (stuGeoL && stuChemL) || (stuGeoL && stuPhyL) || (stuGeoL && stuBioL) || (stuGeoL && stuWosL)
//                        || (stuGeoL && stuHisL) || (stuGeoL && stuHisML) || (stuGeoL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuGeoL && stuMathF) || (stuGeoL && stuPolF) || (stuGeoL && stuItaF) || (stuGeoL && stuSpaF)
//                        || (stuGeoL && stuEngF) || (stuGeoL && stuGerF) || (stuGeoL && stuFreF) || (stuGeoL && stuItF)
//                        || (stuGeoL && stuChemF) || (stuGeoL && stuPhyF) || (stuGeoL && stuBioF) || (stuGeoL && stuWosF)
//                        || (stuGeoL && stuHisF) || (stuGeoL && stuHisMF) || (stuGeoL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuGeoF) {
//                liczba = 100 * 0.10;
//                if ((stuGeoF && stuMathF) || (stuGeoF && stuPolF) || (stuGeoF && stuItaF) || (stuGeoF && stuSpaF)
//                        || (stuGeoF && stuEngF) || (stuGeoF && stuGerF) || (stuGeoF && stuFreF) || (stuGeoF && stuItF)
//                        || (stuGeoF && stuChemF) || (stuGeoF && stuPhyF) || (stuGeoF && stuBioF) || (stuGeoF && stuWosF)
//                        || (stuGeoF && stuHisF) || (stuGeoF && stuHisMF) || (stuGeoF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuGeoF && stuMathL) || (stuGeoF && stuPolL) || (stuGeoF && stuItaL) || (stuGeoF && stuSpaL)
//                        || (stuGeoF && stuEngL) || (stuGeoF && stuGerL) || (stuGeoF && stuFreL) || (stuGeoF && stuItL)
//                        || (stuGeoF && stuChemL) || (stuGeoF && stuPhyL) || (stuGeoF && stuBioL) || (stuGeoF && stuWosL)
//                        || (stuGeoF && stuHisL) || (stuGeoF && stuHisML) || (stuGeoF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //WOS
//            if (stuWosL) {
//                liczba = 100 * 0.20;
//                if ((stuWosL && stuMathL) || (stuWosL && stuPolL) || (stuWosL && stuItaL) || (stuWosL && stuSpaL)
//                        || (stuWosL && stuEngL) || (stuWosL && stuGerL) || (stuWosL && stuFreL) || (stuWosL && stuItL)
//                        || (stuWosL && stuChemL) || (stuWosL && stuPhyL) || (stuWosL && stuBioL) || (stuWosL && stuGeoL)
//                        || (stuWosL && stuHisL) || (stuWosL && stuHisML) || (stuWosL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuWosL && stuMathF) || (stuWosL && stuPolF) || (stuWosL && stuItaF) || (stuWosL && stuSpaF)
//                        || (stuWosL && stuEngF) || (stuWosL && stuGerF) || (stuWosL && stuFreF) || (stuWosL && stuItF)
//                        || (stuWosL && stuChemF) || (stuWosL && stuPhyF) || (stuWosL && stuBioF) || (stuWosL && stuGeoF)
//                        || (stuWosL && stuHisF) || (stuWosL && stuHisMF) || (stuWosL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuWosF) {
//                liczba = 100 * 0.10;
//                if ((stuWosF && stuMathF) || (stuWosF && stuPolF) || (stuGeoF && stuItaF) || (stuWosF && stuSpaF)
//                        || (stuWosF && stuEngF) || (stuWosF && stuGerF) || (stuWosF && stuFreF) || (stuWosF && stuItF)
//                        || (stuWosF && stuChemF) || (stuWosF && stuPhyF) || (stuWosF && stuBioF) || (stuWosF && stuGeoF)
//                        || (stuWosF && stuHisF) || (stuWosF && stuHisMF) || (stuWosF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuWosF && stuMathL) || (stuWosF && stuPolL) || (stuWosF && stuItaL) || (stuWosF && stuSpaL)
//                        || (stuWosF && stuEngL) || (stuWosF && stuGerL) || (stuWosF && stuFreL) || (stuWosF && stuItL)
//                        || (stuWosF && stuChemL) || (stuWosF && stuPhyL) || (stuWosF && stuBioL) || (stuWosF && stuGeoL)
//                        || (stuWosF && stuHisL) || (stuWosF && stuHisML) || (stuWosF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Historia
//            if (stuHisL) {
//                liczba = 100 * 0.20;
//                if ((stuWosL && stuMathL) || (stuHisL && stuPolL) || (stuHisL && stuItaL) || (stuHisL && stuSpaL)
//                        || (stuHisL && stuEngL) || (stuHisL && stuGerL) || (stuHisL && stuFreL) || (stuHisL && stuItL)
//                        || (stuHisL && stuChemL) || (stuHisL && stuPhyL) || (stuHisL && stuBioL) || (stuHisL && stuGeoL)
//                        || (stuHisL && stuWosL) || (stuHisL && stuHisML) || (stuHisL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuHisL && stuMathF) || (stuHisL && stuPolF) || (stuHisL && stuItaF) || (stuHisL && stuSpaF)
//                        || (stuHisL && stuEngF) || (stuHisL && stuGerF) || (stuHisL && stuFreF) || (stuHisL && stuItF)
//                        || (stuHisL && stuChemF) || (stuHisL && stuPhyF) || (stuHisL && stuBioF) || (stuHisL && stuGeoF)
//                        || (stuHisL && stuWosF) || (stuHisL && stuHisMF) || (stuHisL && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuHisF) {
//                liczba = 100 * 0.10;
//                if ((stuHisF && stuMathF) || (stuHisF && stuPolF) || (stuHisF && stuItaF) || (stuHisF && stuSpaF)
//                        || (stuHisF && stuEngF) || (stuHisF && stuGerF) || (stuWosF && stuFreF) || (stuHisF && stuItF)
//                        || (stuHisF && stuChemF) || (stuHisF && stuPhyF) || (stuHisF && stuBioF) || (stuHisF && stuGeoF)
//                        || (stuHisF && stuWosF) || (stuHisF && stuHisMF) || (stuHisF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuHisF && stuMathL) || (stuHisF && stuPolL) || (stuHisF && stuItaL) || (stuHisF && stuSpaL)
//                        || (stuHisF && stuEngL) || (stuHisF && stuGerL) || (stuHisF && stuFreL) || (stuHisF && stuItL)
//                        || (stuHisF && stuChemL) || (stuHisF && stuPhyL) || (stuHisF && stuBioL) || (stuHisF && stuGeoL)
//                        || (stuHisF && stuWosL) || (stuHisF && stuHisML) || (stuHisF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Historia muzyki
//            if (stuHisML) {
//                liczba = 100 * 0.20;
//                if ((stuHisML && stuMathL) || (stuHisML && stuPolL) || (stuHisML && stuItaL) || (stuHisML && stuSpaL)
//                        || (stuHisML && stuEngL) || (stuHisML && stuGerL) || (stuHisML && stuFreL) || (stuHisML && stuItL)
//                        || (stuHisML && stuChemL) || (stuHisML && stuPhyL) || (stuHisML && stuBioL) || (stuHisML && stuGeoL)
//                        || (stuHisML && stuWosL) || (stuHisML && stuHisL) || (stuHisL && stuHisAL)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuHisML && stuMathF) || (stuHisML && stuPolF) || (stuHisML && stuItaF) || (stuHisML && stuSpaF)
//                        || (stuHisML && stuEngF) || (stuHisML && stuGerF) || (stuHisML && stuFreF) || (stuHisML && stuItF)
//                        || (stuHisML && stuChemF) || (stuHisML && stuPhyF) || (stuHisML && stuBioF) || (stuHisML && stuGeoF)
//                        || (stuHisML && stuWosF) || (stuHisML && stuHisF) || (stuHisML && stuHisAF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuHisMF) {
//                liczba = 100 * 0.10;
//                if ((stuHisMF && stuMathF) || (stuHisMF && stuPolF) || (stuHisMF && stuItaF) || (stuHisMF && stuSpaF)
//                        || (stuHisMF && stuEngF) || (stuHisMF && stuGerF) || (stuHisMF && stuFreF) || (stuHisMF && stuItF)
//                        || (stuHisMF && stuChemF) || (stuHisMF && stuPhyF) || (stuHisMF && stuBioF) || (stuHisMF && stuGeoF)
//                        || (stuHisMF && stuWosF) || (stuHisMF && stuHisF) || (stuHisMF && stuHisAF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuHisMF && stuMathL) || (stuHisMF && stuPolL) || (stuHisMF && stuItaL) || (stuHisMF && stuSpaL)
//                        || (stuHisMF && stuEngL) || (stuHisMF && stuGerL) || (stuHisMF && stuFreL) || (stuHisMF && stuItL)
//                        || (stuHisMF && stuChemL) || (stuHisMF && stuPhyL) || (stuHisMF && stuBioL) || (stuHisF && stuGeoL)
//                        || (stuHisMF && stuWosL) || (stuHisMF && stuHisL) || (stuHisMF && stuHisAL)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            //Historia sztuki
//            if (stuHisAL) {
//                liczba = 100 * 0.20;
//                if ((stuHisAL && stuMathL) || (stuHisML && stuPolL) || (stuHisAL && stuItaL) || (stuHisAL && stuSpaL)
//                        || (stuHisAL && stuEngL) || (stuHisAL && stuGerL) || (stuHisAL && stuFreL) || (stuHisAL && stuItL)
//                        || (stuHisAL && stuChemL) || (stuHisAL && stuPhyL) || (stuHisAL && stuBioL) || (stuHisAL && stuGeoL)
//                        || (stuHisAL && stuWosL) || (stuHisAL && stuHisL) || (stuHisAL && stuHisML)) {
//                    liczba = 2 * (100 * 0.20);
//                } else if ((stuHisAL && stuMathF) || (stuHisAL && stuPolF) || (stuHisAL && stuItaF) || (stuHisAL && stuSpaF)
//                        || (stuHisAL && stuEngF) || (stuHisAL && stuGerF) || (stuHisAL && stuFreF) || (stuHisAL && stuItF)
//                        || (stuHisAL && stuChemF) || (stuHisAL && stuPhyF) || (stuHisAL && stuBioF) || (stuHisAL && stuGeoF)
//                        || (stuHisAL && stuWosF) || (stuHisAL && stuHisF) || (stuHisAL && stuHisMF)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            if (stuHisAF) {
//                liczba = 100 * 0.10;
//                if ((stuHisAF && stuMathF) || (stuHisAF && stuPolF) || (stuHisAF && stuItaF) || (stuHisAF && stuSpaF)
//                        || (stuHisAF && stuEngF) || (stuHisAF && stuGerF) || (stuHisAF && stuFreF) || (stuHisAF && stuItF)
//                        || (stuHisAF && stuChemF) || (stuHisAF && stuPhyF) || (stuHisAF && stuBioF) || (stuHisAF && stuGeoF)
//                        || (stuHisAF && stuWosF) || (stuHisAF && stuHisF) || (stuHisAF && stuHisMF)) {
//                    liczba = 2 * (100 * 0.10);
//                } else if ((stuHisAF && stuMathL) || (stuHisAF && stuPolL) || (stuHisAF && stuItaL) || (stuHisAF && stuSpaL)
//                        || (stuHisAF && stuEngL) || (stuHisAF && stuGerL) || (stuHisAF && stuFreL) || (stuHisAF && stuItL)
//                        || (stuHisAF && stuChemL) || (stuHisAF && stuPhyL) || (stuHisAF && stuBioL) || (stuHisAF && stuGeoL)
//                        || (stuHisAF && stuWosL) || (stuHisAF && stuHisL) || (stuHisAF && stuHisML)) {
//                    liczba = (100 * 0.20) + (100 * 0.10);
//                }
//            }
//
//            punkty = egzamin + liczba;
//
//        }
//        studentToUpdate.setPoints(punkty);
//        studentRepository.save(studentToUpdate);
//
//        return studentToUpdate;
//    }

    public Student addStudent(StudentDTO student, ExamDTO exam
            , GradeDTO grade, OlympiadDTO olymp
            , ExtraParametersDTO extparam
    ) {
        Student studentToAdd = findUserByEmail(student.getEmail()).orElse(null);
        if (studentToAdd == null) {
            Student student1 = new Student();
            student1.setFirstName(student.getFirstName());
            student1.setLastName(student.getLastName());
            student1.setDateOfBirth(LocalDate.parse(student.getDateOfBirth()));
            student1.setSex(student.getSex());
            student1.setAlign(student.getAlign());
            student1.setLanguagePolish(student.getLanguagePolish());
            student1.setEmail(student.getEmail());

            mapExam(exam, student1);
            mapGrade(grade, student1);
            mapOlympiad(olymp, student1);
            mapExtParam(extparam, student1);
            student1 = studentRepository.save(student1);


            return student1;
        } else {
            return studentToAdd;
        }
    }


    private void mapExam(ExamDTO dto, Student student) {
        Exam exam = new Exam(student);
        exam.setMath(dto.getMath());
        exam.setLanguagePolishResult(dto.getLanguagePolishResult());
        exam.setForeignLanguage(dto.getForeignLanguage());
        student.setExams(exam);
    }

    private void mapGrade(GradeDTO dto, Student student) {
        Grade grade = new Grade(student);
        grade.setAverageOfGrades(dto.getAverageOfGrades());
        grade.setPolishGrade(dto.getPolishGrade());
        grade.setMathGrade(dto.getMathGrade());
        grade.setEnglishGrade(dto.getEnglishGrade());
        grade.setOtherLanguageGrade(dto.getOtherLanguageGrade());
        grade.setHistoryGrade(dto.getHistoryGrade());
        grade.setCivicsGrade(dto.getCivicsGrade());
        grade.setBiologyGrade(dto.getBiologyGrade());
        grade.setChemistryGrade(dto.getChemistryGrade());
        grade.setPhysicsGrade(dto.getPhysicsGrade());
        grade.setGeographyGrade(dto.getGeographyGrade());
        grade.setITGrade(dto.getITGrade());
        grade.setPhysicalEducationGrade(dto.getPhysicalEducationGrade());
        grade.setDesignAndTechnology(dto.getDesignAndTechnology());
        grade.setMusic(dto.getMusic());
        grade.setArt(dto.getArt());
        student.setGrades(grade);
    }

    private void mapOlympiad(OlympiadDTO dto, Student student) {
        Olympiad olympiad = new Olympiad(student);
        olympiad.setPolishOlympiad(dto.getPolishOlympiad());
        olympiad.setMathOlympiad(dto.getMathOlympiad());
        olympiad.setEnglishOlympiad(dto.getEnglishOlympiad());
        olympiad.setGermanOlympiad(dto.getGermanOlympiad());
        olympiad.setFrenchOlympiad(dto.getFrenchOlympiad());
        olympiad.setSpanishOlympiad(dto.getSpanishOlympiad());
        olympiad.setItalianOlympiad(dto.getItalianOlympiad());
        olympiad.setHistoryOlympiad(dto.getHistoryOlympiad());
        olympiad.setCivicsOlympiad(dto.getCivicsOlympiad());
        olympiad.setBiologyOlympiad(dto.getBiologyOlympiad());
        olympiad.setChemistryOlympiad(dto.getChemistryOlympiad());
        olympiad.setPhysicsOlympiad(dto.getPhysicsOlympiad());
        olympiad.setGeographyOlympiad(dto.getGeographyOlympiad());
        olympiad.setHistoryOfMusicOlympiad(dto.getHistoryOfMusicOlympiad());
        olympiad.setHistoryOfArtOlympiad(dto.getHistoryOfArtOlympiad());
        olympiad.setITOlympiad(dto.getITOlympiad());
        student.setOlympiads(olympiad);
    }

    //
    private void mapExtParam(ExtraParametersDTO dto, Student student) {
        ExtraParameters extpar = new ExtraParameters(student);
        extpar.setFastCounting(dto.getFastCounting());
        extpar.setFastReading(dto.getFastReading());
        extpar.setTroubleshooting(dto.getTroubleshooting());
        extpar.setQuickMemorization(dto.getQuickMemorization());
        extpar.setActingSkills(dto.getActingSkills());
        extpar.setVocalSkills(dto.getVocalSkills());
        extpar.setDanceSkills(dto.getDanceSkills());
        extpar.setWritingSkills(dto.getWritingSkills());
        extpar.setPhotographicSkills(dto.getPhotographicSkills());
        extpar.setLinguisticSkills(dto.getLinguisticSkills());
        extpar.setLanguageCertificate(dto.getLanguageCertificate());
        extpar.setInterestInPolitics(dto.getInterestInPolitics());
        extpar.setCommunicationSkills(dto.getCommunicationSkills());
        extpar.setSportSkills(dto.getSportSkills());
        extpar.setExtremeSport(dto.getExtremeSport());
        extpar.setPhysicalFitness(dto.getPhysicalFitness());
        extpar.setPhysicalEndurance(dto.getPhysicalEndurance());
        extpar.setWorkInTheOpenGround(dto.getWorkInTheOpenGround());
        extpar.setAbilityToUseAMap(dto.getAbilityToUseAMap());
        extpar.setPeriodicTable(dto.getPeriodicTable());
        extpar.setChemicalExperiments(dto.getChemicalExperiments());
        extpar.setBiologicalAndNaturalInterests(dto.getBiologicalAndNaturalInterests());
        extpar.setInterestInTechnology(dto.getInterestInTechnology());
        student.setExtraParameters(extpar);
    }

    public Student updateStudent(StudentDTO student, ExamDTO exam, GradeDTO grade, OlympiadDTO olymp,
                                 ExtraParametersDTO extrparam) {
//        Student studentToUpdate = findUserById(student.getId()).orElse(null);
        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
        if (studentToUpdate != null) {
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            studentToUpdate.setDateOfBirth(LocalDate.parse(student.getDateOfBirth()));
//            studentToUpdate.setDateOfBirth(student.getDateOfBirth());
            studentToUpdate.setSex(student.getSex());
            studentToUpdate.setAlign(student.getAlign());
            studentToUpdate.setLanguagePolish(student.getLanguagePolish());
            studentToUpdate.setEmail(student.getEmail());
            studentToUpdate.setClassForStudent(String.valueOf(student.getClassForStudent()));

            updateExam(exam, studentToUpdate);
            updateGrade(grade, studentToUpdate);
            updateOlympiad(olymp, studentToUpdate);
            updateExtraParam(extrparam, studentToUpdate);

            return studentRepository.save(studentToUpdate);
        } else {
            return studentToUpdate;
        }
    }

    private void updateExam(ExamDTO dto, Student student) {
        Exam exam = new Exam(student);
        exam.setLanguagePolishResult(dto.getLanguagePolishResult());
        exam.setMath(dto.getMath());
        exam.setForeignLanguage(dto.getForeignLanguage());
        student.setExams(exam);
    }

    private void updateGrade(GradeDTO dto, Student student) {
        Grade grade = new Grade(student);
        grade.setAverageOfGrades(dto.getAverageOfGrades());
        grade.setPolishGrade(dto.getPolishGrade());
        grade.setMathGrade(dto.getMathGrade());
        grade.setEnglishGrade(dto.getEnglishGrade());
        grade.setOtherLanguageGrade(dto.getOtherLanguageGrade());
        grade.setHistoryGrade(dto.getHistoryGrade());
        grade.setCivicsGrade(dto.getCivicsGrade());
        grade.setBiologyGrade(dto.getBiologyGrade());
        grade.setChemistryGrade(dto.getChemistryGrade());
        grade.setPhysicsGrade(dto.getPhysicsGrade());
        grade.setGeographyGrade(dto.getGeographyGrade());
        grade.setITGrade(dto.getITGrade());
        grade.setPhysicalEducationGrade(dto.getPhysicalEducationGrade());
        grade.setDesignAndTechnology(dto.getDesignAndTechnology());
        grade.setMusic(dto.getMusic());
        grade.setArt(dto.getArt());
        student.setGrades(grade);
    }

    private void updateOlympiad(OlympiadDTO dto, Student student) {
        Olympiad olympiad = new Olympiad(student);
        olympiad.setPolishOlympiad(dto.getPolishOlympiad());
        olympiad.setMathOlympiad(dto.getMathOlympiad());
        olympiad.setEnglishOlympiad(dto.getEnglishOlympiad());
        olympiad.setGermanOlympiad(dto.getGermanOlympiad());
        olympiad.setFrenchOlympiad(dto.getFrenchOlympiad());
        olympiad.setSpanishOlympiad(dto.getSpanishOlympiad());
        olympiad.setItalianOlympiad(dto.getItalianOlympiad());
        olympiad.setHistoryOlympiad(dto.getHistoryOlympiad());
        olympiad.setCivicsOlympiad(dto.getCivicsOlympiad());
        olympiad.setBiologyOlympiad(dto.getBiologyOlympiad());
        olympiad.setChemistryOlympiad(dto.getChemistryOlympiad());
        olympiad.setPhysicsOlympiad(dto.getPhysicsOlympiad());
        olympiad.setGeographyOlympiad(dto.getGeographyOlympiad());
        olympiad.setHistoryOfMusicOlympiad(dto.getHistoryOfMusicOlympiad());
        olympiad.setHistoryOfArtOlympiad(dto.getHistoryOfArtOlympiad());
        olympiad.setITOlympiad(dto.getITOlympiad());
        student.setOlympiads(olympiad);
    }

    private void updateExtraParam(ExtraParametersDTO dto, Student student) {
        ExtraParameters extpar = new ExtraParameters(student);
        extpar.setFastCounting(dto.getFastCounting());
        extpar.setFastReading(dto.getFastReading());
        extpar.setTroubleshooting(dto.getTroubleshooting());
        extpar.setQuickMemorization(dto.getQuickMemorization());
        extpar.setActingSkills(dto.getActingSkills());
        extpar.setVocalSkills(dto.getVocalSkills());
        extpar.setDanceSkills(dto.getDanceSkills());
        extpar.setWritingSkills(dto.getWritingSkills());
        extpar.setPhotographicSkills(dto.getPhotographicSkills());
        extpar.setLinguisticSkills(dto.getLinguisticSkills());
        extpar.setLanguageCertificate(dto.getLanguageCertificate());
        extpar.setInterestInPolitics(dto.getInterestInPolitics());
        extpar.setCommunicationSkills(dto.getCommunicationSkills());
        extpar.setSportSkills(dto.getSportSkills());
        extpar.setExtremeSport(dto.getExtremeSport());
        extpar.setPhysicalFitness(dto.getPhysicalFitness());
        extpar.setPhysicalEndurance(dto.getPhysicalEndurance());
        extpar.setWorkInTheOpenGround(dto.getWorkInTheOpenGround());
        extpar.setAbilityToUseAMap(dto.getAbilityToUseAMap());
        extpar.setPeriodicTable(dto.getPeriodicTable());
        extpar.setChemicalExperiments(dto.getChemicalExperiments());
        extpar.setBiologicalAndNaturalInterests(dto.getBiologicalAndNaturalInterests());
        extpar.setInterestInTechnology(dto.getInterestInTechnology());
        student.setExtraParameters(extpar);
    }

    public int enableAppUser(String email) {
        return studentRepository.enableStudent(email);
    }

    public int diableAppUser(String email) {
        return studentRepository.disableStudent(email);
    }

    public void deleteOurStudent(Student student) {
        studentRepository.delete(student);
    }


//    public Set<String> getStudentsSurname(){
//        return studentRepository.findAll().stream()
//                .map(Student::getLastName)
//                .collect(Collectors.toSet());
//    }
//
//    public List<Student> findStudentBySurname(String surname){
//        return studentRepository.getStudentBySurname(surname);
//    }


    public double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}

