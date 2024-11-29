package com.chromamon.analysis.ieee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ieee_diagnostic_results", schema = "analysis")
public class DiagnosticData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transformer_id", nullable = false)
    private String transformerId;

    @Column(name = "process_date", nullable = false)
    private Date processDate;

    @Column(name = "diagnostic", nullable = false)
    private String diagnostic;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "gravity_level", nullable = false)
    private String gravityLevel;

    @Column(name = "analysis_date", nullable = false)
    private Date analysisDate;

    @Column(name = "document_id", nullable = false)
    private String documentId;

    public DiagnosticData(String transformerId, String method, String diagnostic, String gravityLevel, Date analysisDate, String documentId) {
        this.transformerId = transformerId;
        this.method = method;
        this.diagnostic = diagnostic;
        this.gravityLevel = gravityLevel;
        this.analysisDate = analysisDate;
        this.documentId = documentId;
        this.processDate = new Date();
    }
}