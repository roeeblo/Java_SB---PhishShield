package com.roeeblo.phishshield.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnalyzeRequest(
    @NotBlank(message = "Content cannot be blank")
    String content,
    
    @NotNull(message = "Type cannot be null")
    ContentType type
) {
    public enum ContentType {
        EMAIL,
        URL,
        SMS
    }
}

