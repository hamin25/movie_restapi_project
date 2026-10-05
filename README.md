README.md 작성
① 프로젝트 소개
주제와 관리하는 데이터 : Movie data management로 title, director, genre, runningtime, score(평점), id 6가지 데이터 필드를 다룬다. 
프로젝트 구조 : 사용자와 Controller 로 소통 -> MovieService를 통해 실제 로직 -> MovieRepository(Interface) -> MovieMemoryRepository로 데이터 관리
로컬 실행 방법 : application의 main 실행 후, port번호 확인하고 이를 http://localhost:8080/api/movies로 요청해서 사용
API Endpoint 표 : 
Method      URL     기능          성공      실패
POST    /api/movies	영화 등록	    201 Created	400 (잘못된 입력)
GET	    /api/movies	전체 조회	    200 OK	-
GET	    /api/movies/{id} 단건 조회 200 OK	404
PUT	    /api/movies/{id} 수정	200 OK	400, 404
DELETE	/api/movies/{id} 삭제	204 No Content	404
GET	/api/movies/search?score={점수}	점수로 조회	200 OK	400 (score 누락)
요청·응답 JSON 예시 :
{
"title" : "스파이더맨",
"director" : "데니얼",
"genre" : "액션",
"runningTime" : "142",
"score" : "3"
}
GitHub Repository URL과 배포 URL :
1. https://github.com/hamin25/movie_restapi_project
2. https://movie-restapi-project.onrender.com/api/movies


② 개발환경 및 Dependency
IDE : 26.2
JDK : 21
Spring Boot : 4.1.1
Build Tool : gradle  9.7.1
데이터 저장 : LinkedHashMap<Long, Movie>
배포 환경 : docker , render (https://movie-restapi-project.onrender.com/api/movies)
사용한 Dependency의 이름과 프로젝트에서 필요한 이유도 작성합니다. build.gradle 전체를 복사하는 대신 직접 선택하거나 추가한 Dependency를 설명합니다.
: @RestController, @GetMapping 등 요청 매핑 어노테이션, JSON과 Java 객체를 자동 변환


③ Solution 분석
STEP 1의 학습 항목 중 질문과 답변 5개 이상을 작성합니다. 각 답변에 관련 클래스·메서드 이름을 적습니다.
* Controller → Service → Repository → Memory의 요청 처리 흐름 : application을 통해 프로그램을 실행시키면 컨트롤러를 통해 어떤 역할을 실행할지부터 입력 받는다. 어떤 명령인지에 따라 api/books(/id)를 입력받아 post, get, put, delete 등 HTTP 형태로 받아 이에 맞는 메서드를 실행한다.  이때 Json은 request body를 통해 Bookrequest로 변환되고, id는 PathVariable으로 받는다. 이렇게 해당 기능을 입력받으면 service가 실제 로직을 맡는다. repository와 직접 소통하며 CRUD기능의 어떤 역할을 수행해야할지 정하고 return 받아 controller에게 다시 전달한다. repository는 실제 데이터를 저장하는 곳으로 interface와 memory부분으로 나뉜다. interface에서드 이 메서드가 어떤 명령을 실행해야하는지 정의하는 부분이고 memory는 interface 메서드를 override 하여 데이터에 대한 실제 동작을 수행한다. 특히 이번 코드는 Map을 사용하기 때문에 id를 key 값으로 활용하여 데이터를 관리하고 있고 결과는 service에서 BookResponse 로 변환되어 controller를 거쳐 JSON으로 응답된다.
* BookRequest, Book, BookResponse의 역할 : BookRequest는 사용자가 보낸 데이터를 받는 requestDTO 이고 BookResponse는 사용자에게 돌려주는 ResponseDTO이다. id가 repository에서 생성되기 때문에 받을 때는 id없이 줄때는 id와 함께 반환된다는 특징이 있다.
* 새 데이터의 ID가 생성되는 위치 : repository에서 생성되고 있다. Sequence 변수를 통해 새로 생성될 때마다 1씩 증가하며 들어오는 데이터에 새로운 id를 Key 값으로 생성하고 map에 저장한다.
* 존재하지 않는 ID에 대해 404가 반환되는 과정 : service에서는 service에서만 사용하기 위해 private으로 선언된 메서드 findBook과 toResponse가 있다. repository에서 id 에 일치하는 book 객체를 가져오는데, 만약 해당 id에 대한 객체 값이 없다면 빈 optional이 되므로 예외를 발생시켜 controller에서 404 Not Found 응답으로 바꿔 출력한다.
* Domain 객체를 Response DTO로 변환하는 과정 : toResponse 메서드에서 객체에 대해 getter로 값을 하나씩 다 꺼낸다음 생성자를 호출 후 반환한다.

④ 개발 과정 요약  프로젝트를 만든 과정을 5단계 내외로 순서대로 정리합니다. 각 단계에서 다음 내용을 간단히 설명합니다.
무엇을 만들거나 변경했는가? : 기존 book rest api 폴더에서 domain을 movie로 바꿨으면 새로운 조회 기능과 입력값 범위 확인 기능을 추가했다.
어느 클래스·메서드를 작성했는가? : MovieRepository 인터페이스 - save, findAll, findById, update, deleteById, findByScore, 
실행 또는 테스트로 어떻게 확인했는가? : postman을 활용하여 GET, POST, DELETE, PUT 을 테스트하였다.


⑤ 기능 수정·확장  STEP 5의 A와 B에 대해 다음 내용을 작성합니다.
기능을 추가한 이유 : 제목, 감독은 영화의 필수 항목이라 생각해 필수 입력 처리를 score는 5점 만점으로 유효한 값을 입력받을 수 있게했다. 평점 몇 이상인 영화만 따로 조회하는 기능이 있으면 영화 데이터 관리에 유용할 것 같아 선택했다.
수정한 클래스와 메서드 : MovieController.create(), MovieController.update() 안에 validate함수를 따로 생성해 추가 혹은 수정 전 해당 함수를 작동하게 했다. MovieRepository에 아예 새로운 메서드인 findByScore 메서드를 추가했다.
테스트에 사용한 요청과 예상 결과 : 유효하지 않은 결과(빈 영화 제목 및 감독 칸, score1~5점 사이) 는 400번이 나올 것이다. 
실제 응답 결과 :
{
"timestamp": "2026-10-05T13:57:51.728Z",
"status": 400,
"error": "Bad Request",
"path": "/api/movies"
}


⑥ 배포 과정 요약
자신이 수행한 빌드 및 배포 순서 : Dockerfile 생성 -> github push -> render에서 배포
배포를 위해 추가하거나 수정한 파일·설정 : Dockerfile, 수업시간에 활용한 파일 중 기존 프로그램에 맞게 자바 버전 21로 변경 후 사용 
배포 중 발생한 문제와 해결 방법 : dockerfile이 최상단에 위치했어야 하는데 잘못된 곳에 위치해서 최상단 위치로 옮긴 후 push 해 다시 시도함
배포 URL로 확인한 요청과 응답 : https://movie-restapi-project.onrender.com/api/movies
전체조회 - [{"id":1,"title":"스파이더맨","director":"데니얼","genre":"액션","runningTime":142,"score":3}]

⑦ Weekly Report
Key Learning: 직접 구현하며 이해한 내용 3가지
1. 계층 분리와 의존성 주입, Controller에서 new로 직접 생성하지 않아도 @service를 bean으로 만들어 생성자에 자동으로 주입하는 것 
2. DTO : 사용자와 서버가 주고 받는 데이터 형식 정하는 방법
3. REST API에서는 URL로 대상을, HTTP 메서드로 동작을 표현, 데이터는 JSON으로 주고받기

Problem & Solution: 개발 또는 배포 중 겪은 문제 1개와 해결 과정
- Web server failed to start. Port 8080 was already in use. 에러 발생, lsof -i :8080로 PID 찾아 직접 kill 명령어를 통해 종료
- annotation 뒤에는 ;을 붙이면 안된다. 

Code Review: 자신이 작성한 중요한 메서드 1개와 동작 설명
- public List<Movie> findByScore(Long score) {
    List<Movie> result = new ArrayList<>();
    for(Movie movies : store.values()){
        if(movies.getScore()>=score) {
            result.add(movies);
        }
    }
    return result;
  }
: 새로운 추가 기능 구현, for문으로 전체 영화 목록 훑으면서 특정 평점 이상인 것 리스트에 다 추가해 리턴함

AI Usage: AI에 질문한 내용, 참고한 답변, 직접 확인·수정한 부분 
1. 새로운 추가기능을 만들 때, id처럼 하나의 값만 가져오는 것도 아니고 findAll처럼 모든 데이터를 가져오는 것도 아닌 경우 return으로 받을 걸 어떻게 정의해야할지 모르겠어서 도움을 받았다
2. delete()에서 findBook()을 호출하는 이유 : Map.remove()는 없는 key에도 에러가 없어 404를 내려면 존재 확인이 필요
3. 점수 조회를 Service와 Repository 중 어디에 만들지 : 둘의 차이와 Repository 방식이 계층 구조상 정석인 이유를 알게 되었고 repository에 새로운 메서드를 얻는 방식으로 선택함

Reflection: 더 공부하고 싶은 내용 또는 궁금한 점 : 이해가 잘 되지 않아 입력 검증을 if문으로 직접 구현했는데, @Valid와 검증 어노테이션을 사용하는 방식을 해보고 싶다.
건의사항 : 지금처럼 복습하면서 수업 받는 기회가 많이 있으면 좋겠습니다!