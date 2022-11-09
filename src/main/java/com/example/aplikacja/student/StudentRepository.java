package com.example.aplikacja.student;

import com.example.aplikacja.appuser.AppUser;
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

}
