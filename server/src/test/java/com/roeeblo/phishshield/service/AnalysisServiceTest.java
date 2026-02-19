package com.roeeblo.phishshield.service;

import com.roeeblo.phishshield.dto.AnalyzeRequest;
import com.roeeblo.phishshield.dto.AnalyzeRequest.ContentType;
import com.roeeblo.phishshield.dto.AnalyzeResponse;
import com.roeeblo.phishshield.util.PiiSanitizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalysisServiceTest {

    @Mock
    private GeminiClient geminiClient;

    private PiiSanitizer piiSanitizer;
    private AnalysisService analysisService;

    @BeforeEach
    void setUp() {
        piiSanitizer = new PiiSanitizer();
        analysisService = new AnalysisService(geminiClient, piiSanitizer);
    }

    @Test
    void analyze_shouldHandleEmptyContent() {
    AnalyzeRequest request = new AnalyzeRequest("", ContentType.EMAIL);

    when(geminiClient.analyzeContent(any(), any()))
        .thenReturn(AnalyzeResponse.safe());

    AnalyzeResponse response = analysisService.analyze(request);

    assertNotNull(response);
    }

    @Test
    void analyze_shouldReturnPhishingResponse_whenContentIsPhishing() {
        AnalyzeRequest request = new AnalyzeRequest(
            "Click here to verify your account: http://suspicious-link.com",
            ContentType.EMAIL
        );
        
        AnalyzeResponse expectedResponse = new AnalyzeResponse(
            true,
            0.95,
            List.of("Suspicious URL", "Urgency language"),
            "Do not click the link"
        );
        
        when(geminiClient.analyzeContent(any(), eq(ContentType.EMAIL)))
            .thenReturn(expectedResponse);
        AnalyzeResponse response = analysisService.analyze(request);

        assertTrue(response.isPhishing());
        assertEquals(0.95, response.suspicion());
        assertFalse(response.reasons().isEmpty());
    }

    @Test
    void analyze_shouldReturnSafeResponse_whenContentIsSafe() {
        AnalyzeRequest request = new AnalyzeRequest(
            "Hello, this is a regular message.",
            ContentType.EMAIL
        );
        
        when(geminiClient.analyzeContent(any(), eq(ContentType.EMAIL)))
            .thenReturn(AnalyzeResponse.safe());
        AnalyzeResponse response = analysisService.analyze(request);

        assertFalse(response.isPhishing());
        assertEquals(0.0, response.suspicion());
    }

    @Test
    void analyze_shouldSanitizePii_beforeAnalysis() {
        AnalyzeRequest request = new AnalyzeRequest(
            "Contact me at john@example.com or 123-456-7890",
            ContentType.EMAIL
        );
        
        when(geminiClient.analyzeContent(any(), any()))
            .thenReturn(AnalyzeResponse.safe());

        analysisService.analyze(request);
        verify(geminiClient).analyzeContent(
        argThat(content -> !content.contains("john@example.com")),
        any()
    );
    class PhishShieldUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:5173");
    }

    @Test
    void analyzeFlow_shouldShowResult() {
        driver.findElement(By.id("message"))
              .sendKeys("Click here to verify account");

        driver.findElement(By.id("analyzeBtn")).click();

        WebElement result = driver.findElement(By.id("result"));

        assertTrue(result.getText().contains("Phishing"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}


    }
}

