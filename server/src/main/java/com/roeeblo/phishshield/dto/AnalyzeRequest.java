package com.roeeblo.phishshield.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to analyze content for phishing")
public record AnalyzeRequest(
    @Schema(
        description = "The message content to analyze",
        example = "היי אמא, זאת הבת שלך. שלחי לי את פרטי האשראי בבקשה",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Content cannot be blank")
    String content,
    
    @Schema(
        description = "Type of content being analyzed",
        example = "SMS",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Type cannot be null")
    ContentType type
) {
    @Schema(description = "Content type enumeration")
    public enum ContentType {
        @Schema(description = "Email message")
        EMAIL,
        @Schema(description = "URL/Link")
        URL,
        @Schema(description = "SMS/WhatsApp message")
        SMS
    }
}
