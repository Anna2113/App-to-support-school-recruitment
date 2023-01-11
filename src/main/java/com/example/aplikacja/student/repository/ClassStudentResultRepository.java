package com.example.aplikacja.student.repository;

import com.example.aplikacja.student.entity.ClassStudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ClassStudentResultRepository extends JpaRepository<ClassStudentResult,Long> {
}
