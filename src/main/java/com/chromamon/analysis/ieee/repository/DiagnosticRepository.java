package com.chromamon.analysis.ieee.repository;

import com.chromamon.analysis.ieee.model.DiagnosticData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosticRepository extends JpaRepository<DiagnosticData, Long> {
}