#스케쥴러 샘플 프로젝트
## 목표
해당 스케쥴러 Rest API기반에 JPA 기반으로 TDD 방식을 접목하는 샘플형 프로젝트를 목표로 하며, 향후 프로젝트 진행시 바로바로 해당 소스에 필요한 부분을 뜯어내서 구성 가능하게 하는 것을 목표로 합니다.

## 스팩
- spring boot 2.4.5
- JPA
- MySQL [ 스키마 : schedulerdb ]
- hikari
- H2 [ Test DB 구축]

## Version
1.0 초기 셋팅 버전
  - 1.0.0 : init gradle 프로젝트 생성 [2021-04-28]
  - 1.0.1 : DB Setting [mysql, jpa] [2021-04-28]
  - 1.0.2 : Rest API Config [2021-04-29]
  - 1.0.3 : Logger [2021-04-29]
  - 1.0.4 : DB Create 셋팅 [2021-05-02]
  - 1.0.5 : Test 전용 DB 구축 [2021-05-04]
  - 1.0.6 : Rest Template Config
  - 1.0.7 : Thread Config
  - 1.0.8 : Excption Handler

## 1.0.4 DB Create 셋팅 방법
1. schema-mysql.yml : DDL 설정
2. data-mysql.sql   : DML 설정 ( 초기 데이터 셋팅 )
3. application.yml 파일 아래와 같이 설정 후 서버 구동
```yml
spring.datasource.initialization-mode = always
```
4. application.yml 파일 변경
```yml
spring.datasource.initialization-mode = never
```

