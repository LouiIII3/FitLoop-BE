# 👗 FITLOOP - 순환형 패션 커뮤니티 플랫폼

<br />

<p align="center">
  <img src="https://github.com/user-attachments/assets/f3189ecb-934e-4aa0-bdac-cdb00acacc49" width="900" alt="FITLOOP Banner"/>
</p>

<br />

**FITLOOP**은 패션 아이템을 사고팔고, 룩북과 챌린지를 통해 스타일을 공유하는  
**순환형 패션 커뮤니티 플랫폼**입니다.

지속 가능한 패션을 위해 **REUSE · ECO · COMMUNITY · STYLE** 가치를 기반으로 설계했습니다.

---

## ⭐ 주요 기능 (사용자 흐름 기준)

---

## 1) 메인 홈 (탐색 시작)

<img src="https://github.com/user-attachments/assets/4a75a072-6719-4211-a12b-c43d02a88075" width="260" alt="메인"/>

- 시즌 배너/이벤트 노출  
- 최근 등록/인기/카테고리 탐색 진입점 제공  
- 빠른 탐색 중심 UI 구성  

---

## 2) 상품 탐색 (최근 등록 · 인기 · 카테고리)

| 최근 등록된 상품 | 인기 상품 | 카테고리별 |
|---|---|---|
| <img src="https://github.com/user-attachments/assets/7ad08c3b-34de-4a1c-b2a3-cd7b89388c4c" width="220" /> | <img src="https://github.com/user-attachments/assets/deefbe8e-3177-484a-ab9b-6ebf578dd520" width="220" /> | <img src="https://github.com/user-attachments/assets/6ef6912d-4a68-4962-bd92-a056bc7ae293" width="220" /> |

- **최근 등록**: 최신 트렌드를 빠르게 탐색  
- **인기 상품**: 좋아요 기반 랭킹 제공  
- **카테고리별**: 대분류/소분류 + 성별 탭 기반 UX 제공  

---

## 3) 상품 정보 (상품 상세 · 마이샵)

| 상품 상세 | 마이샵 |
|---|---|
| <img src="https://github.com/user-attachments/assets/dd066a3f-6954-46f4-a2e2-4eee85dba5c1" width="240" /> | <img src="https://github.com/user-attachments/assets/c22e0efd-fa47-443f-a50a-9a1a85f5c241" width="240" /> |

- **상품 상세**: 이미지/가격/태그/카테고리/설명 + 좋아요  
- **마이샵**: 판매 중/완료 상품 관리, 평점/프로필 관리  

---

## 4) 장바구니 (구매 준비)

<img src="https://github.com/user-attachments/assets/9a051969-1392-44eb-9420-319d512a4893" width="260"/>

- 전체/개별 선택  
- 판매자별 금액 분리 및 합산  
- 실시간 총 금액 반영  

---

## 5) 쿠폰 (혜택 적용)

| 쿠폰 등록 | 쿠폰 확인 |
|---|---|
| <img src="https://github.com/user-attachments/assets/61e33dca-c638-4ca1-bc0c-a9a431645d98" width="240" /> | <img src="https://github.com/user-attachments/assets/e94a1426-cca2-4c66-8ff1-286489e3354d" width="240" /> |

- 쿠폰 코드 입력 및 저장  
- 적용 가능 여부 확인  
- 결제 UX 강화  

---

## 6) FITPAY 충전 & 결제 (결제 플로우)

| FITPAY 구매화면 | QR 결제 |
|---|---|
| <img src="https://github.com/user-attachments/assets/027a746b-b0c0-4153-ae45-32e89eb57e38" width="240" /> | <img src="https://github.com/user-attachments/assets/65ed784b-02fd-4a41-a223-897c49312121" width="240" /> |

- FitPay 잔액 확인/충전  
- 카카오페이 QR 결제 연동  
- 결제 흐름 전환 (QR → 완료 화면)  

---

## 7) 관리자 페이지 (운영/관리)

<img src="https://github.com/user-attachments/assets/0a6c413e-5477-423c-964b-373fdfa43558" width="900"/>

- 사용자 상태 관리  
- 누적 주문/결제 데이터 조회  
- CSV Export  
- ADMIN 전용 접근 제어  

---

# 🚀 설계 방향 (Architecture Overview)

## 🔐 인증/인가(Security)
- **Spring Security + JWT**
- Access Token(헤더), Refresh Token(HttpOnly 쿠키)
- Refresh Token DB 저장 → 도난·만료 관리 강화
- Role 기반 접근 제한(MEMBER / ADMIN)
- Logout 시 Refresh Token 즉시 삭제

## ⚡ 비동기 이벤트 처리
- **Kafka**로 좋아요 이벤트 비동기 처리  
- **Redis**로 좋아요 실시간 읽기 캐싱  
- **Spring Batch**로 Redis 누적 데이터 → DB 일괄 반영  
  - 성능 + 정합성 확보

## 🛠 예외 처리
- 프로젝트 전역에서 사용하는 **커스텀 에러코드 시스템**
- 일관된 JSON 포맷으로 반환
- 프론트의 예외 핸들링 구조 최적화

---

# 📚 API 명세 (Swagger 기반)

### 📌 Swagger 문서
- Local: `http://localhost:8080/swagger-ui/index.html`
- SwaggerHub: https://app.swaggerhub.com/apis/none-f10-fb1/fitloop-api/1.0.0

### 🧾 요청 예시

#### 회원가입
```json
{
  "username": "testuser",
  "password": "P@ssw0rd!",
  "name": "홍길동",
  "birthday": "1995-05-15",
  "email": "test@example.com"
} 
```
---

<br><br>

## Tech Stack (어필 포인트)

### Frontend
- **Next.js 15** / **React 19** / **TypeScript**
- **Tailwind CSS** + **Ant Design** 기반 UI 구성
- **TanStack Query**로 서버 상태 관리 및 캐싱/리패칭 최적화
- **Axios** 기반 API 통신 레이어 분리
- **Zustand**로 전역 상태(예: 좋아요/유저 상태 등) 관리
- **Recharts**로 데이터 시각화 구성
- **react-cookie**로 쿠키 기반 인증 처리
- **lottie-react**로 인터랙션/모션 UX 강화

---

### Backend
- **Spring Boot** 기반 REST API
- **MySQL** + **JPA/Hibernate** 기반 데이터 모델링 및 영속성 관리
- **Spring Security + JWT** 인증/인가 구조
- **Google OAuth2 로그인** 연동
- **AWS S3** 이미지 업로드/조회 처리
- **Redis** 기반 캐싱/상태 데이터 처리 (예: 좋아요/실시간 카운트 확장)
- **Kafka** 기반 이벤트 처리 구조(Producer/Consumer, JsonSerializer/Deserializer)
- **Spring Batch**
  - **Redis에 누적된 좋아요 수(실시간 카운트)를 일정 주기로 DB에 일괄 반영**하기 위한 배치 처리로 활용
- **아임포트(Iamport) 결제 연동** 기반 외부 결제 API 연계

> ⚠️ 보안: API Key/Secret, DB 비밀번호, JWT Secret 등 민감정보는  
> `.env` 또는 `application-secret.yml`로 분리하고 Git에 포함하지 않습니다.

---
<br>

### 🧩 Kafka 적용

대량의 사용자 이벤트를 비동기적으로 처리하기 위해 Kafka를 도입하였습니다. <br>

<p align="center">
  <img src="https://github.com/user-attachments/assets/f567283d-44e0-46a4-9e91-86b0674d4442" alt="FITLOOP Kafka">
</p>

* **사용 목적**: 좋아요 이벤트 처리, 향후 알림/조회수/활동 로그 처리 확장 대비
* **적용 범위**: Like 기능 전반 (이벤트 발행/소비)
* **Kafka 토픽 구성**:

  * `user-like-events`: 유저가 좋아요한 상품 ID 기록용
  * `product-like-events`: 특정 상품의 좋아요 수 집계용
* **설정 값**:

  * 파티션: 3개 (병렬 처리)
  * 복제: 2개 (데이터 내구성 확보)

Kafka를 중심으로 `Producer (서비스)` → `Topic` → `Consumer (Redis 반영)` → `Spring Batch` → `DB 저장`까지의 구조를 구성함으로써, **확장성 · 내결함성 · 비동기성**을 동시에 확보하였습니다.

---

<br>

## 🌱 마무리

FITLOOP은 단순 중고 거래를 넘어,  
**패션 콘텐츠와 커뮤니티를 연결하고 재사용을 촉진하는 순환형 플랫폼**을 목표로 개발했습니다.
