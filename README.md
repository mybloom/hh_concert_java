# 콘서트 예약 서비스 

## 1. 프로젝트 설계 
<details>
<summary> Milestone, Flowchart, Sequence diagram</summary>

### Milestone
- [Milestone 문서 링크](https://docs.google.com/spreadsheets/d/1i9oEs6TZyc6xhpA1JSr8YnbOr1RjcxkH)

### Flowchart
<img src="docs/flowchart_concert_241231.png">

### Sequence Diagram

#### <span style="color: green;">1. 예약가능날짜조회API </span>
<img src="docs/concert_예약가능날짜조회API.png">
<br><br><br><br>

#### <span style="color: green;">2. 예약가능좌석정보조회API </span>
<img src="docs/concert_예약가능좌석정보조회API.png">
<br><br><br><br>

#### <span style="color: green;">3. 좌석예약요청API </span>
<img src="docs/concert_좌석예약요청API.png">
<br><br><br><br>

#### <span style="color: green;"> 4. 잔액충전및조회API </span>
<img src="docs/concert_잔액충전및조회API.png">
<br><br><br><br>

#### <span style="color: green;"> 5. 결제API </span>
<img src="docs/concert_결제API.png">
<br><br><br><br>

</details>

## 2. ERD

<img src="docs/erd_concert_202501.png">

## 3. API 명세서 
- [API 명세서 문서 링크](https://github.com/mybloom/hh_concert_java/issues/2)
- [Mock API 링크(로컬용)](http://localhost:8080/swagger-ui/index.html)

## 4. 패키지 구조
<img src="docs/패키지구조.png">