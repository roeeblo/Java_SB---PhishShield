package com.roeeblo.phishshield.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PiiSanitizer {

    // Pattern for email addresses
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    
    // Pattern for phone numbers (various formats)
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("(\\+?\\d{1,3}[-.\\s]?)?(\\(?\\d{2,3}\\)?[-.\\s]?)?\\d{3,4}[-.\\s]?\\d{3,4}");
    
    // Pattern for credit card numbers
    private static final Pattern CREDIT_CARD_PATTERN = 
        Pattern.compile("\\b(?:\\d{4}[-.\\s]?){3}\\d{4}\\b");
    
    // Pattern for SSN (US Social Security Number)
    private static final Pattern SSN_PATTERN = 
        Pattern.compile("\\b\\d{3}[-.\\s]?\\d{2}[-.\\s]?\\d{4}\\b");
    
    // Pattern for Israeli ID numbers
    private static final Pattern IL_ID_PATTERN = 
        Pattern.compile("\\b\\d{9}\\b");

    public String sanitize(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        String sanitized = content;
        
        // Replace PII with placeholders
        sanitized = EMAIL_PATTERN.matcher(sanitized).replaceAll("[EMAIL_REDACTED]");
        sanitized = CREDIT_CARD_PATTERN.matcher(sanitized).replaceAll("[CARD_REDACTED]");
        sanitized = SSN_PATTERN.matcher(sanitized).replaceAll("[SSN_REDACTED]");
        sanitized = PHONE_PATTERN.matcher(sanitized).replaceAll("[PHONE_REDACTED]");
        
        return sanitized;
    }
}

