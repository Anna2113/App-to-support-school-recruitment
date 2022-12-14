package com.example.aplikacja.student.service;

import com.example.aplikacja.student.dto.*;
import com.example.aplikacja.student.entity.*;
import com.example.aplikacja.student.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
//    private final KlasaRepository klassRepository;

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

    public Optional<Exam> findExamById(Long id){
        return examRepository.findById(id);
    }

    public Optional<Grade> findGradeById(Long id){return gradeRepository.findById(id);}

    public Optional<Olympiad> findOlympiadById(Long id){return olympiadRepository.findById(id);}

    public Optional<ExtraParameters> findExParamById(Long id){return extraParameters.findById(id);}

//    public Optional<Klasa> findClassBySymbol(String symbol){return klassRepository.findBySymbol(symbol);}

//    public String addPoints(StudentDTO student, ExamDTO exam, GradeDTO grade, OlympiadDTO olympiad,
//                            ExtraParametersDTO extparam, )


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

//    public Klasa addClass(KlasaDTO cl){
//        Klasa klasaToAdd = findClassBySymbol(cl.getSymbol()).orElse(null);
//        if(klasaToAdd == null){
//            Klasa klasa1 = new Klasa();
//            klasa1.setNameOfClass(cl.getNameOfClass());
//            klasa1.setSymbol(cl.getSymbol());
//            klasa1.setLiczba(cl.getLiczba());
//
//            klasa1 = klassRepository.save(klasa1);
//
//            return klasa1;
//        }else{
//            return klasaToAdd;
//        }
//    }


    public Optional<Student> findUserByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

//    public List<Klasa> getAllKlass(){
//        return klassRepository.findAll();
//    }


    public Student updateStudent(StudentDTO student, ExamDTO exam, GradeDTO grade, OlympiadDTO olymp,
                                 ExtraParametersDTO extrparam){
//        Student studentToUpdate = findUserById(student.getId()).orElse(null);
        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
        if(studentToUpdate != null){
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            studentToUpdate.setDateOfBirth(LocalDate.parse(student.getDateOfBirth()));
//            studentToUpdate.setDateOfBirth(student.getDateOfBirth());
            studentToUpdate.setSex(student.getSex());
            studentToUpdate.setAlign(student.getAlign());
            studentToUpdate.setLanguagePolish(student.getLanguagePolish());
            studentToUpdate.setEmail(student.getEmail());

            updateExam(exam, studentToUpdate);
            updateGrade(grade, studentToUpdate);
//            updateOlympiad(olymp, studentToUpdate);
//            updateExtraParam(extrparam, studentToUpdate);

            return studentRepository.save(studentToUpdate);
        }else{
            return studentToUpdate;
        }
    }

    private void updateExam(ExamDTO dto, Student student){
        Exam exam = new Exam(student);
        exam.setLanguagePolishResult(dto.getLanguagePolishResult());
        exam.setMath(dto.getMath());
        exam.setForeignLanguage(dto.getForeignLanguage());
        student.setExams(exam);
    }

    private void updateGrade(GradeDTO dto, Student student){
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
        student.setGrades(grade);
    }

    private void updateOlympiad(OlympiadDTO dto, Student student){
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

    private void updateExtraParam(ExtraParametersDTO dto, Student student){
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

    public int enableAppUser(String email) {
        return studentRepository.enableStudent(email);
    }

    public int diableAppUser(String email) {
        return studentRepository.disableStudent(email);
    }

    public void deleteOurStudent(Student student){
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

}

