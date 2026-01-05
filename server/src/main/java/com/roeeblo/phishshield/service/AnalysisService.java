package com.roeeblo.phishshield.service;

import com.roeeblo.phishshield.dto.AnalyzeRequest;
import com.roeeblo.phishshield.dto.AnalyzeResponse;
import com.roeeblo.phishshield.util.PiiSanitizer;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    private final GeminiClient geminiClient;
    private final PiiSanitizer piiSanitizer;

    public AnalysisService(GeminiClient geminiClient, PiiSanitizer piiSanitizer) {
        this.geminiClient = geminiClient;
        this.piiSanitizer = piiSanitizer;
    }

    public AnalyzeResponse analyze(AnalyzeRequest request) {
        // Sanitize PII before sending to AI
        String sanitizedContent = piiSanitizer.sanitize(request.content());
        
        // Send to Gemini for analysis
        return geminiClient.analyzeContent(sanitizedContent, request.type());
    }
}

