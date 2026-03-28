<h1 align="center">📝 Todoc</h1>

<p align="center">
  <b>디지털 방명록 서비스</b><br/>
  QR로 작성하고, 실시간으로 공유되는 새로운 경험
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Backend-SpringBoot-green"/>
  <img src="https://img.shields.io/badge/Frontend-React-blue"/>
  <img src="https://img.shields.io/badge/DB-PostgreSQL-blue"/>
  <img src="https://img.shields.io/badge/Deploy-AWS-orange"/>
  <img src="https://img.shields.io/badge/AI-OpenAI-black"/>
</p>

---

## 🚀 Preview

<p align="center">
  <img width="446" height="887" alt="image" src="https://github.com/user-attachments/assets/95ef54f1-c813-4362-85b5-78412572be0a" />
</p>

---

## 📌 About

> 매장에서 QR을 통해 방명록을 작성하고  
> 실시간으로 공유되는 **B2B 디지털 방명록 서비스**

- 🎨 폰트 + 테마로 감성 표현
- 📺 매장 디스플레이 실시간 반영
- 🤖 AI 기반 폰트 추천

---

## ✨ Features

### 👤 User
- QR 스캔 → 웹앱 접속
- 방명록 작성 (폰트 선택)
- AI 폰트 추천
- 실시간 반영

### 🏪 Owner
- 방명록 관리 (삭제)
- 디스플레이 제어
- 테마 관리

---

## 🤖 AI Font Recommendation

- 감정 분석 기반 폰트 추천
- Top 3 감정 추출
- 감정 → 폰트 매핑

```json
{
  "emotions": ["감성", "따뜻함", "편안함"],
  "fonts": ["Sandoll Gothic", "Sandoll Smile", "Sandoll 거복", "SD 잔"]
}
```
## ⚙️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security + JWT
- JPA

### Infra
- AWS EC2
- AWS RDS (PostgreSQL)
- AWS S3
- Docker
- Nginx
- GitHub Actions (CI/CD)

### Frontend
- React + TypeScript
- Vercel

---

## 🔥 Key Points

### ✔ AI 응답 안정화
- 문제: AI 응답 형식이 일정하지 않았음
- 해결: JSON 형식 강제 + 감정 카테고리 제한
- 결과: 안정적인 파싱 가능

### ✔ CI/CD 자동화
- 기존: 수동 배포 시 5분 이상 소요
- 개선: GitHub Actions + Docker 기반 자동 배포 구축
- 결과: 1~2분 내 배포 가능

### ✔ 위치 기반 매장 조회
- Haversine 공식을 활용해 사용자 위치 기준 반경 내 매장 조회 기능 구현

---

## 📊 ERD

<img width="1947" height="1061" alt="image" src="https://github.com/user-attachments/assets/8ac435cb-b04f-4bc9-9de5-9c3e91f45ad3" />

---

## 👥 Team

| Role | Members |
|------|---------|
| Backend | 2 |
| Frontend | 1 |
| Designer | 2 |

---

## 🙋‍♀️ My Role

- 백엔드 API 설계 및 개발
- AI 폰트 추천 기능 구현
- AWS 인프라 구축
- CI/CD 자동화 구축
