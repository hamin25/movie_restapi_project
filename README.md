* Controller → Service → Repository → Memory의 요청 처리 흐름 : application을 통해 프로그램을 실행시키면 컨트롤러를 통해 어떤 역할을 실행할지부터 입력 받는다. 어떤 명령인지에 따라 api/books(/id)를 입력받아 post, get, put, delete 등 HTTP 형태로 받아 이에 맞는 메서드를 실행한다.  이때 Json은 request body를 통해 Bookrequest로 변환되고, id는 PathVariable으로 받는다. 이렇게 해당 기능을 입력받으면 service가 실제 로직을 맡는다. repository와 직접 소통하며 CRUD기능의 어떤 역할을 수행해야할지 정하고 return 받아 controller에게 다시 전달한다. repository는 실제 데이터를 저장하는 곳으로 interface와 memory부분으로 나뉜다. interface에서드 이 메서드가 어떤 명령을 실행해야하는지 정의하는 부분이고 memory는 interface 메서드를 override 하여 데이터에 대한 실제 동작을 수행한다. 특히 이번 코드는 Map을 사용하기 때문에 id를 key 값으로 활용하여 데이터를 관리하고 있고 결과는 service에서 BookResponse 로 변환되어 controller를 거쳐 JSON으로 응답된다.

* BookRequest, Book, BookResponse의 역할 : BookRequest는 사용자가 보낸 데이터를 받는 requestDTO 이고 BookResponse는 사용자에게 돌려주는 ResponseDTO이다. id가 repository에서 생성되기 때문에 받을 때는 id없이 줄때는 id와 함께 반환된다는 특징이 있다.

* 새 데이터의 ID가 생성되는 위치 : repository에서 생성되고 있다. Sequence 변수를 통해 새로 생성될 때마다 1씩 증가하며 들어오는 데이터에 새로운 id를 Key 값으로 생성하고 map에 저장한다.

* 존재하지 않는 ID에 대해 404가 반환되는 과정 : service에서는 service에서만 사용하기 위해 private으로 선언된 메서드 findBook과 toResponse가 있다. repository에서 id 에 일치하는 book 객체를 가져오는데, 만약 해당 id에 대한 객체 값이 없다면 빈 optional이 되므로 예외를 발생시켜 controller에서 404 Not Found 응답으로 바꿔 출력한다.

* Domain 객체를 Response DTO로 변환하는 과정 : toResponse 메서드에서 객체에 대해 getter로 값을 하나씩 다 꺼낸다음 생성자를 호출 후 반환한다.


README.md 작성
① 프로젝트 소개
주제와 관리하는 데이터 : Movie data management
프로젝트 구조 : 
로컬 실행 방법
API Endpoint 표
요청·응답 JSON 예시
GitHub Repository URL과 배포 URL


② 개발환경 및 Dependency

항목

작성 내용

IDE

사용한 IntelliJ IDEA

JDK

실제 사용한 버전

Spring Boot

프로젝트에서 사용한 버전

Build Tool

Gradle 및 사용한 버전

데이터 저장

사용한 Java Collection

배포 환경

수업에서 사용한 배포 방식(배포 URL)

사용한 Dependency의 이름과 프로젝트에서 필요한 이유도 작성합니다. build.gradle 전체를 복사하는 대신 직접 선택하거나 추가한 Dependency를 설명합니다.

③ Solution 분석

STEP 1의 학습 항목 중 질문과 답변 5개 이상을 작성합니다. 각 답변에 관련 클래스·메서드 이름을 적습니다.

④ 개발 과정 요약

프로젝트를 만든 과정을 5단계 내외로 순서대로 정리합니다. 각 단계에서 다음 내용을 간단히 설명합니다.

무엇을 만들거나 변경했는가?
어느 클래스·메서드를 작성했는가?
실행 또는 테스트로 어떻게 확인했는가?
⑤ 기능 수정·확장

STEP 5의 A와 B에 대해 다음 내용을 작성합니다.

기능을 추가한 이유
수정한 클래스와 메서드
테스트에 사용한 요청과 예상 결과
실제 응답 결과
⑥ 배포 과정 요약

자신이 수행한 빌드 및 배포 순서
배포를 위해 추가하거나 수정한 파일·설정
배포 중 발생한 문제와 해결 방법
배포 URL로 확인한 요청과 응답
⑦ Weekly Report

Key Learning: 직접 구현하며 이해한 내용 3가지
Problem & Solution: 개발 또는 배포 중 겪은 문제 1개와 해결 과정
Code Review: 자신이 작성한 중요한 메서드 1개와 동작 설명
AI Usage: AI에 질문한 내용, 참고한 답변, 직접 확인·수정한 부분
Reflection: 더 공부하고 싶은 내용 또는 궁금한 점
건의사항