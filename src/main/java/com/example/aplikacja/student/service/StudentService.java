package com.example.aplikacja.student.service;

import com.example.aplikacja.appuser.AppUser;
import com.example.aplikacja.appuser.UserRepository;
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
import java.util.*;

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
    private final UserRepository userRepository;

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

    public List<Student> listaStudentowUsera(){
        return userRepository.getStudent();
    }

    public List<Student> listaRezerwowa() {
        return studentRepository.reserveList();
    }

    public Student addStudent(StudentDTO student, ExamDTO exam
            , GradeDTO grade, OlympiadDTO olymp
            , ExtraParametersDTO extparam, AppUser appUser) {
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
            student1.setAppUser(appUser);

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
        Student studentToUpdate = findUserById(student.getId()).orElse(null);
//        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
        if (studentToUpdate != null) {
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            studentToUpdate.setDateOfBirth(LocalDate.parse(student.getDateOfBirth()));
//            studentToUpdate.setDateOfBirth(student.getDateOfBirth());
            studentToUpdate.setSex(student.getSex());
            studentToUpdate.setAlign(student.getAlign());
            studentToUpdate.setLanguagePolish(student.getLanguagePolish());
//            studentToUpdate.setEmail(student.getEmail());

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

        String wagaMathExam = klasa1.getWeightExamMath();
        String wagaPolExam = klasa1.getWeightExamPolish();
        String wagaEngExam = klasa1.getWeightExamEnglish();

        Double waga_fin = klasa1.getNumberOfPointsForFinalist();
        Double waga_lau = klasa1.getNumberOfPointsForOlimp();

        String mathGrade = studentToUpdate.getGrades().getMathGrade();
        String geoGrade = studentToUpdate.getGrades().getGeographyGrade();
        String itGrade = studentToUpdate.getGrades().getITGrade();

        String mathExam = studentToUpdate.getExams().getMath();
        String polExam = studentToUpdate.getExams().getLanguagePolishResult();
        String engExam = studentToUpdate.getExams().getForeignLanguage();

        LaureateOrFinalist finMat = studentToUpdate.getOlympiads().getMathOlympiad();
        LaureateOrFinalist finGeo = studentToUpdate.getOlympiads().getGeographyOlympiad();
        LaureateOrFinalist finInf = studentToUpdate.getOlympiads().getITOlympiad();

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

        if (finMat == LaureateOrFinalist.Laureat && finGeo == LaureateOrFinalist.Finalista && finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaGeo + wagaInf);
        }
        if (finGeo == LaureateOrFinalist.Laureat && finMat == LaureateOrFinalist.Finalista && finInf == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaMath + wagaInf);
        }
        if (finInf == LaureateOrFinalist.Laureat && finGeo == LaureateOrFinalist.Finalista && finMat == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaGeo + wagaMath);
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

        if (finPol == LaureateOrFinalist.Laureat && finHis == LaureateOrFinalist.Finalista && finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaHis + wagaWos);
        }
        if (finHis == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finWos == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaWos);
        }
        if (finWos == LaureateOrFinalist.Laureat && finHis == LaureateOrFinalist.Finalista && finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaHis + wagaPol);
        }
        if (finPol == LaureateOrFinalist.Brak && finHis == LaureateOrFinalist.Brak && finWos == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }


        punkty = punktyHuman + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsHuman(round(punkty));
        studentToUpdate.setPunktyOlimpijskieHuman(round(punkty_fin));

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

        if (finBio == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaChem + wagaAng);
        }
        if (finChem == LaureateOrFinalist.Laureat && finBio == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaBio + wagaAng);
        }
        if (finAng == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista && finBio == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaChem + wagaBio);
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
        LaureateOrFinalist finFranc = studentToUpdate.getOlympiads().getFrenchOlympiad();

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

        if (finPol == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaAng + wagaMuz);
        }
        if (finPol == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaAng + wagaSzt);
        }
        if (finPol == LaureateOrFinalist.Laureat && finMus == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaMuz + wagaSzt);
        }
        if (finAng == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaMuz);
        }
        if (finAng == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaSzt);
        }
        if (finAng == LaureateOrFinalist.Laureat && finMus == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaMuz + wagaSzt);
        }
        if (finMus == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaAng);
        }
        if (finMus == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaSzt);
        }
        if (finMus == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaAng + wagaSzt);
        }
        if (finSzt == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finAng == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaAng);
        }
        if (finSzt == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaSzt);
        }
        if (finSzt == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaAng + wagaSzt);
        }

        if (finPol == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista
                && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaAng + wagaMuz + wagaSzt);
        }
        if (finAng == LaureateOrFinalist.Laureat && finPol == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista
                && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaPol + wagaMuz + wagaSzt);
        }
        if (finMus == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista && finPol == LaureateOrFinalist.Finalista
                && finSzt == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaAng + wagaPol + wagaSzt);
        }
        if (finSzt == LaureateOrFinalist.Laureat && finAng == LaureateOrFinalist.Finalista && finMus == LaureateOrFinalist.Finalista
                && finPol == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaAng + wagaMuz + wagaPol);
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

        if (finFiz == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaChem + wagaFrac);
        }
        if (finChem == LaureateOrFinalist.Laureat && finFiz == LaureateOrFinalist.Finalista && finFranc == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaFiz + wagaFrac);
        }
        if (finFranc == LaureateOrFinalist.Laureat && finChem == LaureateOrFinalist.Finalista && finFiz == LaureateOrFinalist.Finalista) {
            punkty_fin = waga_lau * 1000 + 6 * waga_fin * (wagaChem + wagaFiz);
        }
        if (finFiz == LaureateOrFinalist.Brak && finChem == LaureateOrFinalist.Brak && finFranc == LaureateOrFinalist.Brak) {
            punkty_fin = 0.0;
        }

        punkty = punktyFizChemFran + pointsFromExams + punkty_fin;
        studentToUpdate.setPointsFIZ(round(punkty));
        studentToUpdate.setPunktyOlimpijskieFizChemFranc(round(punkty_fin));

        return studentRepository.save(studentToUpdate);
    }

    public Map<String, Double> punkty(Student student, Map<String, Double> points) {
        points.put(NameOfClass.FizChemFranc.getLabel(), student.getPointsFIZ());
        points.put(NameOfClass.MatGeoInf.getLabel(), student.getPointsMatGeoInf());
        points.put(NameOfClass.Humanistyczna.getLabel(), student.getPointsHuman());
        points.put(NameOfClass.BiolChem.getLabel(), student.getPointsBiolChem());
        points.put(NameOfClass.Artystyczna.getLabel(), student.getPointsArt());
        points.put(NameOfClass.Sportowa.getLabel(), student.getPointsS());
        points.put(NameOfClass.MatAngNiem.getLabel(), student.getPointsMAN());

        return points;
    }

    public Map<String, Double> punktyOlymp(Student student, Map<String, Double> pointsOlimp) {
        pointsOlimp.put(NameOfClass.FizChemFranc.getLabel(), student.getPunktyOlimpijskieFizChemFranc());
        pointsOlimp.put(NameOfClass.MatGeoInf.getLabel(), student.getPunktyOlimpijskieMatGeoInf());
        pointsOlimp.put(NameOfClass.Humanistyczna.getLabel(), student.getPunktyOlimpijskieHuman());
        pointsOlimp.put(NameOfClass.BiolChem.getLabel(), student.getPunktyOlimpijskieBiolChem());
        pointsOlimp.put(NameOfClass.Artystyczna.getLabel(), student.getPunktyOlimpijskieArtystyczna());
        pointsOlimp.put(NameOfClass.Sportowa.getLabel(), student.getPunktyOlimpijskieSportowa());
        pointsOlimp.put(NameOfClass.MatAngNiem.getLabel(), student.getPunktyOlimpijskieMatAngNiem());

        return pointsOlimp;
    }


    public Student updateStudentClass(Student student, NameOfClass name) {
        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
        if (studentToUpdate != null) {
            studentToUpdate.setClassForStudent(String.valueOf(name));
            return studentRepository.save(studentToUpdate);
        } else {
            return studentToUpdate;
        }
    }


    public Student editClassForStudent(Student student) {
        Student studentToUpdate = findUserById(student.getId()).orElse(null);
        if (studentToUpdate != null) {
            studentToUpdate.setClassForStudent(String.valueOf(student.getClassForStudent()));

            return studentRepository.save(studentToUpdate);
        } else {
            return studentToUpdate;
        }
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

    public double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}

