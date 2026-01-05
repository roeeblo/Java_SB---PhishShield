# PhishShield

[![CI/CD Pipeline](https://github.com/YOUR_USERNAME/Java_SB---PhishShield/actions/workflows/ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/Java_SB---PhishShield/actions/workflows/ci.yml)
[![CodeQL](https://github.com/YOUR_USERNAME/Java_SB---PhishShield/actions/workflows/codeql.yml/badge.svg)](https://github.com/YOUR_USERNAME/Java_SB---PhishShield/actions/workflows/codeql.yml)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green.svg)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

AI-powered phishing detection service using Google Gemini - designed to help elderly users identify phishing attempts in Hebrew.

## Features

- Real-time phishing detection for SMS, Email, and URLs
- Powered by Google Gemini AI (Gemini 2.5 Flash)
- Hebrew language support
- PII sanitization before analysis
- Rate limiting to prevent abuse
- Docker support for easy deployment
- CI/CD with GitHub Actions

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- Docker & Docker Compose
- Google Gemini API Key

### Configuration

Set your Gemini API key as an environment variable:

```bash
# Linux/Mac
export GEMINI_API_KEY=your-api-key-here

# Windows PowerShell
$env:GEMINI_API_KEY="your-api-key-here"
```

### Running with Docker (Recommended)

```bash
docker-compose up --build
```

Then open http://localhost:8080

### Running Locally

```bash
cd server
mvn spring-boot:run
```

## API Endpoints

### POST /api/analyze

Analyze content for phishing indicators.

**Request:**
```json
{
  "content": "Suspicious message content here",
  "type": "SMS"
}
```

**Response:**
```json
{
  "isPhishing": true,
  "suspicion": 0.9,
  "reasons": ["Request for credit card details", "Impersonation of family member"],
  "recommendation": "Do not send sensitive information via message!"
}
```

### GET /api/health

Health check endpoint.

## Architecture

```
PhishShield/
├── .github/workflows/     # CI/CD pipelines
│   ├── ci.yml            # Main pipeline
│   └── codeql.yml        # Security scanning
├── server/               # Spring Boot application
│   ├── src/main/java/    # Java source code
│   └── src/main/resources/
│       └── static/       # Frontend (HTML/CSS/JS)
├── Dockerfile            # Multi-stage Docker build
└── docker-compose.yml    # Container orchestration
```

## Security

- API keys are never committed to the repository
- CodeQL analysis runs on every push
- OWASP dependency checks in CI
- Rate limiting: 10 requests/minute per IP

## License

MIT License

## Acknowledgments

- Google Gemini AI for the analysis engine
- Spring Boot for the backend framework
