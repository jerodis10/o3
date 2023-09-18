# 환급액 조회 프로그램

## 개발 환경
- Java 11
- Gradle 7.6.1
- Spring Boot 2.7.15
- JPA
- H2


## Swagger 주소
- /swagger-ui.html


## 요구사항 구현 
#### 회원 가입 API
  - 필수 파라미터 및 회원 정보 validation 체크
  - 패스워드, 주민등록번호 암호화

#### 회원 로그인 API
  - 필수 파라미터 유효성 체크
  - spring security + JWT 활용한 로그인 구현

#### 회원 정보 조회 API
  - 요청 헤더에 있는 JWT로 회원 조회

#### 회원 환급액 정보 스크랩 API
  - 요청 헤더에 있는 JWT로 회원 조회
  - OpenFeign 라이브러리 통한 외부 API 호출
  - Resilience4J 서킷브레이커 패턴 적용
  - 환급액 계산에 사용되는 필수 정보 별도 테이블(TAX)에 insert
  - JPA의 비관락을 통한 동시성 제어

#### 환급액 조회 API
  - 요청 헤더에 있는 JWT로 회원 및 환급액 정보 조회
  - 환급액 계산