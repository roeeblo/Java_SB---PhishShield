<p align="center">
  <img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&size=28&duration=3000&pause=1000&color=00BFFF&center=true&vCenter=true&width=600&lines=🛡️+PhishShield;AI-Powered+Phishing+Detection;Protecting+Users+From+Scams;Built+with+Spring+Boot+%2B+Gemini+AI" alt="Typing SVG" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Google Gemini-AI-4285F4?style=for-the-badge&logo=google&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-Container-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"/>
  <img src="https://img.shields.io/badge/GitHub Actions-CI/CD-2088FF?style=for-the-badge&logo=githubactions&logoColor=white"/>
  <img src="https://img.shields.io/badge/CodeQL-Security-000000?style=for-the-badge&logo=github&logoColor=white"/>
</p>

<p align="center">
  <a href="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/ci.yml">
    <img src="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/ci.yml/badge.svg" alt="CI/CD Pipeline"/>
  </a>
  <a href="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/codeql.yml">
    <img src="https://github.com/roeeblo/Java_SB---PhishShield/actions/workflows/codeql.yml/badge.svg" alt="CodeQL"/>
  </a>
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License"/>
</p>

---

## 📖 About

**PhishShield** is an AI-powered phishing detection service designed to help users (especially elderly) identify phishing attempts in Hebrew. The service analyzes SMS messages, emails, and URLs using Google's Gemini AI to detect scams and fraud attempts.

<p align="center">
  <img src="https://img.shields.io/badge/🎯_Target_Audience-Elderly_Users-FF6B6B?style=flat-square"/>
  <img src="https://img.shields.io/badge/🌍_Language-Hebrew-4ECDC4?style=flat-square"/>
  <img src="https://img.shields.io/badge/🤖_AI_Model-Gemini_2.5_Flash-9B59B6?style=flat-square"/>
</p>

---

## 🚀 Tech Stack

### Backend
| Technology | Purpose |
|------------|---------|
| <img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white"/> | Core Language |
| <img src="https://img.shields.io/badge/Spring Boot-3.2-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> | Backend Framework |
| <img src="https://img.shields.io/badge/Maven-3.9-C71A36?style=flat-square&logo=apachemaven&logoColor=white"/> | Build Tool |

### AI & Analysis
| Technology | Purpose |
|------------|---------|
| <img src="https://img.shields.io/badge/Google Gemini-2.5 Flash-4285F4?style=flat-square&logo=google&logoColor=white"/> | AI Analysis Engine |
| <img src="https://img.shields.io/badge/PII Sanitizer-Custom-FF6B6B?style=flat-square"/> | Privacy Protection |

### DevOps & CI/CD
| Technology | Purpose |
|------------|---------|
| <img src="https://img.shields.io/badge/Docker-Container-2496ED?style=flat-square&logo=docker&logoColor=white"/> | Containerization |
| <img src="https://img.shields.io/badge/GitHub Actions-CI/CD-2088FF?style=flat-square&logo=githubactions&logoColor=white"/> | Automation Pipeline |
| <img src="https://img.shields.io/badge/Dependabot-Auto Updates-025E8C?style=flat-square&logo=dependabot&logoColor=white"/> | Dependency Management |

### Security & Quality
| Technology | Purpose |
|------------|---------|
| <img src="https://img.shields.io/badge/CodeQL-SAST-000000?style=flat-square&logo=github&logoColor=white"/> | Static Analysis |
| <img src="https://img.shields.io/badge/OWASP-Dependency Check-000000?style=flat-square&logo=owasp&logoColor=white"/> | Vulnerability Scanning |
| <img src="https://img.shields.io/badge/Rate Limiting-10 req/min-FF4444?style=flat-square"/> | Abuse Prevention |

### Documentation
| Technology | Purpose |
|------------|---------|
| <img src="https://img.shields.io/badge/OpenAPI-3.0-85EA2D?style=flat-square&logo=openapiinitiative&logoColor=black"/> | API Specification |
| <img src="https://img.shields.io/badge/Swagger UI-Interactive Docs-85EA2D?style=flat-square&logo=swagger&logoColor=black"/> | API Documentation |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    PhishShield Architecture                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐    ┌─────────────────────────────────────┐ │
│  │   Client    │───▶│         Spring Boot API             │ │
│  │  (Browser)  │    │                                     │ │
│  └─────────────┘    │  ┌─────────────┐  ┌──────────────┐  │ │
│                     │  │ Rate Limit  │  │   Swagger    │  │ │
│                     │  │   Filter    │  │     UI       │  │ │
│                     │  └──────┬──────┘  └──────────────┘  │ │
│                     │         │                           │ │
│                     │  ┌──────▼──────┐                    │ │
│                     │  │  Controller │                    │ │
│                     │  └──────┬──────┘                    │ │
│                     │         │                           │ │
│                     │  ┌──────▼──────┐                    │ │
│                     │  │   Service   │                    │ │
│                     │  └──────┬──────┘                    │ │
│                     │         │                           │ │
│                     │  ┌──────▼──────┐  ┌──────────────┐  │ │
│                     │  │ PII Sanitize│─▶│ Gemini Client│──┼─┼──▶ Google Gemini AI
│                     │  └─────────────┘  └──────────────┘  │ │
│                     └─────────────────────────────────────┘ │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## ⚡ Features

- 🔍 **Real-time Analysis** - Instant phishing detection for SMS, Email, and URLs
- 🤖 **AI-Powered** - Leverages Google Gemini 2.5 Flash for intelligent analysis
- 🇮🇱 **Hebrew Support** - Native Hebrew language analysis and responses
- 🔒 **Privacy First** - PII sanitization before AI processing
- 🛡️ **Rate Limiting** - Protection against abuse (10 req/min per IP)
- 📖 **API Documentation** - Interactive Swagger UI
- 🐳 **Containerized** - Docker support for easy deployment
- 🔄 **CI/CD Pipeline** - Automated testing, security scanning, and deployment
- 🔐 **Security Scanning** - CodeQL and OWASP dependency checks

---

## 🚀 Quick Start

### Prerequisites

```bash
Java 21+
Maven 3.9+
Docker & Docker Compose
Google Gemini API Key
```

### Run with Docker (Recommended)

```bash
# Set your API key
export GEMINI_API_KEY=your-api-key-here

# Start the application
docker-compose up --build
```

### Run Locally

```bash
cd server
export GEMINI_API_KEY=your-api-key-here
mvn spring-boot:run
```

### Access Points

| Endpoint | URL |
|----------|-----|
| 🌐 Application | http://localhost:8080 |
| 📖 Swagger UI | http://localhost:8080/swagger-ui.html |
| 📄 OpenAPI JSON | http://localhost:8080/api-docs |
| ❤️ Health Check | http://localhost:8080/api/health |

---

## 📡 API Usage

### Analyze Message

```bash
POST /api/analyze
Content-Type: application/json

{
  "content": "היי אמא, זאת הבת שלך. שלחי לי את פרטי האשראי בבקשה",
  "type": "SMS"
}
```

### Response

```json
{
  "isPhishing": true,
  "suspicion": 0.9,
  "reasons": ["בקשה לפרטי אשראי", "התחזות לבן משפחה"],
  "recommendation": "אל תשלח פרטים רגישים בהודעה!"
}
```

---

## 🔐 Security Features

| Feature | Description |
|---------|-------------|
| 🔑 **API Key Protection** | Keys stored as environment variables |
| 📊 **CodeQL Analysis** | Automated SAST on every push |
| 📦 **OWASP Check** | Dependency vulnerability scanning |
| 🚦 **Rate Limiting** | 10 requests/minute per IP |
| 🔄 **Dependabot** | Automated security updates |
| 🧹 **PII Sanitization** | Personal data removed before AI processing |

---

## 📁 Project Structure

```
PhishShield/
├── .github/
│   ├── workflows/
│   │   ├── ci.yml              # CI/CD Pipeline
│   │   └── codeql.yml          # Security Analysis
│   └── dependabot.yml          # Auto-updates
├── server/
│   └── src/main/java/
│       └── com/roeeblo/phishshield/
│           ├── controller/     # REST Controllers
│           ├── service/        # Business Logic
│           ├── dto/            # Data Transfer Objects
│           ├── config/         # Configuration
│           └── util/           # Utilities
├── Dockerfile                  # Multi-stage build
└── docker-compose.yml          # Container orchestration
```

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## 📄 License

This project is licensed under the MIT License.

---

<p align="center">
  <img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&size=14&duration=3000&pause=1000&color=6DB33F&center=true&vCenter=true&width=400&lines=Built+with+❤️+using+Spring+Boot;Protected+by+AI+🤖;Keeping+users+safe+🛡️" alt="Footer" />
</p>
