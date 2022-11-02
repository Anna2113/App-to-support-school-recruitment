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

    public Student addStudent(StudentDTO student, ExamDTO exam) {
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
            student1 = studentRepository.save(student1);

            Exam ex = new Exam(student1);
            ex.setLanguagePolishResult(exam.getLanguagePolishResult());
            ex.setMath(exam.getMath());
            ex.setForeignLanguage(exam.getForeignLanguage());
            examRepository.save(ex);

            return student1;
        } else {
            return studentToAdd;
        }
    }

    public Exam addResult(Exam exam, Student student) {
        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
        if (student1 != null) {
            Exam ex = new Exam();
            ex.setLanguagePolishResult(exam.getLanguagePolishResult());
            ex.setMath(exam.getMath());
            ex.setForeignLanguage(exam.getForeignLanguage());
            ex.setStudent(student1);
            return examRepository.save(ex);
        } else {
            return exam;
        }
    }

    public Grade addGrades(Grade grade, Student student) {
        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
        if (student1 != null) {
            Grade gr = new Grade();
            gr.setAverageOfGrades(grade.getAverageOfGrades());
            gr.setPolishGrade(grade.getPolishGrade());
            gr.setMathGrade(grade.getMathGrade());
            gr.setEnglishGrade(grade.getEnglishGrade());
            gr.setOtherLanguageGrade(grade.getOtherLanguageGrade());
            gr.setCivicsGrade(grade.getCivicsGrade());
            gr.setHistoryGrade(grade.getHistoryGrade());
            gr.setPhysicsGrade(grade.getPhysicsGrade());
            gr.setChemistryGrade(grade.getChemistryGrade());
            gr.setBiologyGrade(grade.getBiologyGrade());
            gr.setGeographyGrade(grade.getGeographyGrade());
            gr.setITGrade(grade.getITGrade());
            gr.setPhysicalEducationGrade(grade.getPhysicalEducationGrade());
            gr.setStudent(grade.getStudent());

            return gradeRepository.save(gr);
        } else {
            return grade;
        }
    }

    public Olympiad addOlympiads(Olympiad olympiad, Student student) {
        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
        if (student1 != null) {
            Olympiad ol = new Olympiad();
            ol.setPolishOlympiad(olympiad.getPolishOlympiad());
            ol.setMathOlympiad(olympiad.getMathOlympiad());
            ol.setEnglishOlympiad(olympiad.getEnglishOlympiad());
            ol.setGermanOlympiad(olympiad.getGermanOlympiad());
            ol.setFrenchOlympiad(olympiad.getFrenchOlympiad());
            ol.setSpanishOlympiad(olympiad.getSpanishOlympiad());
            ol.setItalianOlympiad(olympiad.getItalianOlympiad());
            ol.setHistoryOlympiad(olympiad.getHistoryOlympiad());
            ol.setCivicsOlympiad(olympiad.getCivicsOlympiad());
            ol.setBiologyOlympiad(olympiad.getBiologyOlympiad());
            ol.setChemistryOlympiad(olympiad.getChemistryOlympiad());
            ol.setPhysicsOlympiad(olympiad.getPhysicsOlympiad());
            ol.setGeographyOlympiad(olympiad.getGeographyOlympiad());
            ol.setHistoryOfMusicOlympiad(olympiad.getHistoryOfMusicOlympiad());
            ol.setHistoryOfArtOlympiad(olympiad.getHistoryOfArtOlympiad());
            ol.setStudent(olympiad.getStudent());

            return olympiadRepository.save(ol);
        } else {
            return olympiad;
        }
    }

    public ExtraParameters addOtherParams(ExtraParameters expar, Student student){
        Student student1 = findUserByEmail(student.getEmail()).orElse(null);
        if(student1 != null){
            ExtraParameters exp = new ExtraParameters();
            exp.setFastCounting(expar.getFastCounting());
            exp.setFastReading(expar.getFastReading());
            exp.setTroubleshooting(expar.getTroubleshooting());
            exp.setQuickMemorization(expar.getQuickMemorization());
            exp.setActingSkills(expar.getActingSkills());
            exp.setVocalSkills(expar.getVocalSkills());
            exp.setDanceSkills(expar.getDanceSkills());
            exp.setWritingSkills(expar.getWritingSkills());
            exp.setPhotographicSkills(expar.getPhotographicSkills());
            exp.setLinguisticSkills(expar.getLinguisticSkills());
            exp.setSportSkills(expar.getSportSkills());
            exp.setExtremeSport(expar.getExtremeSport());
            exp.setPhysicalFitness(expar.getPhysicalFitness());
            exp.setPhysicalEndurance(expar.getPhysicalEndurance());
            exp.setWorkInTheOpenGround(expar.getWorkInTheOpenGround());
            exp.setAbilityToUseAMap(expar.getAbilityToUseAMap());
            exp.setBiologicalAndNaturalInterests(expar.getBiologicalAndNaturalInterests());
            exp.setInterestInTechnology(expar.getInterestInTechnology());
            exp.setStudent(expar.getStudent());

            return extraParameters.save(exp);
        }else{
            return expar;
        }
    }

    public Optional<Student> findUserByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


}
