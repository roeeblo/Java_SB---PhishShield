package com.roeeblo.phishshield.controller;

import com.roeeblo.phishshield.dto.AnalyzeRequest;
import com.roeeblo.phishshield.dto.AnalyzeResponse;
import com.roeeblo.phishshield.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Phishing Analysis", description = "API for analyzing messages for phishing attempts")
public class AnalyzeController {

    private final AnalysisService analysisService;

    public AnalyzeController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @Operation(
        summary = "Analyze message for phishing",
        description = "Analyzes SMS, Email, or URL content using AI to detect phishing attempts. " +
                      "Returns suspicion level (0-1) and detailed reasoning in Hebrew."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Analysis completed successfully",
            content = @Content(schema = @Schema(implementation = AnalyzeResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request - missing or invalid content/type"
        ),
        @ApiResponse(
            responseCode = "429",
            description = "Rate limit exceeded - max 10 requests per minute per IP"
        )
    })
    @PostMapping("/analyze")
    public ResponseEntity<AnalyzeResponse> analyze(@Valid @RequestBody AnalyzeRequest request) {
        AnalyzeResponse response = analysisService.analyze(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Health check", description = "Returns OK if the service is running")
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
