package com.roeeblo.phishshield.dto;

import java.util.List;

public record AnalyzeResponse(
    boolean isPhishing,
    double suspicion,
    List<String> reasons,
    String recommendation
) {
    public static AnalyzeResponse safe() {
        return new AnalyzeResponse(
            false,
            0.0,
            List.of(),
            "ההודעה נראית בטוחה."
        );
    }

    public static AnalyzeResponse phishing(double suspicion, List<String> reasons, String recommendation) {
        return new AnalyzeResponse(true, suspicion, reasons, recommendation);
    }
}

