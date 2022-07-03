# triple2022-backend

실행 전 필수사항: application.yml 파일에서 MySQL DB 설정 변경

# 실행방법
소스를 다운받고 해당 경로에서 gradlew bootRun 명령어 실행


# 테스트 케이스
https://docs.google.com/spreadsheets/d/1HU0c76IZgkXIMfmopv-K3r3XUVOEXnxWZC233H-9yrs/edit?usp=sharing

# Rest API
- POST /event :포인트 적립(호출 전에 사용자 아이디, 장소 아이디, 이미지 아이디(업로드시)가 필요)
- POST /saveImage : 리뷰 등록시 이미지 업로드
- POST /savePlace : 사용자의 장소 등록
- POST /signup : 사용자 등록
- POST /login : 로그인
- GET /logout : 로그아웃
- GET /session :세션에 포인트 조회가 가능

# 엔티티
<img width="509" alt="image" src="https://user-images.githubusercontent.com/108323860/177026870-4ec57adc-9e68-4ec8-95ea-c3cd27598c80.png">
