# 🚀스케쥴러 샘플 프로젝트
## 🚀목표
해당 스케쥴러 Rest API기반에 JPA 기반으로 TDD 방식을 접목하는 샘플형 프로젝트를 목표로 하며, 향후 프로젝트 진행시 바로바로 해당 소스에 필요한 부분을 뜯어내서 구성 가능하게 하는 것을 목표로 합니다.

- 하루에 한번씩은 완성을 못하더라도 커밋하는 것을 두번째 목표

## 📚스팩
### 기본 사양
- spring boot 2.4.5
- JPA
- MySQL [ 스키마 : schedulerdb ]
- hikari
- H2 [ Test DB 구축]
### Util
- gson
- jackson
- validation-api
### IDLE
 * Intellij 로 구축
### Front
- node 14.17.0
- npm 6.14.13
- Vue CLI v4.5.13
- Vuetify 2.4.0


# ✅Version

## 1.0 초기 셋팅 버전
  - 1.0.0 : init gradle 프로젝트 생성 [2021-04-28]
  - 1.0.1 : DB Setting [mysql, jpa] [2021-04-28]
  - 1.0.2 : Rest API Config [2021-04-29]
  - 1.0.3 : Logger [2021-04-29]
  - 1.0.4 : DB Create 셋팅 [2021-05-02]
  - 1.0.5 : Test 전용 DB (H2 Memory) 구축 [2021-05-04]
  - 1.0.6 : Rest Template Config [2021-05-05]
  - 1.0.7 : Thread Config [2021-05-05]
  - 1.0.8 : Exception Handler [2021-05-05]

## 1.1 Scheduler API 구현
  - 1.1.0 : Scheduler DB 테이블 스키마 설계 [2021-05-06]
  - 1.1.1 : 사용자 계정 일반 사양, 스케쥴러 기초 CRUD Rest API 설계 [2021-05-13]
  - 1.1.2 : 사용자 공휴일 CRUD Rest API 설계 [2021-05-15]
  - 1.1.3 : 간단한 View 화면 개발 [2021-05-20]
     - 서버개발이 목표기 때문에 Vue CLI 3 으로 프론트 구성

## 1.2 Spring Security
  - 1.2.0 : Spring Security 설계 [2021-05-21]
  - 1.2.1 : Spring Security 추가 [2021-05-21]
  - 1.2.2 : 사용자 계정에 Spring Security 연동 [2021-05-25]

## 1.3 Spring Batch
  - 1.3.0 : 공휴일 공공데이터 연계  

# 📄 Version 별 특이사항
## 1.0.4
### DB Create 셋팅 방법
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

## 1.0.8 
### Intellij 콘솔 로그 한글 깨짐 해결 방법
- Intellij File Encodings 변경
1. Ctrl + alt + S
2. Editor > File Encodings 선택
3. 셋팅
* Global Encoding : UTF-8
*  Project Encoding : UTF-8
* Default encoding fot properties files : UTF-8

- Intellij VM Options 수정
1. Shift 연속 두번 클릭 > Edit Custom WM Options 선택
2. 최하단에 추가
```
-Dfile.encoding=UTF-8
```
### ApiResult 설정
해당 프로젝트는 모든 결과 값 형태
```aidl
{
    code: 100
    message: 'SUCCESS'
    data: {}
}
```

## 1.1.1 
### LazyInitialization 에러 처리
```aidl
LazyInitializationException: could not initialize proxy - no session
```
스케쥴러를 조회하는 곳에서 발생하게되는 에러였습니다.
이것은 `@ToMany` 관계에서 조회를 할 떄 지연되면서 발생합니다.

해결 방법
> CASE1 : FetchType.EAGER 를 사용하여 즉시 로딩으로 바꾼다.

하지만 이 방법은 사용 할 수 없는 경우도 발생합니다.
> CASE2 : @Transactional(readOnly = true)) 를 사용하여 조회 시점까지 세션을 유지합니다.
```java
@Transactional(readOnly = true)
public List<SchedulerInfoView> findSchedulers(Long id, String year, String month){
    ...
}
```

## 1.1.3
### Vue CLI 3 설치
1. Node 설치
2. Vue CLI 설치
```shell
npm i -g @vue/cli
vue --version
```
vue 버전이 3.x 이상인지 확인
```shell
npm i -g @vue/cli-init
```
vue 프로젝트 생성
```shell
vue create front
```
- step1 > Manually select features 선택
- step2 > Vue version, Babel, Router, Vuex, CSS Pre-processors, Linter 들 선택하였습니다.
- step3 > 3.x
- step4 > Stylus
- step5 > `Lint on save` > `In package.json` > `N`
프로젝트 생성 후
> 프로젝트  실행
```shell
npm run serve
```
### vuetify 적용
```shell
vue add vuetify
```
Vue cli 3 버전이므로  Preview (Vuetify 3 + Vite) 로 설치

main.js 에 해당 vuetify import 
```javascript
import vuetify from './plugins/vuetify'
```