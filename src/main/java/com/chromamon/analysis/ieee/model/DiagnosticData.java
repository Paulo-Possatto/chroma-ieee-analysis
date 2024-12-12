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
public class DiagnosticData {

    private String transformerId;
    private Date processDate;
    private String result;
    private String method;
    private String gravityLevel;
    private Date analysisDate;

    public DiagnosticData(String transformerId, String method, String result, Date analysisDate) {
        this.transformerId = transformerId;
        this.method = method;
        this.result = result;
        this.analysisDate = analysisDate;
        this.processDate = new Date();
    }
}