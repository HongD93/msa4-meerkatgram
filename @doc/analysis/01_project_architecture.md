# 프로젝트 아키텍처 (Project Architecture)

## 1. 시스템 구조 (System Architecture)
본 프로젝트인 `Meerkatgram`은 사용자가 이미지 게시글을 올리고 소통하는 커뮤니티형 웹 애플리케이션입니다. 
백엔드(Spring Boot 3)와 프론트엔드(Vue 3)가 분리되어 있으며 HTTP API를 통해 통신하는 모던 웹 애플리케이션 구조를 가집니다.

- **Backend**: Java 17, Spring Boot 3.5, Spring Security, MyBatis
- **Database**: MySQL 8.4
- **Frontend**: Vue 3 (프론트엔드 레포지토리 별도 관리)
- **CI/CD 및 인프라**: Jenkins 기반의 파이프라인과 Docker 이미지 빌드, Kubernetes(K8s) 배포 방식을 채택하고 있습니다.

## 2. 도메인 주도 패키지 구조 (Package Structure)
코드 베이스는 기능별 응집도를 높이기 위해 도메인별로 패키지를 분리한 형태를 취하고 있습니다.

### `domain/` (비즈니스 로직)
- **성격**: 비즈니스 기능 단위별로 코드를 모아둔 곳입니다.
- **주요 도메인**: 
  - `auth/`: 로그인, 로그아웃, 토큰 재발급 등 인증 관련 기능
  - `file/`: 프로필/게시글 이미지 등의 파일 업로드 기능
  - `post/`: 게시글 작성, 조회, 삭제 등 게시글 기능
  - `user/`: 회원가입, 유저 조회 등 사용자 관리 기능

### `global/` (공통 코드)
- **성격**: 모든 도메인에서 공통적으로 사용하는 설정 및 유틸리티 코드입니다.
- **주요 패키지**:
  - `config/`: CORS 및 웹(리소스) 설정
  - `errors/`: 전역 에러 처리를 위한 GlobalExceptionHandler 및 커스텀 예외
  - `responses/`: API 공통 응답 규격(GlobalRes)
  - `security/`: Spring Security 설정, JWT 토큰 발급 및 검증 필터 로직
  - `util/`: 파일 관리 등 공통 유틸리티

## 3. 의존성 방향
- `domain` 패키지 코드는 `global` 패키지를 참조하여 사용할 수 있습니다. (예: 공통 예외 발생, 공통 응답 객체 반환)
- 하지만 `global` 코드는 특정 `domain`의 코드를 알아서는 안 되는 **단방향 의존성** 규칙을 가집니다.
