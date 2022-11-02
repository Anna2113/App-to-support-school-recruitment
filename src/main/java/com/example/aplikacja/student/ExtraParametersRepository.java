package com.example.aplikacja.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraParametersRepository extends JpaRepository<ExtraParameters, Long> {
}
