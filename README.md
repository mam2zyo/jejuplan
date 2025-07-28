아래는 팀원들에게 전달할 수 있는 형식으로 정리한 문서입니다. **로컬 테스트 환경에서의 인증/인가 및 S3 설정**에 대한 내용을 포함하고 있습니다:

---

##  JejuTrip 프로젝트 - 로컬 테스트 환경 안내

### 1. 테스트용 사용자 계정 생성

`local` 프로파일에서 애플리케이션 실행 시 `TestUserGenerator`가 자동 실행되어, **아래의 두 사용자 계정이 DB에 등록**됩니다:

| 사용자 유형 | 이메일                     | 비밀번호            | 권한        |
| ------ | ----------------------- | --------------- | --------- |
| 관리자    | `codecraft@example.com` | `code1234`      | `MANAGER` |
| 일반 사용자 | `hello@google.com`      | `jejutrip#2025` | `USER`    |

> 이 계정들은 **Spring Security 기반 인증 및 인가 테스트**용으로 사용됩니다.

---

### 2.  로컬 S3(LocalStack) 설정

프로젝트에서는 S3를 로컬에서 사용할 수 있도록 `LocalStack` 기반 설정(`LocalS3Config`, `S3BucketInitializer`)을 제공합니다.

#### 사용 조건:

* `local` 프로파일일 때만 적용됩니다.
* 로컬에서 S3 관련 기능(파일 업로드 등)을 테스트할 수 있습니다.

#### LocalStack 실행 방법:

윈도우 환경에서는 프로젝트 루트 디렉토리에서 다음 명령어를 실행하세요:

```bash
docker-compose up -d
```

> 위 명령어는 `docker-compose.yml` 파일에 정의된 S3(LocalStack) 컨테이너를 **백그라운드 모드로 실행**합니다.

---

### 3.  파일 업로드 기능이 필요 없는 경우

로컬 환경에서 **파일 업로드(S3)를 사용하지 않을 경우**, 다음과 같이 설정하시면 됩니다:

* `S3BucketInitializer` 클래스에 있는 `@Component` 어노테이션을 **주석 처리하거나 삭제**하면, 로컬 S3 초기화가 생략됩니다.

```java
//@Component
@Profile("local")
public class S3BucketInitializer {
    ...
}
```

---

### 4. ️ 참고 사항

* `S3BucketInitializer`는 애플리케이션 시작 시 S3 버킷 존재 여부를 확인하고, 없을 경우 자동으로 생성합니다.
* 버킷 이름은 `application-local.yml` 또는 `application.yml`의 다음 설정에서 정의됩니다:

```yaml
spring:
  cloud:
    aws:
      s3:
        bucket: your-bucket-name
```

---
