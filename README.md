# PhishShield

AI-powered phishing detection service using Google Gemini.

## Features

- 🛡️ Real-time phishing URL and email analysis
- 🤖 Powered by Google Gemini AI
- 🔒 PII sanitization before analysis
- 🐳 Docker support for easy deployment

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- Docker & Docker Compose (optional)
- Google Gemini API Key

### Configuration

Set your Gemini API key as an environment variable:

```bash
export GEMINI_API_KEY=your-api-key-here
```

### Running Locally

```bash
cd server
mvn spring-boot:run
```

### Running with Docker

```bash
docker-compose up --build
```

## API Endpoints

### POST /api/analyze

Analyze content for phishing indicators.

**Request:**
```json
{
  "content": "Suspicious email or URL content here",
  "type": "EMAIL"
}
```

**Response:**
```json
{
  "isPhishing": true,
  "confidence": 0.95,
  "reasons": ["Suspicious URL pattern", "Urgency language detected"],
  "recommendation": "Do not click any links in this message"
}
```

## License

MIT License

