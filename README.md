# ๐์ค์ผ์ฅด๋ฌ ์ํ ํ๋ก์ ํธ
## ๐๋ชฉํ
ํด๋น ์ค์ผ์ฅด๋ฌ Rest API๊ธฐ๋ฐ์ JPA ๊ธฐ๋ฐ์ผ๋ก TDD ๋ฐฉ์์ ์ ๋ชฉํ๋ ์ํํ ํ๋ก์ ํธ๋ฅผ ๋ชฉํ๋ก ํ๋ฉฐ, ํฅํ ํ๋ก์ ํธ ์งํ์ ๋ฐ๋ก๋ฐ๋ก ํด๋น ์์ค์ ํ์ํ ๋ถ๋ถ์ ๋ฏ์ด๋ด์ ๊ตฌ์ฑ ๊ฐ๋ฅํ๊ฒ ํ๋ ๊ฒ์ ๋ชฉํ๋ก ํฉ๋๋ค.

- ํ๋ฃจ์ ํ๋ฒ์ฉ์ ์์ฑ์ ๋ชปํ๋๋ผ๋ ์ปค๋ฐํ๋ ๊ฒ์ ๋๋ฒ์งธ ๋ชฉํ

## ๐์คํฉ
### ๊ธฐ๋ณธ ์ฌ์
- spring boot 2.4.5
- JPA
- MySQL [ ์คํค๋ง : schedulerdb ]
- hikari
- H2 [ Test DB ๊ตฌ์ถ]
### Util
- gson
- jackson
- validation-api
### IDLE
 * Intellij ๋ก ๊ตฌ์ถ
### Front
- node 14.17.0
- npm 6.14.13
- Vue CLI v4.5.13
- Vuetify 2.4.0


# โVersion

## 1.0 ์ด๊ธฐ ์ํ ๋ฒ์ 
  - 1.0.0 : init gradle ํ๋ก์ ํธ ์์ฑ [2021-04-28]
  - 1.0.1 : DB Setting [mysql, jpa] [2021-04-28]
  - 1.0.2 : Rest API Config [2021-04-29]
  - 1.0.3 : Logger [2021-04-29]
  - 1.0.4 : DB Create ์ํ [2021-05-02]
  - 1.0.5 : Test ์ ์ฉ DB (H2 Memory) ๊ตฌ์ถ [2021-05-04]
  - 1.0.6 : Rest Template Config [2021-05-05]
  - 1.0.7 : Thread Config [2021-05-05]
  - 1.0.8 : Exception Handler [2021-05-05]

## 1.1 Scheduler API ๊ตฌํ
  - 1.1.0 : Scheduler DB ํ์ด๋ธ ์คํค๋ง ์ค๊ณ [2021-05-06]
  - 1.1.1 : ์ฌ์ฉ์ ๊ณ์  ์ผ๋ฐ ์ฌ์, ์ค์ผ์ฅด๋ฌ ๊ธฐ์ด CRUD Rest API ์ค๊ณ [2021-05-13]
  - 1.1.2 : ์ฌ์ฉ์ ๊ณตํด์ผ CRUD Rest API ์ค๊ณ [2021-05-15]
  - 1.1.3 : ๊ฐ๋จํ View ํ๋ฉด ๊ฐ๋ฐ [2021-05-20]
     - ์๋ฒ๊ฐ๋ฐ์ด ๋ชฉํ๊ธฐ ๋๋ฌธ์ Vue CLI 3 ์ผ๋ก ํ๋ก ํธ ๊ตฌ์ฑ

## 1.2 Spring Security
  - 1.2.0 : Spring Security ์ค๊ณ [2021-05-21]
  - 1.2.1 : Spring Security ์ถ๊ฐ [2021-05-21]
  - 1.2.2 : ์ฌ์ฉ์ ๊ณ์ ์ Spring Security ์ฐ๋ [2021-05-25]

## 1.3 Spring Batch
  - 1.3.0 : Spring Boot Batch ์์ฑ [2021-05-30]  
  - 1.3.1 : ๊ณตํด์ผ ๊ณต๊ณต๋ฐ์ดํฐ ์ฐ๊ณ [2021-06-03]

# ๐ Version ๋ณ ํน์ด์ฌํญ
## 1.0.4
### DB Create ์ํ ๋ฐฉ๋ฒ
1. schema-mysql.yml : DDL ์ค์ 
2. data-mysql.sql   : DML ์ค์  ( ์ด๊ธฐ ๋ฐ์ดํฐ ์ํ )
3. application.yml ํ์ผ ์๋์ ๊ฐ์ด ์ค์  ํ ์๋ฒ ๊ตฌ๋
```yml
spring.datasource.initialization-mode = always
```
4. application.yml ํ์ผ ๋ณ๊ฒฝ
```yml
spring.datasource.initialization-mode = never
```

## 1.0.8 
### Intellij ์ฝ์ ๋ก๊ทธ ํ๊ธ ๊นจ์ง ํด๊ฒฐ ๋ฐฉ๋ฒ
- Intellij File Encodings ๋ณ๊ฒฝ
1. Ctrl + alt + S
2. Editor > File Encodings ์ ํ
3. ์ํ
* Global Encoding : UTF-8
*  Project Encoding : UTF-8
* Default encoding fot properties files : UTF-8

- Intellij VM Options ์์ 
1. Shift ์ฐ์ ๋๋ฒ ํด๋ฆญ > Edit Custom WM Options ์ ํ
2. ์ตํ๋จ์ ์ถ๊ฐ
```
-Dfile.encoding=UTF-8
```
### ApiResult ์ค์ 
ํด๋น ํ๋ก์ ํธ๋ ๋ชจ๋  ๊ฒฐ๊ณผ ๊ฐ ํํ
```aidl
{
    code: 100
    message: 'SUCCESS'
    data: {}
}
```

## 1.1.1 
### LazyInitialization ์๋ฌ ์ฒ๋ฆฌ
```aidl
LazyInitializationException: could not initialize proxy - no session
```
์ค์ผ์ฅด๋ฌ๋ฅผ ์กฐํํ๋ ๊ณณ์์ ๋ฐ์ํ๊ฒ๋๋ ์๋ฌ์์ต๋๋ค.
์ด๊ฒ์ `@ToMany` ๊ด๊ณ์์ ์กฐํ๋ฅผ ํ  ๋ ์ง์ฐ๋๋ฉด์ ๋ฐ์ํฉ๋๋ค.

ํด๊ฒฐ ๋ฐฉ๋ฒ
> CASE1 : FetchType.EAGER ๋ฅผ ์ฌ์ฉํ์ฌ ์ฆ์ ๋ก๋ฉ์ผ๋ก ๋ฐ๊พผ๋ค.

ํ์ง๋ง ์ด ๋ฐฉ๋ฒ์ ์ฌ์ฉ ํ  ์ ์๋ ๊ฒฝ์ฐ๋ ๋ฐ์ํฉ๋๋ค.
> CASE2 : @Transactional(readOnly = true)) ๋ฅผ ์ฌ์ฉํ์ฌ ์กฐํ ์์ ๊น์ง ์ธ์์ ์ ์งํฉ๋๋ค.
```java
@Transactional(readOnly = true)
public List<SchedulerInfoView> findSchedulers(Long id, String year, String month){
    ...
}
```

## 1.1.3
### Vue CLI 3 ์ค์น
1. Node ์ค์น
2. Vue CLI ์ค์น
```shell
npm i -g @vue/cli
vue --version
```
vue ๋ฒ์ ์ด 3.x ์ด์์ธ์ง ํ์ธ
```shell
npm i -g @vue/cli-init
```
vue ํ๋ก์ ํธ ์์ฑ
```shell
vue create front
```
- step1 > Manually select features ์ ํ
- step2 > Vue version, Babel, Router, Vuex, CSS Pre-processors, Linter ๋ค ์ ํํ์์ต๋๋ค.
- step3 > 3.x
- step4 > Stylus
- step5 > `Lint on save` > `In package.json` > `N`
ํ๋ก์ ํธ ์์ฑ ํ
> ํ๋ก์ ํธ  ์คํ
```shell
npm run serve
```
### vuetify ์ ์ฉ
```shell
vue add vuetify
```
Vue cli 3 ๋ฒ์ ์ด๋ฏ๋ก  Preview (Vuetify 3 + Vite) ๋ก ์ค์น

main.js ์ ํด๋น vuetify import 
```javascript
import vuetify from './plugins/vuetify'
```

## 1.3.0
### Spring Boot Batch ์คํ
1. Edit Configurations
2. Spring boot ๋ก ์คํ ์์ฑ
3. Program arguments
```textmate
--job.name=[Job Name] // ์คํํ  Job๋ช์นญ 
[param]=[๊ฐ] // Job Parameter
ex >
--job.name=sampleJob requestDate=20210530
```
4. Run

## 1.3.1
### ๊ณตํด์ผ ๊ณต๊ณต๋ฐ์ดํฐ
1. [๊ณตํด์ผ ๊ณต๊ณต๋ฐ์ดํฐ](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15012690)
2. ํ์ฉ์ ์ฒญ
3. ์ ๋ณด
Url : http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo
   
Param
```textmate
ServiceKey : ๋ฐ๊ธ ๋ฐ์ ์๋น์คํค
solYear : ์กฐํ๋๋
solMonth : ์กฐํ๋ฌ
pageNo : ํ์ด์งNo
numOfRows : ์กฐํ ํ์ด์ง์
```
Result
```textmate
dateName : ๊ณตํด์ผ๋ช
locdate : ๊ณตํด์ผ(YYYYMMdd)
```
