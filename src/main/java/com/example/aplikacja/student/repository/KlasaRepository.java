package com.example.aplikacja.student.repository;

import com.example.aplikacja.student.dto.KlasaDTO;
import com.example.aplikacja.student.dto.WeightOfGradeDTO;
import com.example.aplikacja.student.entity.Klasa;
import com.example.aplikacja.student.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface KlasaRepository extends JpaRepository<Klasa, Long> {

    Optional<Klasa> findById(Long id);

    Optional<WeightOfGradeDTO> findWById(Long id);

    Optional<KlasaDTO> findKDById(Long id);



    @Query("SELECT k FROM Klasa k WHERE k.symbol = ?1")
    Optional<Klasa> findBySymbol(String symbol);

    @Query("SELECT k FROM Klasa k WHERE k.nameOfClass = ?1")
    Optional<Klasa> findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Klasa k " +
            "SET k.enabled = TRUE WHERE k.symbol = ?1")
    int enableClass(String symbol);

    @Transactional
    @Modifying
    @Query("UPDATE Klasa k " +
            "SET k.enabled = FALSE WHERE k.symbol = ?1")
    int disableClass(String symbol);


//    @Query("SELECT k FROM Klasa k WHERE k.student.email = ?1 and " +
//            "k.nameOfClass = k.student.classForStudent")
//    List<Student> allStudentsForChooseClass(Long id);



}


