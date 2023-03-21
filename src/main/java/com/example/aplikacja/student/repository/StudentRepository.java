package com.example.aplikacja.student.repository;

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
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Student a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableStudent(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Student a " +
            "SET a.enabled = FALSE WHERE a.email = ?1")
    int disableStudent(String email);

    @Query("SELECT a FROM Student a WHERE a.lastName = ?1")
    List<Student> getStudentBySurname(String surname);

    //pierwszy parametr z metody ?1
    @Query("SELECT s FROM Student s WHERE s.classForStudent = ?1")
    List<Student> studenciWKlasie(String nazwaKlasy);

    @Query("SELECT s FROM Student s WHERE s.status <> 'sklasyfikowany'")
    List<Student> reserveList();



}
