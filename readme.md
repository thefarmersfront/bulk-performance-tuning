# BULK 처리 개선해보기
BULK 처리 개선해보기 관련 예시 코드 입니다.


[src/test/java/com/example/bulkinsert/BulkInsertApplicationTests.java](src/test/java/com/example/bulkinsert/BulkInsertApplicationTests.java)에 `jpaTest()`와 `jdbcTest()`로 성능 비교 가능합니다.


## 실행 조건
`TestContainers` 기반이기 때문에 `Docker`가 설치된 환경에서 동작합니다.
