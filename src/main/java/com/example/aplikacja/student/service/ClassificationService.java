package com.example.aplikacja.student.service;

import com.example.aplikacja.student.entity.Klasa;
import com.example.aplikacja.student.entity.Student;
import com.example.aplikacja.student.enums.*;
import com.example.aplikacja.student.repository.KlasaRepository;
import com.example.aplikacja.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

        Klasa klasaBiolChemAng = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.BiolChem)).findFirst().get();

        Klasa klasaSportowa = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Sportowa)).findFirst().get();

        Klasa klasaArtystyczna = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.Artystyczna)).findFirst().get();

        Klasa klasaFizChemFranc = klassRepository.findAll().stream().filter(k ->
                k.getNameOfClass().equals(NameOfClass.FizChemFranc)).findFirst().get();


        //Olimpiady
        LaureateOrFinalist lauMat = studentToUpdate.getOlympiads().getMathOlympiad();
        LaureateOrFinalist lauGeo = studentToUpdate.getOlympiads().getGeographyOlympiad();
        LaureateOrFinalist lauInf = studentToUpdate.getOlympiads().getITOlympiad();
        LaureateOrFinalist lauAng = studentToUpdate.getOlympiads().getEnglishOlympiad();
        LaureateOrFinalist lauNiem = studentToUpdate.getOlympiads().getGermanOlympiad();
        LaureateOrFinalist lauPol = studentToUpdate.getOlympiads().getPolishOlympiad();
        LaureateOrFinalist lauHist = studentToUpdate.getOlympiads().getHistoryOlympiad();
        LaureateOrFinalist lauWOS = studentToUpdate.getOlympiads().getCivicsOlympiad();
        LaureateOrFinalist lauBio = studentToUpdate.getOlympiads().getBiologyOlympiad();
        LaureateOrFinalist lauChem = studentToUpdate.getOlympiads().getChemistryOlympiad();
        LaureateOrFinalist lauMuzHist = studentToUpdate.getOlympiads().getHistoryOfMusicOlympiad();
        LaureateOrFinalist lauSztHist = studentToUpdate.getOlympiads().getHistoryOfArtOlympiad();
        LaureateOrFinalist lauFiz = studentToUpdate.getOlympiads().getPhysicsOlympiad();
        LaureateOrFinalist lauItal = studentToUpdate.getOlympiads().getItalianOlympiad();
        LaureateOrFinalist lauFra = studentToUpdate.getOlympiads().getFrenchOlympiad();
        LaureateOrFinalist lauSpin = studentToUpdate.getOlympiads().getSpanishOlympiad();

        List<LaureateOrFinalist> olympiads = Arrays.asList(lauMat, lauGeo, lauInf, lauAng, lauNiem, lauPol, lauHist,
                lauWOS, lauBio, lauChem, lauMuzHist, lauSztHist, lauFiz, lauItal, lauFra, lauSpin);


//        List<LaureateOrFinalist> olimpiads = Arrays.asList(lauMat, lauGeo, lauInf, lauAng, lauNiem, lauPol,
//                lauHist, lauWOS, lauBio, lauChem, lauMuzHist, lauSztHist, lauFiz, lauItal, lauFra, lauSpin);
//

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
        String grFiz = studentToUpdate.getGrades().getPhysicsGrade();
        String grChe = studentToUpdate.getGrades().getChemistryGrade();
        String grPol = studentToUpdate.getGrades().getPolishGrade();
        String grMuz = studentToUpdate.getGrades().getMusic();
        String grHis = studentToUpdate.getGrades().getHistoryGrade();
        String grWos = studentToUpdate.getGrades().getCivicsGrade();


//        Double srednia = Double.valueOf(studentToUpdate.getGrades().getAverageOfGrades());

        //Umiejętności

        Ability szbLicz = studentToUpdate.getExtraParameters().getFastCounting();
        Ability szbCzyt = studentToUpdate.getExtraParameters().getFastReading();
        Ability rozProb = studentToUpdate.getExtraParameters().getTroubleshooting();
        Ability szbZapam = studentToUpdate.getExtraParameters().getQuickMemorization();
        Ability aktorstwo = studentToUpdate.getExtraParameters().getActingSkills();
        Ability spiew = studentToUpdate.getExtraParameters().getVocalSkills();
        Ability taniec = studentToUpdate.getExtraParameters().getDanceSkills();
        Ability pisanie = studentToUpdate.getExtraParameters().getWritingSkills();
        Ability foto = studentToUpdate.getExtraParameters().getPhotographicSkills();
        Ability jezyk = studentToUpdate.getExtraParameters().getLinguisticSkills();
        Ability certyf = studentToUpdate.getExtraParameters().getLanguageCertificate();
        Ability polit = studentToUpdate.getExtraParameters().getInterestInPolitics();
        Ability komuni = studentToUpdate.getExtraParameters().getCommunicationSkills();
        Ability sport = studentToUpdate.getExtraParameters().getSportSkills();
        Ability wyczSpo = studentToUpdate.getExtraParameters().getExtremeSport();
        Ability sprFiz = studentToUpdate.getExtraParameters().getPhysicalFitness();
        Ability wytrzFiz = studentToUpdate.getExtraParameters().getPhysicalEndurance();
        Ability otwTer = studentToUpdate.getExtraParameters().getWorkInTheOpenGround();
        Ability mapa = studentToUpdate.getExtraParameters().getAbilityToUseAMap();
        Ability tabMend = studentToUpdate.getExtraParameters().getPeriodicTable();
        Ability chemia = studentToUpdate.getExtraParameters().getChemicalExperiments();
        Ability bioPrzyr = studentToUpdate.getExtraParameters().getBiologicalAndNaturalInterests();
        Ability tech = studentToUpdate.getExtraParameters().getInterestInTechnology();

        double maxMGI = studentToUpdate.getPointsMatGeoInf();  //mat, geo, inf
        double maxS = studentToUpdate.getPointsS(); //mat, bio, wf
        double maxBIO = studentToUpdate.getPointsBiolChem(); //bio, chem, ang
        double maxMAN = studentToUpdate.getPointsMAN(); // mat, ang, niem
        double maxART = studentToUpdate.getPointsArt(); //pol, ang, muz
        double maxHUM = studentToUpdate.getPointsHuman(); //pol, wos, hist
        double maxFIZ = studentToUpdate.getPointsFIZ(); // fiz, chem, fran

        List<Double> punkty = new ArrayList<>();
        punkty.add(studentToUpdate.getPointsMatGeoInf());
        punkty.add(studentToUpdate.getPointsHuman());
        punkty.add(studentToUpdate.getPointsFIZ());
        punkty.add(studentToUpdate.getPointsMAN());
        punkty.add(studentToUpdate.getPointsBiolChem());
        punkty.add(studentToUpdate.getPointsS());
        punkty.add(studentToUpdate.getPointsArt());

        List<Klasa> kl = new ArrayList<>();
        kl.add(klasaArtystyczna);
        kl.add(klasaBiolChemAng);
        kl.add(klasaPol);
        kl.add(klasaSportowa);
        kl.add(klasaMatAngNiem);
        kl.add(klasaMatGeoInf);
        kl.add(klasaFizChemFranc);

//        List<Double> sredniaKl = new ArrayList<>();
//        sredniaKl.add(klasaArtystyczna.getMinAvgGrade());
//        sredniaKl.add(klasaBiolChem.getMinAvgGrade());
//        sredniaKl.add(klasaPol.getMinAvgGrade());
//        sredniaKl.add(klasaFizChemFranc.getMinAvgGrade());
//        sredniaKl.add(klasaMatGeoInf.getMinAvgGrade());
//        sredniaKl.add(klasaSportowa.getMinAvgGrade());
//        sredniaKl.add(klasaMatAngNiem.getMinAvgGrade());

        Double max = 0.0, maxMin = 0.0;
        int i, j;

        AtomicReference<String> name = new AtomicReference<>();
        AtomicReference<Double> wartosc = new AtomicReference<>();


        List<LaureateOrFinalist> listLaureat = new ArrayList<>();
        List<Double> listaMaxLaureat = new ArrayList<>();
        Map<NameOfClass, Double> newMap = new HashMap<>();

        //Jeżeli uczeń ma więcej niż jedną olimpiadę
        Double maxWew = 0.0;
        int licznik = 0;
        for (int k = 0; k < olympiads.size(); k++) {
            if (olympiads.get(k) == LaureateOrFinalist.Laureat) {
                licznik++;
                listLaureat.add(olympiads.get(k)); //lista olimpiad (przedmiotów), z których uczeń jest laureatem
            }
        }

        for (int l = 0; l < listLaureat.size(); l++) {
            if (listLaureat.get(l) == lauMat) {
                if (maxMGI > maxMAN && maxMGI > maxS) {
                    maxWew = maxMGI;
                    newMap.put(NameOfClass.MatGeoInf, maxWew);
                    listaMaxLaureat.add(maxWew);
                } else if (maxMGI > maxMAN && maxMGI < maxS) {
                    maxWew = maxS;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Sportowa, maxWew);
                } else if (maxMGI > maxS && maxMGI < maxMAN) {
                    maxWew = maxMAN;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatAngNiem, maxWew);
                } else if (maxMGI < maxS && maxMGI < maxMAN) {
                    if (maxS < maxMAN) {
                        maxWew = maxMAN;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.MatAngNiem, maxWew);
                    } else if (maxMAN < maxS) {
                        maxWew = maxS;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.Sportowa, maxWew);
                    }
                } else if (maxMAN > maxMGI && maxMAN > maxS) {
                    maxWew = maxMAN;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatAngNiem, maxWew);
                } else if (maxMAN > maxMGI && maxMAN < maxS) {
                    maxWew = maxS;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Sportowa, maxWew);
                } else if (maxMAN > maxS && maxMAN < maxMGI) {
                    maxWew = maxMGI;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatGeoInf, maxWew);
                } else if (maxMAN < maxS && maxMAN < maxMGI) {
                    if (maxMGI < maxS) {
                        maxWew = maxS;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.Sportowa, maxWew);
                    } else if (maxS < maxMGI) {
                        maxWew = maxMGI;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.MatGeoInf, maxWew);
                    }
                } else if (maxS > maxMGI && maxS > maxMAN) {
                    maxWew = maxS;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Sportowa, maxWew);
                } else if (maxS > maxMGI && maxS < maxMAN) {
                    maxWew = maxMAN;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatAngNiem, maxWew);
                } else if (maxS > maxMAN && maxS < maxMGI) {
                    maxWew = maxMGI;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatGeoInf, maxWew);
                } else if (maxS < maxMAN && maxS < maxMGI) {
                    if (maxMAN < maxMGI) {
                        maxWew = maxMGI;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.MatGeoInf, maxWew);
                    } else if (maxMGI < maxMAN) {
                        maxWew = maxMAN;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.MatAngNiem, maxWew);
                    }
                }
            }
            if (listLaureat.get(l) == lauAng) {
                if (maxBIO > maxMAN && maxBIO > maxART) {
                    maxWew = maxBIO;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.BiolChem, maxWew);
                } else if (maxBIO > maxMAN && maxBIO < maxART) { //maxMAN < maxBIO < maxART
                    maxWew = maxART;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Artystyczna, maxWew);
                } else if (maxBIO > maxART && maxBIO < maxMAN) { // maxART < maxBIO < maxMAN
                    maxWew = maxMAN;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatAngNiem, maxWew);
                } else if (maxBIO < maxART && maxBIO < maxMAN) {
                    if (maxART < maxMAN) {
                        maxWew = maxMAN;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.MatAngNiem, maxWew);
                    } else if (maxMAN < maxART) {
                        maxWew = maxART;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.Artystyczna, maxWew);
                    }
                } else if (maxMAN > maxBIO && maxMAN > maxART) {
                    maxWew = maxMAN;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatAngNiem, maxWew);
                } else if (maxMAN > maxBIO && maxMAN < maxART) { //  maxBIO < maxMAN < maxART
                    maxWew = maxART;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Artystyczna, maxWew);
                } else if (maxMAN > maxART && maxMAN < maxBIO) { // maxART < maxMAN <  maxBIO
                    maxWew = maxBIO;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.BiolChem, maxWew);
                } else if (maxMAN < maxART && maxMAN < maxBIO) {
                    if (maxBIO < maxART) {
                        maxWew = maxART;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.Artystyczna, maxWew);
                    } else if (maxART < maxBIO) {
                        maxWew = maxBIO;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.BiolChem, maxWew);
                    }
                } else if (maxART > maxBIO && maxART > maxMAN) {
                    maxWew = maxART;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Artystyczna, maxWew);
                } else if (maxART > maxBIO && maxART < maxMAN) {
                    maxWew = maxMAN;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatAngNiem, maxWew);
                } else if (maxART > maxMAN && maxART < maxBIO) {
                    maxWew = maxBIO;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.BiolChem, maxWew);
                } else if (maxART < maxMAN && maxART < maxBIO) {
                    if (maxMAN < maxBIO) {
                        maxWew = maxBIO;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.BiolChem, maxWew);
                    } else if (maxBIO < maxMAN) {
                        maxWew = maxMAN;
                        listaMaxLaureat.add(maxWew);
                        newMap.put(NameOfClass.MatAngNiem, maxWew);
                    }
                }
            }
            if (listLaureat.get(l) == lauPol) {
                if (maxART < maxHUM) {
                    maxWew = maxHUM;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Humanistyczna, maxWew);
                } else if (maxART > maxHUM) {
                    maxWew = maxART;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Artystyczna, maxWew);
                }
            }

            if (listLaureat.get(l) == lauBio) {
                if (maxBIO < maxS) {
                    maxWew = maxS;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.Sportowa, maxWew);
                } else if (maxBIO > maxS) {
                    maxWew = maxBIO;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.BiolChem, maxWew);
                }
            }
            if (listLaureat.get(l) == lauChem) {
                if (maxBIO > maxFIZ) {
                    maxWew = maxBIO;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.BiolChem, maxWew);
                } else if (maxBIO < maxFIZ) {
                    maxWew = maxFIZ;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.FizChemFranc, maxWew);
                }
            }
            if (listLaureat.get(l) == lauFra) {
                if (maxMAN > maxFIZ) {
                    maxWew = maxMAN;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.MatAngNiem, maxWew);
                } else if (maxMAN < maxFIZ) {
                    maxWew = maxFIZ;
                    listaMaxLaureat.add(maxWew);
                    newMap.put(NameOfClass.FizChemFranc, maxWew);
                }
            }

            if (listLaureat.get(l) == lauItal || listLaureat.get(l) == lauSpin) {
                maxWew = maxMAN;
                listaMaxLaureat.add(maxWew);
                newMap.put(NameOfClass.MatAngNiem, maxWew);
            }

            if (listLaureat.get(l) == lauNiem) {
                maxWew = maxMAN;
                listaMaxLaureat.add(maxWew);
                newMap.put(NameOfClass.MatAngNiem, maxWew);
            }

            if (listLaureat.get(l) == lauGeo || listLaureat.get(l) == lauInf) {
                maxWew = maxMGI;
                listaMaxLaureat.add(maxWew);
                newMap.put(NameOfClass.MatGeoInf, maxWew);
            }
            if (listLaureat.get(l) == lauHist || listLaureat.get(l) == lauWOS) {
                maxWew = maxHUM;
                listaMaxLaureat.add(maxWew);
                newMap.put(NameOfClass.Humanistyczna, maxWew);

            }
            if (listLaureat.get(l) == lauMuzHist || listLaureat.get(l) == lauSztHist) {
                maxWew = maxART;
                listaMaxLaureat.add(maxWew);
                newMap.put(NameOfClass.Artystyczna, maxWew);

            }
            if (listLaureat.get(l) == lauFiz) {
                maxWew = maxART;
                listaMaxLaureat.add(maxWew);
                newMap.put(NameOfClass.FizChemFranc, maxWew);
            }
        }
        newMap.forEach((k, v) -> {
                    if (name.compareAndSet(null, name.get())) {
                        name.set(k.getLabel());
                        wartosc.set(v);
                    }
                    if (v > wartosc.get()) {
                        wartosc.set(v);
                        name.set(k.getLabel());
                    }
                    System.out.println(k + ":" + v);
                }
        );

        studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
        studentToUpdate.setClassForStudent(String.valueOf(name.get()));
        studentToUpdate.setClassificationPoints(maxWew);
        studentToUpdate.setFirstClassification(String.valueOf(name.get()));

        //Maks z punktów ucznia
        for (i = 0; i < punkty.size(); i++) {
            if (punkty.get(i) > max) {
                max = punkty.get(i);
            }
        }
        Map<NameOfClass, Double> newList = new TreeMap<>();

        Double maxSr = 0.0;
        for (j = 0; j < kl.size(); j++) {
            if (max > kl.get(j).getMinAmountOfPointsFromExams()) {
                maxMin = kl.get(j).getMinAmountOfPointsFromExams();
                newList.put(kl.get(j).getNameOfClass(), maxMin);

                newList.forEach((k, v) -> {
                            if (name.compareAndSet(null, name.get())) {
                                name.set(k.getLabel());
                                wartosc.set(v);
                            }
                            if (v > wartosc.get()) {
                                wartosc.set(v);
                                name.set(k.getLabel());
                            }
                            System.out.println(k + ":" + v);
                        }
                );
                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                studentToUpdate.setClassForStudent(String.valueOf(name.get()));
                studentToUpdate.setClassificationPoints(max);
            } else {
                if (szbLicz == Ability.TAK && szbCzyt == Ability.TAK && szbZapam == Ability.TAK &&
                        aktorstwo == Ability.TAK && rozProb == Ability.TAK &&
                        spiew == Ability.TAK && taniec == Ability.TAK &&
                        pisanie == Ability.TAK && foto == Ability.TAK &&
                        jezyk == Ability.TAK && certyf == Ability.TAK &&
                        polit == Ability.TAK && komuni == Ability.TAK &&
                        sport == Ability.TAK && wyczSpo == Ability.TAK &&
                        sprFiz == Ability.TAK && wytrzFiz == Ability.TAK &&
                        otwTer == Ability.TAK && mapa == Ability.TAK &&
                        tabMend == Ability.TAK && chemia == Ability.TAK &&
                        bioPrzyr == Ability.TAK && tech == Ability.TAK) {

                    Random generator = new Random();
                    NameOfClass losowaKl = kl.get(generator.nextInt(kl.size())).getNameOfClass();
                    if (losowaKl.equals(NameOfClass.FizChemFranc)) {
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsFIZ());
                    } else if (losowaKl.equals(NameOfClass.MatGeoInf)) {
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
                    } else if (losowaKl.equals(NameOfClass.BiolChem)) {
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
                    } else if (losowaKl.equals(NameOfClass.Humanistyczna)) {
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsHuman());
                    } else if (losowaKl.equals(NameOfClass.Artystyczna)) {
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
                    } else if (losowaKl.equals(NameOfClass.MatAngNiem)) {
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
                    } else if (losowaKl.equals(NameOfClass.Sportowa)) {
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
                    }
                    studentToUpdate.setClassForStudent(String.valueOf(losowaKl));
                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);

                } else if (szbLicz == Ability.NIE && szbCzyt == Ability.NIE && szbZapam == Ability.NIE &&
                        aktorstwo == Ability.NIE && rozProb == Ability.NIE &&
                        spiew == Ability.NIE && taniec == Ability.NIE &&
                        pisanie == Ability.NIE && foto == Ability.NIE &&
                        jezyk == Ability.NIE && certyf == Ability.NIE &&
                        polit == Ability.NIE && komuni == Ability.NIE &&
                        sport == Ability.NIE && wyczSpo == Ability.NIE &&
                        sprFiz == Ability.NIE && wytrzFiz == Ability.NIE &&
                        otwTer == Ability.NIE && mapa == Ability.NIE &&
                        tabMend == Ability.NIE && chemia == Ability.NIE &&
                        bioPrzyr == Ability.NIE && tech == Ability.NIE) {
                    //TODO: Jeżeli się uda wyświetlić punkty
                    studentToUpdate.setStatus(StudentStatus.rezerwowy);
                    studentToUpdate.setClassForStudent("Uczeń trafił na listę rezerwową");
                } else {
                    if (szbLicz == Ability.TAK && rozProb == Ability.TAK
                            && tech == Ability.TAK && otwTer == Ability.TAK) {
                        studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
                        studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
                    } else {
                        if (szbCzyt == Ability.TAK && pisanie == Ability.TAK
                                && polit == Ability.TAK && komuni == Ability.TAK) {
                            studentToUpdate.setClassForStudent(String.valueOf(klasaPol.getNameOfClass()));
                            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsHuman());
                        } else {
                            if (jezyk == Ability.TAK && szbZapam == Ability.TAK
                                    && certyf == Ability.TAK && rozProb == Ability.TAK) {
                                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
                                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
                            } else {
                                if (bioPrzyr == Ability.TAK && tabMend == Ability.TAK
                                        && chemia == Ability.TAK && jezyk == Ability.TAK) {
                                    studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
                                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
                                } else {
                                    if (sport == Ability.TAK && wyczSpo == Ability.TAK
                                            && szbLicz == Ability.TAK && bioPrzyr == Ability.TAK) {
                                        studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
                                        studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
                                    } else {
                                        if (aktorstwo == Ability.TAK && spiew == Ability.TAK
                                                && taniec == Ability.TAK && pisanie == Ability.TAK) {
                                            studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
                                            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
                                        } else {
                                            if (szbLicz == Ability.TAK && tabMend == Ability.TAK && chemia == Ability.TAK
                                                    && bioPrzyr == Ability.TAK) {
                                                studentToUpdate.setClassForStudent(String.valueOf(klasaFizChemFranc.getNameOfClass()));
                                                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
                                                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsFIZ());
                                            } else {
                                                //TODO: Jeżeli się uda wyświetlić punkty
                                                studentToUpdate.setClassForStudent("Uczeń trafił na listę rezerwową");
                                                studentToUpdate.setStatus(StudentStatus.rezerwowy);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //Jeżeli uczeń ma jedną olimpiadę
//        if (lauMat == LaureateOrFinalist.Laureat) {
//            if (maxMGI > maxMAN && maxMGI > maxS) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//            } else if (maxMGI > maxMAN && maxMGI < maxS) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//            } else if (maxMGI > maxS && maxMGI < maxMAN) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//            } else if (maxMGI < maxS && maxMGI < maxMAN) {
//                if (maxS < maxMAN) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//                } else if (maxMAN < maxS) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//                }
//            } else if (maxMAN > maxMGI && maxMAN > maxS) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//            } else if (maxMAN > maxMGI && maxMAN < maxS) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//            } else if (maxMAN > maxS && maxMAN < maxMGI) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//            } else if (maxMAN < maxS && maxMAN < maxMGI) {
//                if (maxMGI < maxS) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//                } else if (maxS < maxMGI) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//                }
//            } else if (maxS > maxMGI && maxS > maxMAN) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//            } else if (maxS > maxMGI && maxS < maxMAN) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//            } else if (maxS > maxMAN && maxS < maxMGI) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//            } else if (maxS < maxMAN && maxS < maxMGI) {
//                if (maxMAN < maxMGI) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//                } else if (maxMGI < maxMAN) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//                }
//            }
//        } else if (lauAng == LaureateOrFinalist.Laureat) {
//            if (maxBIO > maxMAN && maxBIO > maxART) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//            } else if (maxBIO > maxMAN && maxBIO < maxART) { //maxMAN < maxBIO < maxART
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//            } else if (maxBIO > maxART && maxBIO < maxMAN) { // maxART < maxBIO < maxMAN
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//            } else if (maxBIO < maxART && maxBIO < maxMAN) {
//                if (maxART < maxMAN) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//                } else if (maxMAN < maxART) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//                }
//            } else if (maxMAN > maxBIO && maxMAN > maxART) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//            } else if (maxMAN > maxBIO && maxMAN < maxART) { //  maxBIO < maxMAN < maxART
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//            } else if (maxMAN > maxART && maxMAN < maxBIO) { // maxART < maxMAN <  maxBIO
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//            } else if (maxMAN < maxART && maxMAN < maxBIO) {
//                if (maxBIO < maxART) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//                } else if (maxART < maxBIO) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//                }
//            } else if (maxART > maxBIO && maxART > maxMAN) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//            } else if (maxART > maxBIO && maxART < maxMAN) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//            } else if (maxART > maxMAN && maxART < maxBIO) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//            } else if (maxART < maxMAN && maxART < maxBIO) {
//                if (maxMAN < maxBIO) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//                } else if (maxBIO < maxMAN) {
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//                }
//            }
//        } else if (lauPol == LaureateOrFinalist.Laureat) {
//            if (maxART < maxHUM) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaPol.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsHuman());
//            } else if (maxART > maxHUM) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//            }
//        } else if (lauBio == LaureateOrFinalist.Laureat) {
//            if (maxBIO < maxS) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//            } else if (maxBIO > maxS) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//            }
//        } else if (lauChem == LaureateOrFinalist.Laureat) {
//            if (maxBIO > maxFIZ) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//            } else if (maxBIO < maxFIZ) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaFizChemFranc.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsFIZ());
//            }
//        } else if (lauFra == LaureateOrFinalist.Laureat) {
//            if (maxMAN > maxFIZ) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//            } else if (maxMAN < maxFIZ) {
//                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                studentToUpdate.setClassForStudent(String.valueOf(klasaFizChemFranc.getNameOfClass()));
//                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsFIZ());
//            }
//        } else if (lauItal == LaureateOrFinalist.Laureat || lauSpin == LaureateOrFinalist.Laureat) {
//            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//            studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//
//        } else if (lauNiem == LaureateOrFinalist.Laureat) {
//            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//            studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//
//        } else if (lauGeo == LaureateOrFinalist.Laureat || lauInf == LaureateOrFinalist.Laureat) {
//            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//
//        } else if (lauHist == LaureateOrFinalist.Laureat || lauWOS == LaureateOrFinalist.Laureat) {
//            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//            studentToUpdate.setClassForStudent(String.valueOf(klasaPol.getNameOfClass()));
//            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsHuman());
//
//        } else if (lauMuzHist == LaureateOrFinalist.Laureat || lauSztHist == LaureateOrFinalist.Laureat) {
//            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//            studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//
//        } else if (lauFiz == LaureateOrFinalist.Laureat) {
//            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//            studentToUpdate.setClassForStudent(String.valueOf(klasaFizChemFranc.getNameOfClass()));
//            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsFIZ());
//        } else {
//            //Maks z punktów ucznia
//            for (i = 0; i < punkty.size(); i++) {
//                if (punkty.get(i) > max) {
//                    max = punkty.get(i);
//                }
//            }
//            Map<NameOfClass, Double> newList = new TreeMap<>();
//
//            Double maxSr = 0.0;
//            for (j = 0; j < kl.size(); j++) {
//                if (max > kl.get(j).getMinAmountOfPointsFromExams()) {
//                    maxMin = kl.get(j).getMinAmountOfPointsFromExams();
//                    newList.put(kl.get(j).getNameOfClass(), maxMin);
//
//                    newList.forEach((k, v) -> {
//                                if (name.compareAndSet(null, name.get())) {
//                                    name.set(k.getLabel());
//                                    wartosc.set(v);
//                                }
//                                if (v > wartosc.get()) {
//                                    wartosc.set(v);
//                                    name.set(k.getLabel());
//                                }
//                                System.out.println(k + ":" + v);
//                            }
//                    );
//                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                    studentToUpdate.setClassForStudent(String.valueOf(name.get()));
//                    studentToUpdate.setClassificationPoints(max);
//                } else {
//                    if (szbLicz == Ability.TAK && szbCzyt == Ability.TAK && szbZapam == Ability.TAK &&
//                            aktorstwo == Ability.TAK && rozProb == Ability.TAK &&
//                            spiew == Ability.TAK && taniec == Ability.TAK &&
//                            pisanie == Ability.TAK && foto == Ability.TAK &&
//                            jezyk == Ability.TAK && certyf == Ability.TAK &&
//                            polit == Ability.TAK && komuni == Ability.TAK &&
//                            sport == Ability.TAK && wyczSpo == Ability.TAK &&
//                            sprFiz == Ability.TAK && wytrzFiz == Ability.TAK &&
//                            otwTer == Ability.TAK && mapa == Ability.TAK &&
//                            tabMend == Ability.TAK && chemia == Ability.TAK &&
//                            bioPrzyr == Ability.TAK && tech == Ability.TAK) {
//
//                        Random generator = new Random();
//                        NameOfClass losowaKl = kl.get(generator.nextInt(kl.size())).getNameOfClass();
//                        if(losowaKl.equals(NameOfClass.FizChemFranc)){
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsFIZ());
//                        }else if(losowaKl.equals(NameOfClass.MatGeoInf)){
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//                        }else if(losowaKl.equals(NameOfClass.BiolChem)){
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//                        }else if(losowaKl.equals(NameOfClass.Humanistyczna)){
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsHuman());
//                        }else if(losowaKl.equals(NameOfClass.Artystyczna)){
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//                        }else if(losowaKl.equals(NameOfClass.MatAngNiem)){
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//                        }else if(losowaKl.equals(NameOfClass.Sportowa)){
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//                        }
//                        studentToUpdate.setClassForStudent(String.valueOf(losowaKl));
//                        studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//
//                    } else if (szbLicz == Ability.NIE && szbCzyt == Ability.NIE && szbZapam == Ability.NIE &&
//                            aktorstwo == Ability.NIE && rozProb == Ability.NIE &&
//                            spiew == Ability.NIE && taniec == Ability.NIE &&
//                            pisanie == Ability.NIE && foto == Ability.NIE &&
//                            jezyk == Ability.NIE && certyf == Ability.NIE &&
//                            polit == Ability.NIE && komuni == Ability.NIE &&
//                            sport == Ability.NIE && wyczSpo == Ability.NIE &&
//                            sprFiz == Ability.NIE && wytrzFiz == Ability.NIE &&
//                            otwTer == Ability.NIE && mapa == Ability.NIE &&
//                            tabMend == Ability.NIE && chemia == Ability.NIE &&
//                            bioPrzyr == Ability.NIE && tech == Ability.NIE) {
//                        //TODO: Jeżeli się uda wyświetlić punkty
//                        studentToUpdate.setStatus(StudentStatus.rezerwowy);
//                        studentToUpdate.setClassForStudent("Uczeń trafił na listę rezerwową");
//                    } else {
//                        if (szbLicz == Ability.TAK && rozProb == Ability.TAK
//                                && tech == Ability.TAK && otwTer == Ability.TAK) {
//                            studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMatGeoInf());
//                        } else {
//                            if (szbCzyt == Ability.TAK && pisanie == Ability.TAK
//                                    && polit == Ability.TAK && komuni == Ability.TAK) {
//                                studentToUpdate.setClassForStudent(String.valueOf(klasaPol.getNameOfClass()));
//                                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsHuman());
//                            } else {
//                                if (jezyk == Ability.TAK && szbZapam == Ability.TAK
//                                        && certyf == Ability.TAK && rozProb == Ability.TAK) {
//                                    studentToUpdate.setClassForStudent(String.valueOf(klasaMatAngNiem.getNameOfClass()));
//                                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsMAN());
//                                } else {
//                                    if (bioPrzyr == Ability.TAK && tabMend == Ability.TAK
//                                            && chemia == Ability.TAK && jezyk == Ability.TAK) {
//                                        studentToUpdate.setClassForStudent(String.valueOf(klasaBiolChemAng.getNameOfClass()));
//                                        studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                                        studentToUpdate.setClassificationPoints(studentToUpdate.getPointsBiolChem());
//                                    } else {
//                                        if (sport == Ability.TAK && wyczSpo == Ability.TAK
//                                                && szbLicz == Ability.TAK && bioPrzyr == Ability.TAK) {
//                                            studentToUpdate.setClassForStudent(String.valueOf(klasaSportowa.getNameOfClass()));
//                                            studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                                            studentToUpdate.setClassificationPoints(studentToUpdate.getPointsS());
//                                        } else {
//                                            if (aktorstwo == Ability.TAK && spiew == Ability.TAK
//                                                    && taniec == Ability.TAK && pisanie == Ability.TAK) {
//                                                studentToUpdate.setClassForStudent(String.valueOf(klasaArtystyczna.getNameOfClass()));
//                                                studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                                                studentToUpdate.setClassificationPoints(studentToUpdate.getPointsArt());
//                                            } else {
//                                                if (szbLicz == Ability.TAK && tabMend == Ability.TAK && chemia == Ability.TAK
//                                                        && bioPrzyr == Ability.TAK) {
//                                                    studentToUpdate.setClassForStudent(String.valueOf(klasaFizChemFranc.getNameOfClass()));
//                                                    studentToUpdate.setStatus(StudentStatus.sklasyfikowany);
//                                                    studentToUpdate.setClassificationPoints(studentToUpdate.getPointsFIZ());
//                                                } else {
//                                                    //TODO: Jeżeli się uda wyświetlić punkty
//                                                    studentToUpdate.setClassForStudent("Uczeń trafił na listę rezerwową");
//                                                    studentToUpdate.setStatus(StudentStatus.rezerwowy);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return studentRepository.save(studentToUpdate);
    }


//    else if (lauMat == LaureateOrFinalist.Brak && lauGeo == LaureateOrFinalist.Brak &&
//    lauInf == LaureateOrFinalist.Brak && lauAng == LaureateOrFinalist.Brak &&
//    lauNiem == LaureateOrFinalist.Brak && lauPol == LaureateOrFinalist.Brak &&
//    lauHist == LaureateOrFinalist.Brak && lauWOS == LaureateOrFinalist.Brak &&
//    lauBio == LaureateOrFinalist.Brak && lauChem == LaureateOrFinalist.Brak &&
//    lauMuzHist == LaureateOrFinalist.Brak && lauSztHist == LaureateOrFinalist.Brak &&
//    lauFiz == LaureateOrFinalist.Brak && lauItal == LaureateOrFinalist.Brak &&
//    lauFra == LaureateOrFinalist.Brak && lauSpin == LaureateOrFinalist.Brak)

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
//        Klasa klasaArtystyczna = klassRepository.findAll().stream().filter(k ->
//                k.getNameOfClass().equals(NameOfClass.Artystyczna)).findFirst().get();
//
//
//        //Olimpiady
//        LaureateOrFinalist lauMat = studentToUpdate.getOlympiads().getMathOlympiad();
//        LaureateOrFinalist lauGeo = studentToUpdate.getOlympiads().getGeographyOlympiad();
//        LaureateOrFinalist lauInf = studentToUpdate.getOlympiads().getITOlympiad();
//        LaureateOrFinalist lauAng = studentToUpdate.getOlympiads().getEnglishOlympiad();
//        LaureateOrFinalist lauNiem = studentToUpdate.getOlympiads().getGermanOlympiad();
//        LaureateOrFinalist lauPol = studentToUpdate.getOlympiads().getPolishOlympiad();
//        LaureateOrFinalist lauHist = studentToUpdate.getOlympiads().getHistoryOlympiad();
//        LaureateOrFinalist lauWOS = studentToUpdate.getOlympiads().getCivicsOlympiad();
//        LaureateOrFinalist lauBio = studentToUpdate.getOlympiads().getBiologyOlympiad();
//        LaureateOrFinalist lauChem = studentToUpdate.getOlympiads().getChemistryOlympiad();
//        LaureateOrFinalist lauMuzHist = studentToUpdate.getOlympiads().getHistoryOfMusicOlympiad();
//        LaureateOrFinalist lauItal = studentToUpdate.getOlympiads().getItalianOlympiad();
//
//        //Egzaminy
//        Double examPolish = Double.valueOf(studentToUpdate.getExams().getLanguagePolishResult());
//        Double examMath = Double.valueOf(studentToUpdate.getExams().getMath());
//        Double examEnglish = Double.valueOf(studentToUpdate.getExams().getForeignLanguage());
//
//        //Oceny
//        String grMath = studentToUpdate.getGrades().getMathGrade();
//        String grGeo = studentToUpdate.getGrades().getGeographyGrade();
//        String grInf = studentToUpdate.getGrades().getITGrade();
//        String grAng = studentToUpdate.getGrades().getEnglishGrade();
//        String grNiem = studentToUpdate.getGrades().getOtherLanguageGrade();
//        String grWf = studentToUpdate.getGrades().getPhysicalEducationGrade();
//        String grBio = studentToUpdate.getGrades().getBiologyGrade();
//
//
//        //Jest laureatem
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
//            //przedmiotów dla tej klasy , porównujemy punkty w tych profilach z tym przedmiotem)
//            {
//                studentToUpdate.setClassForStudent(String.valueOf(klasaMatGeoInf.getNameOfClass()));
//                // TODO: tutaj dodać jakieś warunki, uwzględnić punkty
//            }
//            //Jest finalistą
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
//            //jeśli nie jest laureatem i nie jest finalistą
//        } else {
//            if (examPolish < 30.00 || examMath < 30.00 || examEnglish < 30.00) {
//                studentToUpdate.setClassForStudent("Uczeń trafia na listę rezerwową bo ma mniej niż 30% z egzaminu.");
//            }
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
}
