# BULK 처리 개선해보기

## 프로젝트 소개
[컬리 기술 블로그](https://helloworld.kurly.com/)의 `BULK 처리 Write에 집중해서 개선해보기` 에서 성능 테스트에 사용한 예시 코드 입니다.

## 시작하기
### 요구사항
- JAVA 17 이상
- Docker 🐳: `TestContainers` 기반이기 때문에 `Docker`가 설치된 환경에서 동작합니다.
### 실행 방법
- jpa 테스트
  ```shell
  $ ./gradlew test --tests com.example.bulkinsert.BulkInsertApplicationTests.jpaTest
  ```
- jdbc 테스트 (jpa에서 성능 개선한 로직)
  ```shell
  $ ./gradlew test --tests com.example.bulkinsert.BulkInsertApplicationTests.jdbcTest
  ```
### 확인
jpaTest와 jdbcTest의 로그와 실행시간의 차이를 확인합니다.
- jpa
<figure style="text-align: center;">
  <img src="/readme-img/jpa.png" alt="jpa test image"/>
  <figcaption><i>(그림: 유형환, © 2023. Kurly. All rights reserved.)</i></figcaption>
</figure>

- jdbc
<figure style="text-align: center;">
  <img src="/readme-img/jdbc.png" alt="jpa test image"/>
  <figcaption><i>(그림: 유형환, © 2023. Kurly. All rights reserved.)</i></figcaption>
</figure>


## 실행 조건
`TestContainers` 기반이기 때문에 `Docker`가 설치된 환경에서 동작합니다.
