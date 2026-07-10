# 레이어드 아키텍처 (Layered Architecture)

프로젝트의 각 도메인(`auth`, `post`, `user` 등)은 책임을 분리하기 위해 4~5개의 레이어(계층)로 나뉘어져 있습니다. 
각 레이어는 본인의 역할에만 집중하며, 반드시 **인접한 아래 레이어와만 통신**해야 합니다.

## 1. Filter Layer (Spring Security)
- **위치**: `global/security/filter`
- **역할**: 클라이언트의 HTTP 요청이 Controller에 도달하기 전 가로채어 1차 검문을 수행합니다.
- **주요 작업**: JWT 토큰 존재 여부 확인, 서명 검증, 만료 체크, 권한(인증/인가) 체크 등. 통과하지 못하면 예외를 발생시키고 요청을 차단합니다.

## 2. Controller Layer
- **위치**: `domain/{도메인}/controllers`
- **역할**: 프론트엔드(클라이언트)의 요청(Request)을 수신하고, 최종 응답(Response)을 반환하는 접점입니다.
- **주요 작업**: 
  - URL 엔드포인트 매핑 (`@PostMapping`, `@GetMapping` 등)
  - 파라미터 검증 (`@Valid`)
  - **비즈니스 로직은 작성하지 않으며**, Service 계층의 메서드를 호출하여 작업을 위임합니다.

## 3. Service Layer
- **위치**: `domain/{도메인}/services`
- **역할**: 애플리케이션의 핵심 **비즈니스 로직**을 담당합니다.
- **주요 작업**:
  - 데이터의 가공 및 처리
  - 트랜잭션 관리 (`@Transactional`을 이용한 롤백/커밋 보장)
  - 비즈니스 규칙 위반 시 커스텀 예외 발생
  - Mapper를 통해 DB에 데이터를 저장하거나 가져온 뒤, 결과물을 클라이언트용 DTO(Response)로 변환하여 Controller에 넘깁니다.

## 4. Mapper & Entity Layer (Repository/DAO)
- **위치**: `domain/{도메인}/mapper` 및 `domain/{도메인}/entities`
- **역할**: 데이터베이스와의 직접적인 통신을 담당합니다.
- **주요 작업**:
  - MyBatis의 `@Mapper` 인터페이스와 XML 매퍼 파일을 통해 SQL 질의를 수행합니다.
  - 조회된 릴레이션 데이터(테이블 Row)를 Java의 `Entity` 객체에 담아 Service로 반환합니다.

## ※ 계층 간 데이터 전달 요약
클라이언트 -> `Request DTO` -> Controller -> `Request DTO` -> Service -> `Entity` 가공 -> Mapper -> `DB`
DB -> Mapper -> `Entity` -> Service -> `Response DTO` 변환 -> Controller -> `GlobalRes` 포장 -> 클라이언트
