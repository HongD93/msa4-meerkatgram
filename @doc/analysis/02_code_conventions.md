# 코드 컨벤션 (Code Conventions)

## 1. 공통 응답 규격 (API Response)
모든 API 응답은 일관성 있게 `GlobalRes<T>`라는 공통 객체로 포장되어 반환됩니다. 클라이언트는 항상 동일한 JSON 구조를 기대할 수 있습니다.
```json
{
  "code": "00", 
  "message": "SUCCESS", 
  "data": { ...실제 데이터... }
}
```
- **성공 시**: code는 `"00"`, message는 `"SUCCESS"` 등을 반환하며 data에 결과를 담습니다.
- **실패 시**: `GlobalExceptionHandler`를 통해 처리되며, 에러별로 지정된 코드(예: `E01`, `E21`, `E99`)와 에러 메시지를 반환합니다.

## 2. DTO (Data Transfer Object) 분리 및 Record 사용
- **Request/Response 분리**: API 요청을 받을 때 사용하는 클래스(Req)와 응답할 때 사용하는 클래스(Res)를 철저히 분리합니다. DB와 매핑되는 **Entity 객체는 절대 클라이언트에 직접 반환하지 않습니다.**
- **Java Record**: DTO는 자바 14의 `record`를 사용하여 불변(immutable) 객체로 선언하여 데이터의 안정성과 간결성을 높입니다.

## 3. 예외 처리 (Exception Handling)
- 컨트롤러나 서비스 로직 내부에 여러 개의 `try-catch`를 작성하지 않습니다.
- 커스텀 예외(예: `InvalidTokenException`, `NotRegisteredException`)를 생성하여 비즈니스 로직 실패 시 `throw` 합니다.
- 발생한 예외는 `@RestControllerAdvice`가 적용된 `GlobalExceptionHandler`에서 잡아 클라이언트에게 정해진 에러 규격으로 응답합니다.

## 4. 데이터베이스 접근 (MyBatis)
- SQL 쿼리문은 Java 코드 내에 직접 작성하지 않고, `src/main/resources/mapper` 하위의 XML 파일에 작성합니다.
- Java에서는 인터페이스에 `@Mapper`를 선언하여 XML의 SQL과 연결합니다.

## 5. 입력값 검증 (Validation)
- 클라이언트로부터 전달되는 데이터는 Controller의 파라미터에 `@Valid` 어노테이션을 붙여 검증합니다.
- DTO 내부에 `@NotBlank`, `@NotNull`, `@Min` 등의 어노테이션으로 규칙을 정의하며, 위반 시 `MethodArgumentNotValidException`이 발생하여 전역으로 에러 처리됩니다.
