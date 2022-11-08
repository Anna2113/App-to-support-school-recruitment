package com.example.aplikacja.student;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Student addStudent(StudentDTO student, ExamDTO exam
                              ,GradeDTO grade, OlympiadDTO olymp
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
        grade.setPhysicalEducationGrade(grade.getPhysicalEducationGrade());
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
        extpar.setSportSkills(dto.getSportSkills());
        extpar.setExtremeSport(dto.getExtremeSport());
        extpar.setPhysicalFitness(dto.getPhysicalFitness());
        extpar.setPhysicalEndurance(dto.getPhysicalEndurance());
        extpar.setWorkInTheOpenGround(dto.getWorkInTheOpenGround());
        extpar.setAbilityToUseAMap(dto.getAbilityToUseAMap());
        extpar.setBiologicalAndNaturalInterests(dto.getBiologicalAndNaturalInterests());
        extpar.setInterestInTechnology(dto.getInterestInTechnology());
        student.setExtraParameters(extpar);
    }


//    public Exam addResult(Exam exam, Student student) {
//        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
//        if (student1 != null) {
//            Exam ex = new Exam();
//            ex.setLanguagePolishResult(exam.getLanguagePolishResult());
//            ex.setMath(exam.getMath());
//            ex.setForeignLanguage(exam.getForeignLanguage());
//            ex.setStudent(student1);
//            return examRepository.save(ex);
//        } else {
//            return exam;
//        }
//    }
//
//    public Grade addGrades(Grade grade, Student student) {
//        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
//        if (student1 != null) {
//            Grade gr = new Grade();
//            gr.setAverageOfGrades(grade.getAverageOfGrades());
//            gr.setPolishGrade(grade.getPolishGrade());
//            gr.setMathGrade(grade.getMathGrade());
//            gr.setEnglishGrade(grade.getEnglishGrade());
//            gr.setOtherLanguageGrade(grade.getOtherLanguageGrade());
//            gr.setCivicsGrade(grade.getCivicsGrade());
//            gr.setHistoryGrade(grade.getHistoryGrade());
//            gr.setPhysicsGrade(grade.getPhysicsGrade());
//            gr.setChemistryGrade(grade.getChemistryGrade());
//            gr.setBiologyGrade(grade.getBiologyGrade());
//            gr.setGeographyGrade(grade.getGeographyGrade());
//            gr.setITGrade(grade.getITGrade());
//            gr.setPhysicalEducationGrade(grade.getPhysicalEducationGrade());
//            gr.setStudent(grade.getStudent());
//
//            return gradeRepository.save(gr);
//        } else {
//            return grade;
//        }
//    }
//
//    public Olympiad addOlympiads(Olympiad olympiad, Student student) {
//        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
//        if (student1 != null) {
//            Olympiad ol = new Olympiad();
//            ol.setPolishOlympiad(olympiad.getPolishOlympiad());
//            ol.setMathOlympiad(olympiad.getMathOlympiad());
//            ol.setEnglishOlympiad(olympiad.getEnglishOlympiad());
//            ol.setGermanOlympiad(olympiad.getGermanOlympiad());
//            ol.setFrenchOlympiad(olympiad.getFrenchOlympiad());
//            ol.setSpanishOlympiad(olympiad.getSpanishOlympiad());
//            ol.setItalianOlympiad(olympiad.getItalianOlympiad());
//            ol.setHistoryOlympiad(olympiad.getHistoryOlympiad());
//            ol.setCivicsOlympiad(olympiad.getCivicsOlympiad());
//            ol.setBiologyOlympiad(olympiad.getBiologyOlympiad());
//            ol.setChemistryOlympiad(olympiad.getChemistryOlympiad());
//            ol.setPhysicsOlympiad(olympiad.getPhysicsOlympiad());
//            ol.setGeographyOlympiad(olympiad.getGeographyOlympiad());
//            ol.setHistoryOfMusicOlympiad(olympiad.getHistoryOfMusicOlympiad());
//            ol.setHistoryOfArtOlympiad(olympiad.getHistoryOfArtOlympiad());
//            ol.setStudent(olympiad.getStudent());
//
//            return olympiadRepository.save(ol);
//        } else {
//            return olympiad;
//        }
//    }
//
//    public ExtraParameters addOtherParams(ExtraParameters expar, Student student){
//        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
//        if(student1 != null){
//            ExtraParameters exp = new ExtraParameters();
//            exp.setFastCounting(expar.getFastCounting());
//            exp.setFastReading(expar.getFastReading());
//            exp.setTroubleshooting(expar.getTroubleshooting());
//            exp.setQuickMemorization(expar.getQuickMemorization());
//            exp.setActingSkills(expar.getActingSkills());
//            exp.setVocalSkills(expar.getVocalSkills());
//            exp.setDanceSkills(expar.getDanceSkills());
//            exp.setWritingSkills(expar.getWritingSkills());
//            exp.setPhotographicSkills(expar.getPhotographicSkills());
//            exp.setLinguisticSkills(expar.getLinguisticSkills());
//            exp.setSportSkills(expar.getSportSkills());
//            exp.setExtremeSport(expar.getExtremeSport());
//            exp.setPhysicalFitness(expar.getPhysicalFitness());
//            exp.setPhysicalEndurance(expar.getPhysicalEndurance());
//            exp.setWorkInTheOpenGround(expar.getWorkInTheOpenGround());
//            exp.setAbilityToUseAMap(expar.getAbilityToUseAMap());
//            exp.setBiologicalAndNaturalInterests(expar.getBiologicalAndNaturalInterests());
//            exp.setInterestInTechnology(expar.getInterestInTechnology());
//            exp.setStudent(expar.getStudent());
//
//            return extraParameters.save(exp);
//        }else{
//            return expar;
//        }
//    }

    public Optional<Student> findUserByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


}
