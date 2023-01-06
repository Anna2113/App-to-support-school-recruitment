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

    public Student addPoints(Student student, Klasa klasa, List<WeightOfGrade> lista) {
        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
        Klasa klasaToUpdate = findClassBySymbol(klasa.getSymbol()).orElse(null);
        double liczbaPkt = 0.0;
        double egzamin = 0.0;
        double srednia = 0.0; //Średnia przedmiotów kierunkowych

        double math = Double.parseDouble(studentToUpdate.getExams().getMath());
        double polish = Double.parseDouble(studentToUpdate.getExams().getLanguagePolishResult());
        double english = Double.parseDouble(studentToUpdate.getExams().getForeignLanguage());

        double grMath = Double.parseDouble(studentToUpdate.getGrades().getMathGrade());
        double grIt = Double.parseDouble(studentToUpdate.getGrades().getITGrade());
        double grGeo = Double.parseDouble(studentToUpdate.getGrades().getGeographyGrade());
        double grPol = Double.parseDouble(studentToUpdate.getGrades().getPolishGrade());
        double grHis = Double.parseDouble(studentToUpdate.getGrades().getHistoryGrade());
        double grWos = Double.parseDouble(studentToUpdate.getGrades().getCivicsGrade());

        double weightMathExam = Double.parseDouble(klasaToUpdate.getWeightExamMath());
        double weightPolishExam = Double.parseDouble(klasaToUpdate.getWeightExamPolish());
        double weightEnglishExam = Double.parseDouble(klasaToUpdate.getWeightExamEnglish());

        if(studentToUpdate != null) {

            egzamin = math * weightMathExam + polish * weightPolishExam + english * weightEnglishExam;

            //Utworzyć listę typu subject (chyba trzeba zrobić klasę), która przechowuje
            // przedmioty kierunkowe. Wtedy wywoływać listę z przedmiotami tak jak z wagami
            // i nie będzie trzeba robić dla konkretnego profilu klasy.

            if (klasaToUpdate.getNameOfClass().getLabel().equals("MatGeoInf")) {
                srednia = lista.stream().mapToDouble(w -> w.getWartosc() * grMath
                        + w.getWartosc() * grIt +
                        grGeo * w.getWartosc()).sum();

            }

            if(klasaToUpdate.getNameOfClass().getLabel().equals("Humanistyczna")){
                srednia = lista.stream().mapToDouble(w -> w.getWartosc() * grPol
                        + w.getWartosc() * grHis +
                        grWos * w.getWartosc()).sum();
            }
            liczbaPkt = egzamin + srednia;


            studentToUpdate.setPoints(liczbaPkt);
//            studentRepository.save(studentToUpdate);

            return studentRepository.save(studentToUpdate);
        }else{
            return studentToUpdate;
        }
    }

    public Student pointsOfStudent(Student student, Exam exam) {
        Student studentToUpdate = findUserByEmail(student.getEmail()).orElse(null);
        double liczba = 0;
        double powtorzeniaL = 0;
        double powtorzeniaF = 0;
        double punkty = 0;
        double egzamin = 0;
        double math = Double.parseDouble(studentToUpdate.getExams().getMath());
        double polish = Double.parseDouble(studentToUpdate.getExams().getLanguagePolishResult());
        double english = Double.parseDouble(studentToUpdate.getExams().getForeignLanguage());
        if (studentToUpdate != null) {

            egzamin = math * 0.8 + polish * 0.5 + english * 0.5;

            Olympiad stu = studentToUpdate.getOlympiads();
            boolean stuMathL = stu.getMathOlympiad().getLabel().equals("Laureat");
            boolean stuMathF = stu.getMathOlympiad().getLabel().equals("Finalista");
            boolean stuItL = stu.getITOlympiad().getLabel().equals("Laureat");
            boolean stuItF = stu.getITOlympiad().getLabel().equals("Finalista");
            boolean stuPolL = stu.getPolishOlympiad().getLabel().equals("Laureat");
            boolean stuPolF = stu.getPolishOlympiad().getLabel().equals("Finalista");
            boolean stuEngL = stu.getEnglishOlympiad().getLabel().equals("Laureat");
            boolean stuEngF = stu.getEnglishOlympiad().getLabel().equals("Finalista");
            boolean stuGerL = stu.getGermanOlympiad().getLabel().equals("Laureat");
            boolean stuGerF = stu.getGermanOlympiad().getLabel().equals("Finalista");
            boolean stuFreL = stu.getFrenchOlympiad().getLabel().equals("Laureat");
            boolean stuFreF = stu.getFrenchOlympiad().getLabel().equals("Finalista");
            boolean stuSpaL = stu.getSpanishOlympiad().getLabel().equals("Laureat");
            boolean stuSpaF = stu.getSpanishOlympiad().getLabel().equals("Finalista");
            boolean stuItaL = stu.getItalianOlympiad().getLabel().equals("Laureat");
            boolean stuItaF = stu.getItalianOlympiad().getLabel().equals("Finalista");
            boolean stuChemL = stu.getChemistryOlympiad().getLabel().equals("Laureat");
            boolean stuChemF = stu.getChemistryOlympiad().getLabel().equals("Finalista");
            boolean stuPhyL = stu.getPhysicsOlympiad().getLabel().equals("Laureat");
            boolean stuPhyF = stu.getPhysicsOlympiad().getLabel().equals("Finalista");
            boolean stuBioL = stu.getBiologyOlympiad().getLabel().equals("Laureat");
            boolean stuBioF = stu.getBiologyOlympiad().getLabel().equals("Finalista");
            boolean stuGeoL = stu.getGeographyOlympiad().getLabel().equals("Laureat");
            boolean stuGeoF = stu.getGeographyOlympiad().getLabel().equals("Finalista");
            boolean stuWosL = stu.getCivicsOlympiad().getLabel().equals("Laureat");
            boolean stuWosF = stu.getCivicsOlympiad().getLabel().equals("Finalista");
            boolean stuHisL = stu.getHistoryOlympiad().getLabel().equals("Laureat");
            boolean stuHisF = stu.getHistoryOlympiad().getLabel().equals("Finalista");
            boolean stuHisML = stu.getHistoryOfMusicOlympiad().getLabel().equals("Laureat");
            boolean stuHisMF = stu.getHistoryOfMusicOlympiad().getLabel().equals("Finalista");
            boolean stuHisAL = stu.getHistoryOfArtOlympiad().getLabel().equals("Laureat");
            boolean stuHisAF = stu.getHistoryOfArtOlympiad().getLabel().equals("Finalista");
//Na potrzeby pracy zakładam, że uczeń nie może mieć więcej niż dwóch olimpiad
// Zrobić warunek na max 2 olimpiady
            //Matematyka
            if (stuMathL) {
                liczba = 100 * 0.20;
                if ((stuMathL && stuPolL) || (stuMathL && stuEngL) || (stuMathL && stuItL) || (stuMathL && stuFreL)
                        || (stuMathL && stuGerL) || (stuMathL && stuSpaL) || (stuMathL && stuItaL) || (stuMathL && stuChemL)
                        || (stuMathL && stuPhyL) || (stuMathL && stuBioL) || (stuMathL && stuGeoL) || (stuMathL && stuWosL)
                        || (stuMathL && stuHisL) || (stuMathL && stuHisML) || (stuMathL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuMathL && stuPolF) || (stuMathL && stuEngF) || (stuMathL && stuItF) || (stuMathL && stuFreF)
                        || (stuMathL && stuGerF) || (stuMathL && stuSpaF) || (stuMathL && stuItaF) || (stuMathL && stuChemF)
                        || (stuMathL && stuPhyF) || (stuMathL && stuBioF) || (stuMathL && stuGeoF) || (stuMathL && stuWosF)
                        || (stuMathL && stuHisF) || (stuMathL && stuHisMF) || (stuMathL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuMathF) {
                liczba = 100 * 0.10;
                if ((stuMathF && stuPolF) || (stuMathF && stuEngF) || (stuMathF && stuItF) || (stuMathF && stuFreF)
                        || (stuMathF && stuGerF) || (stuMathF && stuSpaF) || (stuMathF && stuItaF) || (stuMathF && stuChemF)
                        || (stuMathF && stuPhyF) || (stuMathF && stuBioF) || (stuMathF && stuGeoF) || (stuMathF && stuWosF)
                        || (stuMathF && stuHisF) || (stuMathF && stuHisMF) || (stuMathF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuMathF && stuPolL) || (stuMathF && stuEngL) || (stuMathF && stuItL) || (stuMathF && stuFreL)
                        || (stuMathF && stuGerL) || (stuMathF && stuSpaL) || (stuMathF && stuItaL) || (stuMathF && stuChemL)
                        || (stuMathF && stuPhyL) || (stuMathF && stuBioL) || (stuMathF && stuGeoL) || (stuMathF && stuWosL)
                        || (stuMathF && stuHisL) || (stuMathF && stuHisML) || (stuMathF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }
            //Polski
            if (stuPolL) {
                liczba = 100 * 0.20;
                if ((stuPolL && stuMathL) || (stuPolL && stuEngL) || (stuPolL && stuItL) || (stuPolL && stuFreL)
                        || (stuPolL && stuGerL) || (stuPolL && stuSpaL) || (stuPolL && stuItaL) || (stuPolL && stuChemL)
                        || (stuPolL && stuPhyL) || (stuPolL && stuBioL) || (stuPolL && stuGeoL) || (stuPolL && stuWosL)
                        || (stuPolL && stuHisL) || (stuPolL && stuHisML) || (stuPolL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuPolL && stuMathF) || (stuPolL && stuEngF) || (stuPolL && stuItF) || (stuPolL && stuFreF)
                        || (stuPolL && stuGerF) || (stuPolL && stuSpaF) || (stuPolL && stuItaF) || (stuPolL && stuChemF)
                        || (stuPolL && stuPhyF) || (stuPolL && stuBioF) || (stuPolL && stuGeoF) || (stuPolL && stuWosF)
                        || (stuPolL && stuHisF) || (stuPolL && stuHisMF) || (stuPolL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuPolF) {
                liczba = 100 * 0.10;
                if ((stuPolF && stuMathF) || (stuPolF && stuEngF) || (stuPolF && stuItF) || (stuPolF && stuFreF)
                        || (stuPolF && stuGerF) || (stuPolF && stuSpaF) || (stuPolF && stuItaF) || (stuPolF && stuChemF)
                        || (stuPolF && stuPhyF) || (stuPolF && stuBioF) || (stuPolF && stuGeoF) || (stuPolF && stuWosF)
                        || (stuPolF && stuHisF) || (stuPolF && stuHisMF) || (stuPolF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuPolF && stuMathL) || (stuPolF && stuEngL) || (stuPolF && stuItL) || (stuPolF && stuFreL)
                        || (stuPolF && stuGerL) || (stuPolF && stuSpaL) || (stuPolF && stuItaL) || (stuPolF && stuChemL)
                        || (stuPolF && stuPhyL) || (stuPolF && stuBioL) || (stuPolF && stuGeoL) || (stuPolF && stuWosL)
                        || (stuPolF && stuHisL) || (stuPolF && stuHisML) || (stuPolF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Angielski
            if (stuEngL) {
                liczba = 100 * 0.20;
                if ((stuEngL && stuMathL) || (stuEngL && stuPolL) || (stuEngL && stuItL) || (stuEngL && stuFreL)
                        || (stuEngL && stuGerL) || (stuEngL && stuSpaL) || (stuEngL && stuItaL) || (stuEngL && stuChemL)
                        || (stuEngL && stuPhyL) || (stuEngL && stuBioL) || (stuEngL && stuGeoL) || (stuEngL && stuWosL)
                        || (stuEngL && stuHisL) || (stuEngL && stuHisML) || (stuEngL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuEngL && stuMathF) || (stuEngL && stuPolF) || (stuEngL && stuItF) || (stuEngL && stuFreF)
                        || (stuEngL && stuGerF) || (stuEngL && stuSpaF) || (stuEngL && stuItaF) || (stuEngL && stuChemF)
                        || (stuEngL && stuPhyF) || (stuEngL && stuBioF) || (stuEngL && stuGeoF) || (stuEngL && stuWosF)
                        || (stuEngL && stuHisF) || (stuEngL && stuHisMF) || (stuEngL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuEngF) {
                liczba = 100 * 0.10;
                if ((stuEngF && stuMathF) || (stuEngF && stuPolF) || (stuEngF && stuItF) || (stuEngF && stuFreF)
                        || (stuEngF && stuGerF) || (stuEngF && stuSpaF) || (stuEngF && stuItaF) || (stuEngF && stuChemF)
                        || (stuEngF && stuPhyF) || (stuEngF && stuBioF) || (stuEngF && stuGeoF) || (stuEngF && stuWosF)
                        || (stuEngF && stuHisF) || (stuEngF && stuHisMF) || (stuEngF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuEngF && stuMathL) || (stuEngF && stuPolL) || (stuEngF && stuItL) || (stuEngF && stuFreL)
                        || (stuEngF && stuGerL) || (stuEngF && stuSpaL) || (stuEngF && stuItaL) || (stuEngF && stuChemL)
                        || (stuEngF && stuPhyL) || (stuEngF && stuBioL) || (stuEngF && stuGeoL) || (stuEngF && stuWosL)
                        || (stuEngF && stuHisL) || (stuEngF && stuHisML) || (stuEngF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Niemiecki
            if (stuGerL) {
                liczba = 100 * 0.20;
                if ((stuGerL && stuMathL) || (stuGerL && stuPolL) || (stuGerL && stuItL) || (stuGerL && stuFreL)
                        || (stuGerL && stuEngL) || (stuGerL && stuSpaL) || (stuGerL && stuItaL) || (stuGerL && stuChemL)
                        || (stuGerL && stuPhyL) || (stuGerL && stuBioL) || (stuGerL && stuGeoL) || (stuGerL && stuWosL)
                        || (stuGerL && stuHisL) || (stuGerL && stuHisML) || (stuGerL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuGerL && stuMathF) || (stuGerL && stuPolF) || (stuGerL && stuItF) || (stuGerL && stuFreF)
                        || (stuGerL && stuEngF) || (stuGerL && stuSpaF) || (stuGerL && stuItaF) || (stuGerL && stuChemF)
                        || (stuGerL && stuPhyF) || (stuGerL && stuBioF) || (stuGerL && stuGeoF) || (stuGerL && stuWosF)
                        || (stuGerL && stuHisF) || (stuGerL && stuHisMF) || (stuGerL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuGerF) {
                liczba = 100 * 0.10;
                if ((stuGerF && stuMathF) || (stuGerF && stuPolF) || (stuGerF && stuItF) || (stuGerF && stuFreF)
                        || (stuGerF && stuEngF) || (stuGerF && stuSpaF) || (stuGerF && stuItaF) || (stuGerF && stuChemF)
                        || (stuGerF && stuPhyF) || (stuGerF && stuBioF) || (stuGerF && stuGeoF) || (stuGerF && stuWosF)
                        || (stuGerF && stuHisF) || (stuGerF && stuHisMF) || (stuGerF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuGerF && stuMathL) || (stuGerF && stuPolL) || (stuGerF && stuItL) || (stuGerF && stuFreL)
                        || (stuGerF && stuEngL) || (stuGerF && stuSpaL) || (stuGerF && stuItaL) || (stuGerF && stuChemL)
                        || (stuGerF && stuPhyL) || (stuGerF && stuBioL) || (stuGerF && stuGeoL) || (stuGerF && stuWosL)
                        || (stuGerF && stuHisL) || (stuGerF && stuHisML) || (stuGerF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Hiszpański
            if (stuSpaL) {
                liczba = 100 * 0.20;
                if ((stuSpaL && stuMathL) || (stuSpaL && stuPolL) || (stuSpaL && stuItL) || (stuSpaL && stuFreL)
                        || (stuSpaL && stuEngL) || (stuSpaL && stuGerL) || (stuSpaL && stuItaL) || (stuSpaL && stuChemL)
                        || (stuSpaL && stuPhyL) || (stuSpaL && stuBioL) || (stuSpaL && stuGeoL) || (stuSpaL && stuWosL)
                        || (stuSpaL && stuHisL) || (stuSpaL && stuHisML) || (stuSpaL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuSpaL && stuMathF) || (stuSpaL && stuPolF) || (stuSpaL && stuItF) || (stuSpaL && stuFreF)
                        || (stuSpaL && stuEngF) || (stuSpaL && stuGerF) || (stuSpaL && stuItaF) || (stuSpaL && stuChemF)
                        || (stuSpaL && stuPhyF) || (stuSpaL && stuBioF) || (stuSpaL && stuGeoF) || (stuSpaL && stuWosF)
                        || (stuSpaL && stuHisF) || (stuSpaL && stuHisMF) || (stuSpaL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuSpaF) {
                liczba = 100 * 0.10;
                if ((stuSpaF && stuMathF) || (stuSpaF && stuPolF) || (stuSpaF && stuItF) || (stuSpaF && stuFreF)
                        || (stuSpaF && stuEngF) || (stuSpaF && stuGerF) || (stuSpaF && stuItaF) || (stuSpaF && stuChemF)
                        || (stuSpaF && stuPhyF) || (stuSpaF && stuBioF) || (stuSpaF && stuGeoF) || (stuSpaF && stuWosF)
                        || (stuSpaF && stuHisF) || (stuSpaF && stuHisMF) || (stuSpaF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuSpaF && stuMathL) || (stuSpaF && stuPolL) || (stuSpaF && stuItL) || (stuSpaF && stuFreL)
                        || (stuSpaF && stuEngL) || (stuSpaF && stuGerL) || (stuSpaF && stuItaL) || (stuSpaF && stuChemL)
                        || (stuSpaF && stuPhyL) || (stuSpaF && stuBioL) || (stuSpaF && stuGeoL) || (stuSpaF && stuWosL)
                        || (stuSpaF && stuHisL) || (stuSpaF && stuHisML) || (stuSpaF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Francuski
            if (stuFreL) {
                liczba = 100 * 0.20;
                if ((stuFreL && stuMathL) || (stuFreL && stuPolL) || (stuFreL && stuItL) || (stuFreL && stuSpaL)
                        || (stuFreL && stuEngL) || (stuFreL && stuGerL) || (stuFreL && stuItaL) || (stuFreL && stuChemL)
                        || (stuFreL && stuPhyL) || (stuFreL && stuBioL) || (stuFreL && stuGeoL) || (stuFreL && stuWosL)
                        || (stuFreL && stuHisL) || (stuFreL && stuHisML) || (stuFreL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuFreL && stuMathF) || (stuFreL && stuPolF) || (stuFreL && stuItF) || (stuFreL && stuSpaF)
                        || (stuFreL && stuEngF) || (stuFreL && stuGerF) || (stuFreL && stuItaF) || (stuFreL && stuChemF)
                        || (stuFreL && stuPhyF) || (stuFreL && stuBioF) || (stuFreL && stuGeoF) || (stuFreL && stuWosF)
                        || (stuFreL && stuHisF) || (stuFreL && stuHisMF) || (stuFreL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuFreF) {
                liczba = 100 * 0.10;
                if ((stuFreF && stuMathF) || (stuFreF && stuPolF) || (stuFreF && stuItF) || (stuFreF && stuSpaF)
                        || (stuFreF && stuEngF) || (stuFreF && stuGerF) || (stuFreF && stuItaF) || (stuFreF && stuChemF)
                        || (stuFreF && stuPhyF) || (stuFreF && stuBioF) || (stuFreF && stuGeoF) || (stuFreF && stuWosF)
                        || (stuFreF && stuHisF) || (stuFreF && stuHisMF) || (stuFreF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuFreF && stuMathL) || (stuFreF && stuPolL) || (stuFreF && stuItL) || (stuFreF && stuSpaL)
                        || (stuFreF && stuEngL) || (stuFreF && stuGerL) || (stuFreF && stuItaL) || (stuFreF && stuChemL)
                        || (stuFreF && stuPhyL) || (stuFreF && stuBioL) || (stuFreF && stuGeoL) || (stuFreF && stuWosL)
                        || (stuFreF && stuHisL) || (stuFreF && stuHisML) || (stuFreF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Włoski
            if (stuItaL) {
                liczba = 100 * 0.20;
                if ((stuItaL && stuMathL) || (stuItaL && stuPolL) || (stuItaL && stuItL) || (stuItaL && stuSpaL)
                        || (stuItaL && stuEngL) || (stuItaL && stuGerL) || (stuItaL && stuFreL) || (stuItaL && stuChemL)
                        || (stuItaL && stuPhyL) || (stuItaL && stuBioL) || (stuItaL && stuGeoL) || (stuItaL && stuWosL)
                        || (stuItaL && stuHisL) || (stuItaL && stuHisML) || (stuItaL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuItaL && stuMathF) || (stuItaL && stuPolF) || (stuItaL && stuItF) || (stuItaL && stuSpaF)
                        || (stuItaL && stuEngF) || (stuItaL && stuGerF) || (stuItaL && stuFreF) || (stuItaL && stuChemF)
                        || (stuItaL && stuPhyF) || (stuItaL && stuBioF) || (stuItaL && stuGeoF) || (stuItaL && stuWosF)
                        || (stuItaL && stuHisF) || (stuItaL && stuHisMF) || (stuItaL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuItaF) {
                liczba = 100 * 0.10;
                if ((stuItaF && stuMathF) || (stuItaF && stuPolF) || (stuItaF && stuItF) || (stuItaF && stuSpaF)
                        || (stuItaF && stuEngF) || (stuItaF && stuGerF) || (stuItaF && stuFreF) || (stuItaF && stuChemF)
                        || (stuItaF && stuPhyF) || (stuItaF && stuBioF) || (stuItaF && stuGeoF) || (stuItaF && stuWosF)
                        || (stuItaF && stuHisF) || (stuItaF && stuHisMF) || (stuItaF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuItaF && stuMathL) || (stuItaF && stuPolL) || (stuItaF && stuItL) || (stuItaF && stuSpaL)
                        || (stuItaF && stuEngL) || (stuItaF && stuGerL) || (stuItaF && stuFreL) || (stuItaF && stuChemL)
                        || (stuItaF && stuPhyL) || (stuItaF && stuBioL) || (stuItaF && stuGeoL) || (stuItaF && stuWosL)
                        || (stuItaF && stuHisL) || (stuItaF && stuHisML) || (stuItaF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Informatyka
            if (stuItL) {
                liczba = 100 * 0.20;
                if ((stuItL && stuMathL) || (stuItL && stuPolL) || (stuItL && stuItaL) || (stuItL && stuSpaL)
                        || (stuItL && stuEngL) || (stuItL && stuGerL) || (stuItL && stuFreL) || (stuItL && stuChemL)
                        || (stuItL && stuPhyL) || (stuItL && stuBioL) || (stuItL && stuGeoL) || (stuItL && stuWosL)
                        || (stuItL && stuHisL) || (stuItL && stuHisML) || (stuItL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuItL && stuMathF) || (stuItL && stuPolF) || (stuItL && stuItaF) || (stuItL && stuSpaF)
                        || (stuItL && stuEngF) || (stuItL && stuGerF) || (stuItL && stuFreF) || (stuItL && stuChemF)
                        || (stuItL && stuPhyF) || (stuItL && stuBioF) || (stuItL && stuGeoF) || (stuItL && stuWosF)
                        || (stuItL && stuHisF) || (stuItL && stuHisMF) || (stuItL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuItF) {
                liczba = 100 * 0.10;
                if ((stuItF && stuMathF) || (stuItF && stuPolF) || (stuItF && stuItaF) || (stuItF && stuSpaF)
                        || (stuItF && stuEngF) || (stuItF && stuGerF) || (stuItF && stuFreF) || (stuItF && stuChemF)
                        || (stuItF && stuPhyF) || (stuItF && stuBioF) || (stuItF && stuGeoF) || (stuItF && stuWosF)
                        || (stuItF && stuHisF) || (stuItF && stuHisMF) || (stuItF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuItF && stuMathL) || (stuItF && stuPolL) || (stuItF && stuItaL) || (stuItF && stuSpaL)
                        || (stuItF && stuEngL) || (stuItF && stuGerL) || (stuItF && stuFreL) || (stuItF && stuChemL)
                        || (stuItF && stuPhyL) || (stuItF && stuBioL) || (stuItF && stuGeoL) || (stuItF && stuWosL)
                        || (stuItF && stuHisL) || (stuItF && stuHisML) || (stuItF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //chemia
            if (stuChemL) {
                liczba = 100 * 0.20;
                if ((stuChemL && stuMathL) || (stuChemL && stuPolL) || (stuChemL && stuItaL) || (stuChemL && stuSpaL)
                        || (stuChemL && stuEngL) || (stuChemL && stuGerL) || (stuChemL && stuFreL) || (stuChemL && stuItL)
                        || (stuChemL && stuPhyL) || (stuChemL && stuBioL) || (stuChemL && stuGeoL) || (stuChemL && stuWosL)
                        || (stuChemL && stuHisL) || (stuChemL && stuHisML) || (stuChemL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuChemL && stuMathF) || (stuChemL && stuPolF) || (stuChemL && stuItaF) || (stuChemL && stuSpaF)
                        || (stuChemL && stuEngF) || (stuChemL && stuGerF) || (stuChemL && stuFreF) || (stuChemL && stuItF)
                        || (stuChemL && stuPhyF) || (stuChemL && stuBioF) || (stuChemL && stuGeoF) || (stuChemL && stuWosF)
                        || (stuChemL && stuHisF) || (stuChemL && stuHisMF) || (stuChemL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuChemF) {
                liczba = 100 * 0.10;
                if ((stuChemF && stuMathF) || (stuChemF && stuPolF) || (stuChemF && stuItaF) || (stuChemF && stuSpaF)
                        || (stuChemF && stuEngF) || (stuChemF && stuGerF) || (stuChemF && stuFreF) || (stuChemF && stuItF)
                        || (stuChemF && stuPhyF) || (stuChemF && stuBioF) || (stuChemF && stuGeoF) || (stuChemF && stuWosF)
                        || (stuChemF && stuHisF) || (stuChemF && stuHisMF) || (stuChemF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuChemF && stuMathL) || (stuChemF && stuPolL) || (stuChemF && stuItaL) || (stuChemF && stuSpaL)
                        || (stuChemF && stuEngL) || (stuChemF && stuGerL) || (stuChemF && stuFreL) || (stuChemF && stuItL)
                        || (stuChemF && stuPhyL) || (stuChemF && stuBioL) || (stuChemF && stuGeoL) || (stuChemF && stuWosL)
                        || (stuChemF && stuHisL) || (stuChemF && stuHisML) || (stuChemF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Fizyka
            if (stuPhyL) {
                liczba = 100 * 0.20;
                if ((stuPhyL && stuMathL) || (stuPhyL && stuPolL) || (stuPhyL && stuItaL) || (stuPhyL && stuSpaL)
                        || (stuPhyL && stuEngL) || (stuPhyL && stuGerL) || (stuPhyL && stuFreL) || (stuPhyL && stuItL)
                        || (stuPhyL && stuChemL) || (stuPhyL && stuBioL) || (stuPhyL && stuGeoL) || (stuPhyL && stuWosL)
                        || (stuPhyL && stuHisL) || (stuPhyL && stuHisML) || (stuPhyL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuPhyL && stuMathF) || (stuPhyL && stuPolF) || (stuPhyL && stuItaF) || (stuPhyL && stuSpaF)
                        || (stuPhyL && stuEngF) || (stuPhyL && stuGerF) || (stuPhyL && stuFreF) || (stuPhyL && stuItF)
                        || (stuPhyL && stuChemF) || (stuPhyL && stuBioF) || (stuPhyL && stuGeoF) || (stuPhyL && stuWosF)
                        || (stuPhyL && stuHisF) || (stuPhyL && stuHisMF) || (stuPhyL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuPhyF) {
                liczba = 100 * 0.10;
                if ((stuPhyF && stuMathF) || (stuPhyF && stuPolF) || (stuPhyF && stuItaF) || (stuPhyF && stuSpaF)
                        || (stuPhyF && stuEngF) || (stuPhyF && stuGerF) || (stuPhyF && stuFreF) || (stuPhyF && stuItF)
                        || (stuPhyF && stuChemF) || (stuPhyF && stuBioF) || (stuPhyF && stuGeoF) || (stuPhyF && stuWosF)
                        || (stuPhyF && stuHisF) || (stuPhyF && stuHisMF) || (stuPhyF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuPhyF && stuMathL) || (stuPhyF && stuPolL) || (stuPhyF && stuItaL) || (stuPhyF && stuSpaL)
                        || (stuPhyF && stuEngL) || (stuPhyF && stuGerL) || (stuPhyF && stuFreL) || (stuPhyF && stuItL)
                        || (stuPhyF && stuChemL) || (stuPhyF && stuBioL) || (stuPhyF && stuGeoL) || (stuPhyF && stuWosL)
                        || (stuPhyF && stuHisL) || (stuPhyF && stuHisML) || (stuPhyF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }
            //Biologia
            if (stuBioL) {
                liczba = 100 * 0.20;
                if ((stuBioL && stuMathL) || (stuBioL && stuPolL) || (stuBioL && stuItaL) || (stuBioL && stuSpaL)
                        || (stuBioL && stuEngL) || (stuBioL && stuGerL) || (stuBioL && stuFreL) || (stuBioL && stuItL)
                        || (stuBioL && stuChemL) || (stuBioL && stuPhyL) || (stuBioL && stuGeoL) || (stuBioL && stuWosL)
                        || (stuBioL && stuHisL) || (stuBioL && stuHisML) || (stuBioL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuBioL && stuMathF) || (stuBioL && stuPolF) || (stuBioL && stuItaF) || (stuBioL && stuSpaF)
                        || (stuBioL && stuEngF) || (stuBioL && stuGerF) || (stuBioL && stuFreF) || (stuBioL && stuItF)
                        || (stuBioL && stuChemF) || (stuBioL && stuPhyF) || (stuBioL && stuGeoF) || (stuBioL && stuWosF)
                        || (stuBioL && stuHisF) || (stuBioL && stuHisMF) || (stuBioL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuBioF) {
                liczba = 100 * 0.10;
                if ((stuBioF && stuMathF) || (stuBioF && stuPolF) || (stuBioF && stuItaF) || (stuBioF && stuSpaF)
                        || (stuBioF && stuEngF) || (stuBioF && stuGerF) || (stuBioF && stuFreF) || (stuBioF && stuItF)
                        || (stuBioF && stuChemF) || (stuBioF && stuPhyF) || (stuBioF && stuGeoF) || (stuBioF && stuWosF)
                        || (stuBioF && stuHisF) || (stuBioF && stuHisMF) || (stuBioF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuBioF && stuMathL) || (stuBioF && stuPolL) || (stuBioF && stuItaL) || (stuBioF && stuSpaL)
                        || (stuBioF && stuEngL) || (stuBioF && stuGerL) || (stuBioF && stuFreL) || (stuBioF && stuItL)
                        || (stuBioF && stuChemL) || (stuBioF && stuPhyL) || (stuBioF && stuGeoL) || (stuBioF && stuWosL)
                        || (stuBioF && stuHisL) || (stuBioF && stuHisML) || (stuBioF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }
            //Geografia
            if (stuGeoL) {
                liczba = 100 * 0.20;
                if ((stuGeoL && stuMathL) || (stuGeoL && stuPolL) || (stuGeoL && stuItaL) || (stuGeoL && stuSpaL)
                        || (stuGeoL && stuEngL) || (stuGeoL && stuGerL) || (stuGeoL && stuFreL) || (stuGeoL && stuItL)
                        || (stuGeoL && stuChemL) || (stuGeoL && stuPhyL) || (stuGeoL && stuBioL) || (stuGeoL && stuWosL)
                        || (stuGeoL && stuHisL) || (stuGeoL && stuHisML) || (stuGeoL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuGeoL && stuMathF) || (stuGeoL && stuPolF) || (stuGeoL && stuItaF) || (stuGeoL && stuSpaF)
                        || (stuGeoL && stuEngF) || (stuGeoL && stuGerF) || (stuGeoL && stuFreF) || (stuGeoL && stuItF)
                        || (stuGeoL && stuChemF) || (stuGeoL && stuPhyF) || (stuGeoL && stuBioF) || (stuGeoL && stuWosF)
                        || (stuGeoL && stuHisF) || (stuGeoL && stuHisMF) || (stuGeoL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuGeoF) {
                liczba = 100 * 0.10;
                if ((stuGeoF && stuMathF) || (stuGeoF && stuPolF) || (stuGeoF && stuItaF) || (stuGeoF && stuSpaF)
                        || (stuGeoF && stuEngF) || (stuGeoF && stuGerF) || (stuGeoF && stuFreF) || (stuGeoF && stuItF)
                        || (stuGeoF && stuChemF) || (stuGeoF && stuPhyF) || (stuGeoF && stuBioF) || (stuGeoF && stuWosF)
                        || (stuGeoF && stuHisF) || (stuGeoF && stuHisMF) || (stuGeoF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuGeoF && stuMathL) || (stuGeoF && stuPolL) || (stuGeoF && stuItaL) || (stuGeoF && stuSpaL)
                        || (stuGeoF && stuEngL) || (stuGeoF && stuGerL) || (stuGeoF && stuFreL) || (stuGeoF && stuItL)
                        || (stuGeoF && stuChemL) || (stuGeoF && stuPhyL) || (stuGeoF && stuBioL) || (stuGeoF && stuWosL)
                        || (stuGeoF && stuHisL) || (stuGeoF && stuHisML) || (stuGeoF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //WOS
            if (stuWosL) {
                liczba = 100 * 0.20;
                if ((stuWosL && stuMathL) || (stuWosL && stuPolL) || (stuWosL && stuItaL) || (stuWosL && stuSpaL)
                        || (stuWosL && stuEngL) || (stuWosL && stuGerL) || (stuWosL && stuFreL) || (stuWosL && stuItL)
                        || (stuWosL && stuChemL) || (stuWosL && stuPhyL) || (stuWosL && stuBioL) || (stuWosL && stuGeoL)
                        || (stuWosL && stuHisL) || (stuWosL && stuHisML) || (stuWosL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuWosL && stuMathF) || (stuWosL && stuPolF) || (stuWosL && stuItaF) || (stuWosL && stuSpaF)
                        || (stuWosL && stuEngF) || (stuWosL && stuGerF) || (stuWosL && stuFreF) || (stuWosL && stuItF)
                        || (stuWosL && stuChemF) || (stuWosL && stuPhyF) || (stuWosL && stuBioF) || (stuWosL && stuGeoF)
                        || (stuWosL && stuHisF) || (stuWosL && stuHisMF) || (stuWosL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuWosF) {
                liczba = 100 * 0.10;
                if ((stuWosF && stuMathF) || (stuWosF && stuPolF) || (stuGeoF && stuItaF) || (stuWosF && stuSpaF)
                        || (stuWosF && stuEngF) || (stuWosF && stuGerF) || (stuWosF && stuFreF) || (stuWosF && stuItF)
                        || (stuWosF && stuChemF) || (stuWosF && stuPhyF) || (stuWosF && stuBioF) || (stuWosF && stuGeoF)
                        || (stuWosF && stuHisF) || (stuWosF && stuHisMF) || (stuWosF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuWosF && stuMathL) || (stuWosF && stuPolL) || (stuWosF && stuItaL) || (stuWosF && stuSpaL)
                        || (stuWosF && stuEngL) || (stuWosF && stuGerL) || (stuWosF && stuFreL) || (stuWosF && stuItL)
                        || (stuWosF && stuChemL) || (stuWosF && stuPhyL) || (stuWosF && stuBioL) || (stuWosF && stuGeoL)
                        || (stuWosF && stuHisL) || (stuWosF && stuHisML) || (stuWosF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Historia
            if (stuHisL) {
                liczba = 100 * 0.20;
                if ((stuWosL && stuMathL) || (stuHisL && stuPolL) || (stuHisL && stuItaL) || (stuHisL && stuSpaL)
                        || (stuHisL && stuEngL) || (stuHisL && stuGerL) || (stuHisL && stuFreL) || (stuHisL && stuItL)
                        || (stuHisL && stuChemL) || (stuHisL && stuPhyL) || (stuHisL && stuBioL) || (stuHisL && stuGeoL)
                        || (stuHisL && stuWosL) || (stuHisL && stuHisML) || (stuHisL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuHisL && stuMathF) || (stuHisL && stuPolF) || (stuHisL && stuItaF) || (stuHisL && stuSpaF)
                        || (stuHisL && stuEngF) || (stuHisL && stuGerF) || (stuHisL && stuFreF) || (stuHisL && stuItF)
                        || (stuHisL && stuChemF) || (stuHisL && stuPhyF) || (stuHisL && stuBioF) || (stuHisL && stuGeoF)
                        || (stuHisL && stuWosF) || (stuHisL && stuHisMF) || (stuHisL && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuHisF) {
                liczba = 100 * 0.10;
                if ((stuHisF && stuMathF) || (stuHisF && stuPolF) || (stuHisF && stuItaF) || (stuHisF && stuSpaF)
                        || (stuHisF && stuEngF) || (stuHisF && stuGerF) || (stuWosF && stuFreF) || (stuHisF && stuItF)
                        || (stuHisF && stuChemF) || (stuHisF && stuPhyF) || (stuHisF && stuBioF) || (stuHisF && stuGeoF)
                        || (stuHisF && stuWosF) || (stuHisF && stuHisMF) || (stuHisF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuHisF && stuMathL) || (stuHisF && stuPolL) || (stuHisF && stuItaL) || (stuHisF && stuSpaL)
                        || (stuHisF && stuEngL) || (stuHisF && stuGerL) || (stuHisF && stuFreL) || (stuHisF && stuItL)
                        || (stuHisF && stuChemL) || (stuHisF && stuPhyL) || (stuHisF && stuBioL) || (stuHisF && stuGeoL)
                        || (stuHisF && stuWosL) || (stuHisF && stuHisML) || (stuHisF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Historia muzyki
            if (stuHisML) {
                liczba = 100 * 0.20;
                if ((stuHisML && stuMathL) || (stuHisML && stuPolL) || (stuHisML && stuItaL) || (stuHisML && stuSpaL)
                        || (stuHisML && stuEngL) || (stuHisML && stuGerL) || (stuHisML && stuFreL) || (stuHisML && stuItL)
                        || (stuHisML && stuChemL) || (stuHisML && stuPhyL) || (stuHisML && stuBioL) || (stuHisML && stuGeoL)
                        || (stuHisML && stuWosL) || (stuHisML && stuHisL) || (stuHisL && stuHisAL)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuHisML && stuMathF) || (stuHisML && stuPolF) || (stuHisML && stuItaF) || (stuHisML && stuSpaF)
                        || (stuHisML && stuEngF) || (stuHisML && stuGerF) || (stuHisML && stuFreF) || (stuHisML && stuItF)
                        || (stuHisML && stuChemF) || (stuHisML && stuPhyF) || (stuHisML && stuBioF) || (stuHisML && stuGeoF)
                        || (stuHisML && stuWosF) || (stuHisML && stuHisF) || (stuHisML && stuHisAF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuHisMF) {
                liczba = 100 * 0.10;
                if ((stuHisMF && stuMathF) || (stuHisMF && stuPolF) || (stuHisMF && stuItaF) || (stuHisMF && stuSpaF)
                        || (stuHisMF && stuEngF) || (stuHisMF && stuGerF) || (stuHisMF && stuFreF) || (stuHisMF && stuItF)
                        || (stuHisMF && stuChemF) || (stuHisMF && stuPhyF) || (stuHisMF && stuBioF) || (stuHisMF && stuGeoF)
                        || (stuHisMF && stuWosF) || (stuHisMF && stuHisF) || (stuHisMF && stuHisAF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuHisMF && stuMathL) || (stuHisMF && stuPolL) || (stuHisMF && stuItaL) || (stuHisMF && stuSpaL)
                        || (stuHisMF && stuEngL) || (stuHisMF && stuGerL) || (stuHisMF && stuFreL) || (stuHisMF && stuItL)
                        || (stuHisMF && stuChemL) || (stuHisMF && stuPhyL) || (stuHisMF && stuBioL) || (stuHisF && stuGeoL)
                        || (stuHisMF && stuWosL) || (stuHisMF && stuHisL) || (stuHisMF && stuHisAL)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            //Historia sztuki
            if (stuHisAL) {
                liczba = 100 * 0.20;
                if ((stuHisAL && stuMathL) || (stuHisML && stuPolL) || (stuHisAL && stuItaL) || (stuHisAL && stuSpaL)
                        || (stuHisAL && stuEngL) || (stuHisAL && stuGerL) || (stuHisAL && stuFreL) || (stuHisAL && stuItL)
                        || (stuHisAL && stuChemL) || (stuHisAL && stuPhyL) || (stuHisAL && stuBioL) || (stuHisAL && stuGeoL)
                        || (stuHisAL && stuWosL) || (stuHisAL && stuHisL) || (stuHisAL && stuHisML)) {
                    liczba = 2 * (100 * 0.20);
                } else if ((stuHisAL && stuMathF) || (stuHisAL && stuPolF) || (stuHisAL && stuItaF) || (stuHisAL && stuSpaF)
                        || (stuHisAL && stuEngF) || (stuHisAL && stuGerF) || (stuHisAL && stuFreF) || (stuHisAL && stuItF)
                        || (stuHisAL && stuChemF) || (stuHisAL && stuPhyF) || (stuHisAL && stuBioF) || (stuHisAL && stuGeoF)
                        || (stuHisAL && stuWosF) || (stuHisAL && stuHisF) || (stuHisAL && stuHisMF)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            if (stuHisAF) {
                liczba = 100 * 0.10;
                if ((stuHisAF && stuMathF) || (stuHisAF && stuPolF) || (stuHisAF && stuItaF) || (stuHisAF && stuSpaF)
                        || (stuHisAF && stuEngF) || (stuHisAF && stuGerF) || (stuHisAF && stuFreF) || (stuHisAF && stuItF)
                        || (stuHisAF && stuChemF) || (stuHisAF && stuPhyF) || (stuHisAF && stuBioF) || (stuHisAF && stuGeoF)
                        || (stuHisAF && stuWosF) || (stuHisAF && stuHisF) || (stuHisAF && stuHisMF)) {
                    liczba = 2 * (100 * 0.10);
                } else if ((stuHisAF && stuMathL) || (stuHisAF && stuPolL) || (stuHisAF && stuItaL) || (stuHisAF && stuSpaL)
                        || (stuHisAF && stuEngL) || (stuHisAF && stuGerL) || (stuHisAF && stuFreL) || (stuHisAF && stuItL)
                        || (stuHisAF && stuChemL) || (stuHisAF && stuPhyL) || (stuHisAF && stuBioL) || (stuHisAF && stuGeoL)
                        || (stuHisAF && stuWosL) || (stuHisAF && stuHisL) || (stuHisAF && stuHisML)) {
                    liczba = (100 * 0.20) + (100 * 0.10);
                }
            }

            punkty = egzamin + liczba;

        }
        studentToUpdate.setPoints(punkty);
        studentRepository.save(studentToUpdate);

        return studentToUpdate;
    }


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


    public Optional<Student> findUserByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
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

            updateExam(exam, studentToUpdate);
            updateGrade(grade, studentToUpdate);
//            updateOlympiad(olymp, studentToUpdate);
//            updateExtraParam(extrparam, studentToUpdate);

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


}

