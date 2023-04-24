package com.example.aplikacja.appuser;

import com.example.aplikacja.appuser.AppUser;
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
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = FALSE WHERE a.email = ?1")
    int disableAppUser(String email);

//    @Query("SELECT s FROM AppUser a JOIN Student s on a.id = s.app_user_id WHERE a.id = ?1)
//    List<Student> getStudent();

//    @Query("SELECT s FROM Student s JOIN AppUser a ON s.appUser.id = a.id WHERE s.appUser.id = a.id")
//    List<Student> getStudent();

//    @Query("SELECT s FROM Student s JOIN AppUser a ON s.appUser.id = a.id WHERE a.id = ?1")
//    List<Student> getStudent(Long id);


}


