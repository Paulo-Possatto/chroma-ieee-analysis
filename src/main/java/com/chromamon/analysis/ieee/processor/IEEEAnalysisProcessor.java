package com.chromamon.analysis.ieee.processor;

import com.chromamon.analysis.ieee.model.AnalysisData;
import com.chromamon.analysis.ieee.model.DiagnosticData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IEEEAnalysisProcessor implements ItemProcessor<AnalysisData, DiagnosticData>{

    private static final double H2_LIMIT = 150;
    private static final double CH4_LIMIT = 120;
    private static final double C2H6_LIMIT = 65;
    private static final double C2H4_LIMIT = 50;
    private static final double C2H2_LIMIT = 35;

    @Override
    public DiagnosticData process(AnalysisData data) {
        log.info("Data processing started! - Document ID: {}", data.getDocumentId());
        String result = applyIEEEMethod(data);
        log.info("Data process finished! - Document ID: {}", data.getDocumentId());
        return new DiagnosticData(
                data.getTransformerIdentification(),
                "IEEE",
                result.split("\\|")[0],
                result.split("\\|")[1],
                data.getAnalysisTimestamp(),
                data.getDocumentId()
        );
    }

    public static String applyIEEEMethod(AnalysisData data) {

        double ethylene = data.getC2h4() > C2H4_LIMIT ? C2H4_LIMIT : data.getC2h4();
        double hydrogen = data.getH2() > H2_LIMIT ? H2_LIMIT : data.getH2();
        double methane = data.getCh4() > CH4_LIMIT ? CH4_LIMIT : data.getCh4();
        double ethane = data.getC2h6() > C2H6_LIMIT ? C2H6_LIMIT : data.getC2h6();
        double acetylene = data.getC2h2() > C2H2_LIMIT ? C2H2_LIMIT : data.getC2h2();

        double r1 = methane / hydrogen;
        double r2 = ethane / methane;
        double r3 = ethylene / ethane;
        double r4 = acetylene / ethylene;

        String fault = "Normal";
        if (r1 < 0.1) {
            fault = "Partial Discharges";
        } else if (r1 >= 0.1 && r1 <= 1) {
            if (r4 > 3) {
                fault = "Low Energy Discharges";
            } else if (r4 >= 0.1 && r4 <= 3) {
                fault = "High Energy Discharges";
            }
        } else if (r1 > 1) {
            if (r2 > 1 && r3 < 1) {
                fault = "Light Overheating";
            } else if (r2 > 1 && r3 > 1) {
                fault = "Severe Overheating";
            }
        } else if (r4 > 1) {
            fault = "Electrical Arch";
        }

        String severity = "Level 1 - Normal";
        if (hydrogen > 1000 || methane > 1000 || ethane > 200 || ethylene > 300 || acetylene > 80) {
            severity = "Level 4 - Urgent";
        } else if (hydrogen > 500 || methane > 400 || ethane > 100 || ethylene > 150 || acetylene > 50) {
            severity = "Level 3 - Precaution";
        } else if (hydrogen > 100 || methane > 120 || ethane > 65 || ethylene > 50 || acetylene > 35) {
            severity = "Level 2 - Alert";
        }

        return String.format("Diagnostic: %s | Gravity: %s", fault, severity);
    }
}
