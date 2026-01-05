package com.roeeblo.phishshield.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roeeblo.phishshield.dto.AnalyzeRequest.ContentType;
import com.roeeblo.phishshield.dto.AnalyzeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public class GeminiClient {

    private static final Logger log = LoggerFactory.getLogger(GeminiClient.class);
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";
    
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GeminiClient(
            @Value("${gemini.api-key}") String apiKey,
            ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
        log.info("GeminiClient initialized with API key: {}...", apiKey.substring(0, Math.min(10, apiKey.length())));
    }

    public AnalyzeResponse analyzeContent(String content, ContentType type) {
        try {
            String prompt = buildPrompt(content, type);
            String response = callGeminiApi(prompt);
            log.info("Gemini API response received, length: {}", response.length());
            return parseResponse(response);
        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage(), e);
            return AnalyzeResponse.safe();
        }
    }

    private String buildPrompt(String content, ContentType type) {
        String typeHebrew = switch (type) {
            case EMAIL -> "מייל";
            case SMS -> "הודעת SMS";
            case URL -> "קישור";
        };
        
        return """
            אתה מומחה לאבטחת מידע. נתח את ההודעה הבאה וקבע אם זו הודעת פישינג (הונאה).
            
            חשוב מאוד: היה מאוזן ושקול!
            
            סוג ההודעה: %s
            
            תוכן ההודעה:
            ---
            %s
            ---
            
            הבחנה קריטית - קישור הוא המפתח:
            - הודעה שאומרת "עדכן פרטים" בלי קישור = לגיטימית (המשתמש ילך לאתר הרשמי בעצמו)
            - הודעה שאומרת "לחץ כאן לעדכן" + קישור חשוד = פישינג
            
            סימנים לפישינג (צריך לפחות 2 מהם):
            1. קישור חשוד בהודעה (דומיין מוזר, bit.ly, לא האתר הרשמי)
            2. בקשה להזין פרטים רגישים ישירות בהודעה או בקישור
            3. איום ודחיפות קיצונית ("יינעל תוך שעה!")
            4. הבטחת פרס/כסף שלא ביקשת
            
            סימנים להודעה לגיטימית:
            1. בקשה לעדכן פרטים בלי קישור (תלך לאתר בעצמך)
            2. תזכורת רגילה מחברה מוכרת
            3. הודעה אישית/משפחתית
            4. עדכון משלוח/הזמנה
            
            החזר JSON בלבד (בלי markdown):
            {
              "isPhishing": true/false,
              "suspicion": רמת החשד מ-0 עד 1 (0 = בטוח לגמרי, 1 = חשוד מאוד),
              "reasons": ["סיבה 1", "סיבה 2"],
              "recommendation": "המלצה קצרה"
            }
            
            דוגמאות:
            - "היי מה נשמע?" -> suspicion: 0 (בטוח - הודעה אישית)
            - "ההזמנה שלך נשלחה, מספר מעקב 123" -> suspicion: 0.1 (בטוח - עדכון רגיל)
            - "מדבר דרור ממפעל הפיס, עליך לתקן פרטי אשראי" -> suspicion: 0.4 (קצת חשוד אבל אין קישור)
            - "מפעל הפיס: לחץ כאן לתקן פרטים: bit.ly/xyz" -> suspicion: 0.9 (חשוד - קישור מקוצר!)
            - "חשבונך יינעל! לחץ כאן: bank-update.xyz" -> suspicion: 1 (מאוד חשוד - דחיפות + קישור מזויף)
            """.formatted(typeHebrew, content);
    }

    private String callGeminiApi(String prompt) throws Exception {
        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", prompt)
                ))
            )
        );

        String url = GEMINI_API_URL + "?key=" + apiKey;
        log.debug("Calling Gemini API: {}", GEMINI_API_URL);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        log.info("Gemini status: {}", response.statusCode());
        log.debug("Gemini raw body (first 800): {}", 
                response.body().substring(0, Math.min(800, response.body().length())));
        
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Gemini HTTP " + response.statusCode() + ": " + response.body());
        }
        
        return response.body();
    }

    private AnalyzeResponse parseResponse(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);
        
        // Check for API errors
        if (root.has("error")) {
            String errorMsg = root.path("error").path("message").asText();
            log.error("Gemini API error: {}", errorMsg);
            throw new RuntimeException("Gemini API error: " + errorMsg);
        }
        
        JsonNode textNode = root.path("candidates").path(0).path("content").path("parts").path(0).path("text");
        
        if (textNode.isMissingNode()) {
            log.warn("No text found in Gemini response: {}", response.substring(0, Math.min(500, response.length())));
            return AnalyzeResponse.safe();
        }

        String text = textNode.asText();
        log.debug("Gemini response text: {}", text);
        
        // Extract JSON from response (may be wrapped in markdown)
        int jsonStart = text.indexOf("{");
        int jsonEnd = text.lastIndexOf("}") + 1;
        
        if (jsonStart >= 0 && jsonEnd > jsonStart) {
            String json = text.substring(jsonStart, jsonEnd);
            log.debug("Extracted JSON: {}", json);
            JsonNode analysisResult = objectMapper.readTree(json);
            
            AnalyzeResponse result = new AnalyzeResponse(
                analysisResult.path("isPhishing").asBoolean(false),
                analysisResult.path("suspicion").asDouble(0.0),
                parseReasons(analysisResult.path("reasons")),
                analysisResult.path("recommendation").asText("לא ניתן לקבוע המלצה")
            );
            log.info("Analysis result: isPhishing={}, suspicion={}", result.isPhishing(), result.suspicion());
            return result;
        }
        
        log.warn("Could not find JSON in response: {}", text);
        return AnalyzeResponse.safe();
    }

    private List<String> parseReasons(JsonNode reasonsNode) {
        if (reasonsNode.isArray()) {
            return objectMapper.convertValue(reasonsNode, 
                objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        }
        return List.of();
    }
}

