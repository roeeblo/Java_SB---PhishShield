<p align="center">
  <img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&size=26&duration=3000&pause=1000&color=00BFFF&center=true&vCenter=true&width=500&lines=PhishShield;AI-Powered+Phishing+Detection" alt="PhishShield" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Gemini AI-2.5 Flash-4285F4?style=for-the-badge&logo=google&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
</p>

<p align="center">
  <a href="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/ci.yml">
    <img src="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/ci.yml/badge.svg" alt="CI/CD"/>
  </a>
  <a href="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/codeql.yml">
    <img src="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/codeql.yml/badge.svg" alt="CodeQL"/>
  </a>
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License"/>
</p>

<p align="center">
  <a href="https://phishshield.runmydocker-app.com">
    <img src="https://img.shields.io/badge/Live Demo-phishshield.runmydocker--app.com-00BFFF?style=for-the-badge"/>
  </a>
</p>

---

## About

PhishShield is a phishing detection service that helps users identify scam attempts in SMS messages, emails, and URLs. Built with Spring Boot and powered by Google's Gemini AI, it provides real-time analysis with Hebrew language support.

**Try it now:** https://phishshield.runmydocker-app.com

---

## Tech Stack

### Core
| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Runtime |
| Spring Boot | 3.2 | Backend Framework |
| Maven | 3.9 | Build Tool |
| Google Gemini | 2.5 Flash | AI Analysis |

### DevOps
| Technology | Purpose |
|------------|---------|
| Docker | Containerization |
| GitHub Actions | CI/CD Pipeline |
| Dependabot | Automated Dependency Updates |

### Security & Quality
| Technology | Purpose |
|------------|---------|
| CodeQL | Static Code Analysis |
| OWASP Dependency Check | Vulnerability Scanning |
| Rate Limiting | Abuse Prevention |

### API Documentation
| Technology | Purpose |
|------------|---------|
| OpenAPI 3.0 | API Specification |
| Swagger UI | Interactive Documentation |

---

## Architecture

```
Client Request
      │
      ▼
┌─────────────────────────────────┐
│         Rate Limiter            │  (10 req/min per IP)
└──────────────┬──────────────────┘
               │
               ▼
┌─────────────────────────────────┐
│       Analyze Controller        │
└──────────────┬──────────────────┘
               │
               ▼
┌─────────────────────────────────┐
│       Analysis Service          │
└──────────────┬──────────────────┘
               │
               ▼
┌─────────────────────────────────┐
│        PII Sanitizer            │  (removes personal data)
└──────────────┬──────────────────┘
               │
               ▼
┌─────────────────────────────────┐
│        Gemini Client            │ ──────▶ Google Gemini API
└─────────────────────────────────┘
```

---

## Running with Docker

```bash
export GEMINI_API_KEY=your-api-key
docker-compose up --build
```

### Endpoints

| Endpoint | Description |
|----------|-------------|
| http://localhost:8080 | Web Interface |
| http://localhost:8080/swagger-ui.html | API Documentation |
| http://localhost:8080/api/health | Health Check |

---

## API Usage

### Request

```http
POST /api/analyze
Content-Type: application/json

{
  "content": "Your message to analyze",
  "type": "SMS"
}
```

### Response

```json
{
  "isPhishing": true,
  "suspicion": 0.85,
  "reasons": ["Reason 1", "Reason 2"],
  "recommendation": "Recommendation text"
}
```

Supported types: `SMS`, `EMAIL`, `URL`

---

## Security

- API keys stored as environment variables
- CodeQL analysis on every push
- OWASP dependency vulnerability scanning
- Rate limiting (10 requests/minute per IP)
- PII sanitization before AI processing
- Automated security updates via Dependabot

---

## Project Structure

```
PhishShield/
├── .github/
│   ├── workflows/
│   │   ├── ci.yml
│   │   └── codeql.yml
│   └── dependabot.yml
├── server/
│   └── src/main/java/com/roeeblo/phishshield/
│       ├── controller/
│       ├── service/
│       ├── dto/
│       ├── config/
│       └── util/
├── Dockerfile
└── docker-compose.yml
```

---

## License

MIT License
