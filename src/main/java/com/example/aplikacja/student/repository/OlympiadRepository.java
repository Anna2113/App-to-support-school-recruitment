package com.example.aplikacja.student.repository;

import com.example.aplikacja.student.entity.Olympiad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OlympiadRepository extends JpaRepository<Olympiad, Long> {
}
