package com.roeeblo.phishshield.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Phishing analysis result")
public record AnalyzeResponse(
    @Schema(description = "Whether the message is identified as phishing", example = "true")
    boolean isPhishing,
    
    @Schema(description = "Suspicion level from 0 (safe) to 1 (definitely phishing)", example = "0.85")
    double suspicion,
    
    @Schema(
        description = "List of reasons for the suspicion level",
        example = "[\"בקשה לפרטי אשראי\", \"התחזות לבן משפחה\"]"
    )
    List<String> reasons,
    
    @Schema(
        description = "Recommendation for the user in Hebrew",
        example = "אל תשלח פרטים רגישים בהודעה!"
    )
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
