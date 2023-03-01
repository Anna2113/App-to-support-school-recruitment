package com.example.aplikacja.student.service;

import com.example.aplikacja.student.entity.Klasa;
import com.example.aplikacja.student.entity.Student;
import com.example.aplikacja.student.enums.Ability;
import com.example.aplikacja.student.enums.LaureateOrFinalist;
import com.example.aplikacja.student.enums.NameOfClass;
import com.example.aplikacja.student.repository.KlasaRepository;
import com.example.aplikacja.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@AllArgsConstructor
public class ClassificationService {

    private final StudentRepository studentRepository;
    private final KlasaRepository klassRepository;

    public Optional<Student> findUserById(Long id) {
        return studentRepository.findById(id);
    }

    public Student classification(Student studentToUpdate) {


        Klasa klasaMatGeoInf = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass() == NameOfClass.MatGeoInf).findFirst().get();

        Klasa klasaPol = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Humanistyczna)).findFirst().get();

        Klasa klasaMatAngNiem = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.MatAngNiem)).findFirst().get();

        Klasa klasaBiolChem = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.BiolChem)).findFirst().get();

        Klasa klasaSportowa = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Sportowa)).findFirst().get();

        Klasa klasaMuzyczna = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Muzyczna)).findFirst().get();

        Klasa klasaAktorska = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Aktorska)).findFirst().get();

        //Olimpiady
        LaureateOrFinalist lauMat = studentToUpdate.getOlympiads().getMathOlympiad();
        LaureateOrFinalist lauGeo = studentToUpdate.getOlympiads().getGeographyOlympiad();
        LaureateOrFinalist lauInf = studentToUpdate.getOlympiads().getITOlympiad();
        LaureateOrFinalist lauAng = studentToUpdate.getOlympiads().getEnglishOlympiad();
        LaureateOrFinalist lauNiem = studentToUpdate.getOlympiads().getGermanOlympiad();
        String lauPol = studentToUpdate.getOlympiads().getPolishOlympiad().getLabel();
        String lauHist = studentToUpdate.getOlympiads().getHistoryOlympiad().getLabel();
        String lauWOS = studentToUpdate.getOlympiads().getCivicsOlympiad().getLabel();
        String lauBio = studentToUpdate.getOlympiads().getBiologyOlympiad().getLabel();
        String lauChem = studentToUpdate.getOlympiads().getChemistryOlympiad().getLabel();
        String lauMuzHist = studentToUpdate.getOlympiads().getHistoryOfMusicOlympiad().getLabel();
        String lauItal = studentToUpdate.getOlympiads().getItalianOlympiad().getLabel();

        //Egzaminy
        Double examPolish = Double.valueOf(studentToUpdate.getExams().getLanguagePolishResult());
        Double examMath = Double.valueOf(studentToUpdate.getExams().getMath());
        Double examEnglish = Double.valueOf(studentToUpdate.getExams().getForeignLanguage());

        //Oceny
        String grMath = studentToUpdate.getGrades().getMathGrade();
        String grGeo = studentToUpdate.getGrades().getGeographyGrade();
        String grInf = studentToUpdate.getGrades().getITGrade();
        String grAng = studentToUpdate.getGrades().getEnglishGrade();
        String grNiem = studentToUpdate.getGrades().getOtherLanguageGrade();
        String grWf = studentToUpdate.getGrades().getPhysicalEducationGrade();
        String grBio = studentToUpdate.getGrades().getBiologyGrade();


        //Jest laureatem
        if (lauMat == LaureateOrFinalist.Laureat) {
            if (lauGeo == LaureateOrFinalist.Laureat || lauInf == LaureateOrFinalist.Laureat) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
            } else if (lauAng == LaureateOrFinalist.Laureat || lauNiem == LaureateOrFinalist.Laureat) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
            } else if (lauGeo == LaureateOrFinalist.Finalista || lauInf == LaureateOrFinalist.Finalista) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
            } else if (lauAng == LaureateOrFinalist.Finalista || lauNiem == LaureateOrFinalist.Finalista) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
            } else //(jest tylko laureatem z matematyki lub jest laureatem z więcej niż dwóch kierunkowych
            //przedmiotów dla tej klasy )
            {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
            }
            //Jest finalistą
        } else if (lauMat == LaureateOrFinalist.Finalista) {
            if (lauGeo == LaureateOrFinalist.Laureat || lauInf == LaureateOrFinalist.Laureat) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
            } else if (lauAng == LaureateOrFinalist.Laureat || lauNiem == LaureateOrFinalist.Laureat) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
            } else if (lauGeo == LaureateOrFinalist.Finalista || lauInf == LaureateOrFinalist.Finalista) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
            } else if (lauAng == LaureateOrFinalist.Finalista || lauNiem == LaureateOrFinalist.Finalista) {
                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
            } else //(Jest tylko finalistą) {
                if (studentToUpdate.getPointsMatGeoInf() >= klasaMatGeoInf.getMinAmountOfPointsFromExams()) {
                    if (grGeo.equals("1.0") || grInf.equals("1.0")) {
                        if (studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
                                || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK
                                || studentToUpdate.getExtraParameters().getWorkInTheOpenGround() == Ability.TAK
                                || studentToUpdate.getExtraParameters().getInterestInTechnology() == Ability.TAK) {
                            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
                        } else {
                            //Student nie klasyfikuje się do tej klasy lub trafia na listę rezerwową.
                            if (studentToUpdate.getPointsMAN() >= klasaMatAngNiem.getMinAmountOfPointsFromExams()) {
                                if (grAng.equals("1.0") || grNiem.equals("1.0")) {
                                    if (studentToUpdate.getExtraParameters().getLinguisticSkills() == Ability.TAK
                                            || studentToUpdate.getExtraParameters().getLanguageCertificate() == Ability.TAK
                                            || studentToUpdate.getExtraParameters().getQuickMemorization() == Ability.TAK
                                            || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK) {
                                        studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
                                    } else {
                                        if (studentToUpdate.getPointsS() >= klasaSportowa.getMinAmountOfPointsFromExams()) {
                                            if (grWf.equals("1.0") || grBio.equals("1.0")) {
                                                if (studentToUpdate.getExtraParameters().getSportSkills() == Ability.TAK
                                                        || studentToUpdate.getExtraParameters().getExtremeSport() == Ability.TAK
                                                        || studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
                                                        || studentToUpdate.getExtraParameters().getBiologicalAndNaturalInterests() == Ability.TAK) {
                                                    studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
                                                }
                                            } else if (!grWf.equals("1.0") || !grBio.equals("1.0")) {
                                                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
                                            }
                                        } else if (studentToUpdate.getPointsS() < klasaSportowa.getMinAmountOfPointsFromExams()) {
                                            if (grWf.equals("5.0") && grBio.equals("5.0")) {
                                                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
                                            } else if (grWf.equals("1.0") || grBio.equals("1.0")) {
                                                studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
                                            } else if (studentToUpdate.getExtraParameters().getSportSkills() == Ability.TAK
                                                    || studentToUpdate.getExtraParameters().getExtremeSport() == Ability.TAK
                                                    || studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
                                                    || studentToUpdate.getExtraParameters().getBiologicalAndNaturalInterests() == Ability.TAK) {
                                                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
                                            } else {
                                                studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
                                            }
                                        }
                                    }
                                } else if (!grAng.equals("1.0") && !grNiem.equals("1.0")) {
                                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
                                }
                            } else if (studentToUpdate.getPointsMAN() < klasaMatAngNiem.getMinAmountOfPointsFromExams()) {
                                if (grAng.equals("5.0") && grNiem.equals("5.0")) {
                                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
                                } else if (grAng.equals("1.0") || grNiem.equals("1.0")) {
                                    studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
                                } else if (studentToUpdate.getExtraParameters().getLinguisticSkills() == Ability.TAK
                                        || studentToUpdate.getExtraParameters().getLanguageCertificate() == Ability.TAK
                                        || studentToUpdate.getExtraParameters().getQuickMemorization() == Ability.TAK
                                        || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK) {
                                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
                                } else {
                                    studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
                                }
                            }
                        }
                    } else if (!grGeo.equals("1.0") && grInf.equals("1.0")) {
                        studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
                    }
                } else if (studentToUpdate.getPointsMatGeoInf() < klasaMatGeoInf.getMinAmountOfPointsFromExams()) {
                    if (grGeo.equals("5.0") && grInf.equals("5.0")) {
                        studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
                    } else if (grGeo.equals("1.0") || grInf.equals("1.0")) {
                        studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
                    } else {
                        //Można zrobić, tak aby uczeń na tym poziomie musiał mieć wszystkie wymagane umiejętności
                        //Czyli alternatywę zamienimy na koniunkcję.
                        if (studentToUpdate.getExtraParameters().getFastCounting() == Ability.TAK
                                || studentToUpdate.getExtraParameters().getTroubleshooting() == Ability.TAK
                                || studentToUpdate.getExtraParameters().getWorkInTheOpenGround() == Ability.TAK
                                || studentToUpdate.getExtraParameters().getInterestInTechnology() == Ability.TAK) {
                            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
                        } else {
                            studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową.");
                        }
                    }
                }
            //jeśli nie jest laureatem i nie jest finalistą
        } else {
            if (examPolish < 30.00 || examMath < 30.00 || examEnglish < 30.00) {
                studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową bo ma mniej niż 30% z egzaminu.");
            }
        }

        //Zakładam, że jeżeli uczeń będzie miał tylko olimpiadę matematyczną lub będzie miał olimpiadę
        //matematyczną i inną niż wyróżnione lub będzie innym finalistą poza wyróżnionymi
        //to przydzieli go domyślnie do klasy matGeoInf.

        // TODO: 18.02.2023 Co zrobić w wypadku kiedy w dwóch klasach jest ten sam przedmiot jako kierunkowy ?

//        if (lauMat.equals("Laureat") || lauGeo.equals("Laureat") || lauInf.equals("Laureat")) {
//            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//        } else if (lauPol.equals("Laureat") || lauHist.equals("Laureat") || lauWOS.equals("Laureat")) {
//            studentToUpdate.setClassForStudent(String.valueOf(klasaPol.getNameOfClass()));
//        } else if (lauMat.equals("Laureat") || lauAng.equals("Laureat") || lauNiem.equals("Laureat")) {
//            studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//        } else if (lauAng.equals("Laureat") || lauBio.equals("Laureat") || lauChem.equals("Laureat")) {
//            studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChem.getNameOfClass()));
//        } else if (lauMat.equals("Laureat") || lauBio.equals("Laureat")) {
//            studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//        } else if (lauMat.equals("Laureat") || lauNiem.equals("Laureat") || lauMuzHist.equals("Laureat")) {
//            studentToUpdate.setClassForStudent(String.valueOf(klasaMuzyczna.getNameOfClass()));
//        } else if (lauAng.equals("Laureat") || lauItal.equals("Laureat") || lauMuzHist.equals("Laureat")) {
//            studentToUpdate.setClassForStudent(String.valueOf(klasaAktorska.getNameOfClass()));
//        }

        return studentRepository.save(studentToUpdate);
    }


}
