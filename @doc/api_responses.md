# 프로젝트 API 응답(Response) 정리

본 문서는 프로젝트의 코드를 분석하여 각 API별 응답 종류를 대분류, 중분류, 소분류로 정리한 문서입니다.

## 공통 예외 (Global Exceptions)
해당 예외들은 필터 및 `GlobalExceptionHandler`를 거치며 모든 API에서 공통적으로 발생할 수 있는 에러 응답입니다.

| 대분류 (API군) | 중분류 (HttpStatus) | 소분류 (코드) | 설명 | 원인 예외 (Exception) |
|---|---|---|---|---|
| 공통 (Common) | 500 Internal Server Error | E99 | 시스템 에러 | Exception |
| 공통 (Common) | 500 Internal Server Error | E80 | DB 에러 | SQLException |
| 공통 (Common) | 400 Bad Request | E21 | 요청 파라미터 이상 | MethodArgumentTypeMismatchException, MethodArgumentNotValidException |
| 공통 (Common) | 401 Unauthorized | E04 | 토큰 이상 (만료, 위조, 형식 오류 등) | InvalidTokenException |
| 공통 (Common) | 403 Forbidden | E03 | 권한 부족 | AccessDeniedException |
| 공통 (Common) | 401 Unauthorized | E02 | 인증되지 않음 (로그인 필요) | AuthenticationException |

---

## 각 API별 응답 상세

### 1. 인증 API (AuthController)

| 대분류 (API) | 중분류 (HttpStatus) | 소분류 (코드) | 설명 (메시지) |
|---|---|---|---|
| `POST /api/login` | 200 OK | 00 | 로그인 완료 (성공) |
| | 400 Bad Request | E21 | 요청 파라미터 이상 (DTO 유효성 검사 실패) |
| | 401 Unauthorized | E01 | 로그인 에러 (NotRegisteredException - 아이디/비밀번호 불일치) |
| `POST /api/reissue-token` | 200 OK | 00 | 토큰 재발급 완료 (성공) |
| | 401 Unauthorized | E04 | 토큰 이상 (InvalidTokenException - 서명 위조, 만료 등) |
| `POST /api/logout` | 200 OK | 00 | 로그아웃 완료 (성공) |
| | 401 Unauthorized | E02 | 로그인이 필요한 서비스입니다 (AuthenticationException) |
| | 401 Unauthorized | E04 | 토큰 이상 (InvalidTokenException) |
| `POST /api/registration`| 200 OK | 00 | 회원가입 완료 (성공) |
| | 400 Bad Request | E21 | 요청 파라미터 이상 (DTO 유효성 검사 실패) |
| | 409 Conflict | E11 | 이미 가입된 회원 (DuplicatedRecordException) |

### 2. 파일 API (FileController)

| 대분류 (API) | 중분류 (HttpStatus) | 소분류 (코드) | 설명 (메시지) |
|---|---|---|---|
| `POST /api/files/profiles`| 200 OK | 00 | 파일 저장 성공 |
| | 500 Internal Server Error| E40 | 파일 업로드 실패 (FileManagedException - 확장자 제한, 디렉토리 실패 등) |
| `POST /api/files/posts` | 200 OK | 00 | 파일 저장 성공 |
| | 500 Internal Server Error| E40 | 파일 업로드 실패 (FileManagedException) |

### 3. 게시글 API (PostController)

| 대분류 (API) | 중분류 (HttpStatus) | 소분류 (코드) | 설명 (메시지) |
|---|---|---|---|
| `GET /api/posts` | 200 OK | 00 | 정상처리 (성공) |
| `GET /api/posts/{id}` | 200 OK | 00 | 게시글 상세 정상 처리 (성공) |
| | 400 Bad Request | E21 | 파라미터 이상 (id 값 @Min 검증 실패) |
| | 404 Not Found | E10 | 이미 삭제된 게시글 (DeletedRecordException) |

### 4. 유저 API (UserController)

- 현재 작성된 API가 존재하지 않습니다. (`@RestController` 선언만 존재)
