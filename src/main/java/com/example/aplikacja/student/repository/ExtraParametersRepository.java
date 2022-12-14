package com.example.aplikacja.student.repository;

import com.example.aplikacja.student.entity.ExtraParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExtraParametersRepository extends JpaRepository<ExtraParameters, Long> {
}
