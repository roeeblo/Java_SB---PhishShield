package com.roeeblo.phishshield.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitConfig implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static final int MAX_GLOBAL_REQUESTS_PER_MINUTE = 100;
    
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final AtomicInteger globalRequestCount = new AtomicInteger(0);
    private volatile long lastResetTime = System.currentTimeMillis();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (!httpRequest.getRequestURI().equals("/api/analyze")) {
            chain.doFilter(request, response);
            return;
        }

        long now = System.currentTimeMillis();
        if (now - lastResetTime > 60000) {
            requestCounts.clear();
            globalRequestCount.set(0);
            lastResetTime = now;
        }

        if (globalRequestCount.get() >= MAX_GLOBAL_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("""
                {"error": "השרת עמוס כרגע, נסו שוב בעוד דקה", "errorCode": "GLOBAL_RATE_LIMIT"}
                """);
            return;
        }

        String clientIp = getClientIp(httpRequest);
        
        AtomicInteger count = requestCounts.computeIfAbsent(clientIp, k -> new AtomicInteger(0));
        
        if (count.get() >= MAX_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("""
                {"error": "יותר מדי בקשות, נסו שוב בעוד דקה", "errorCode": "IP_RATE_LIMIT"}
                """);
            return;
        }

        count.incrementAndGet();
        globalRequestCount.incrementAndGet();
        
        chain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
