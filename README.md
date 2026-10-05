* Controller → Service → Repository → Memory의 요청 처리 흐름 : application을 통해 프로그램을 실행시키면 컨트롤러를 통해 어떤 역할을 실행할지부터 입력 받는다. 어떤 명령인지에 따라 api/books(/id)를 입력받아 post, get, put, delete 등 HTTP 형태로 받아 이에 맞는 메서드를 실행한다.  이때 Json은 request body를 통해 Bookrequest로 변환되고, id는 PathVariable으로 받는다. 이렇게 해당 기능을 입력받으면 service가 실제 로직을 맡는다. repository와 직접 소통하며 CRUD기능의 어떤 역할을 수행해야할지 정하고 return 받아 controller에게 다시 전달한다. repository는 실제 데이터를 저장하는 곳으로 interface와 memory부분으로 나뉜다. interface에서드 이 메서드가 어떤 명령을 실행해야하는지 정의하는 부분이고 memory는 interface 메서드를 override 하여 데이터에 대한 실제 동작을 수행한다. 특히 이번 코드는 Map을 사용하기 때문에 id를 key 값으로 활용하여 데이터를 관리하고 있고 결과는 service에서 BookResponse 로 변환되어 controller를 거쳐 JSON으로 응답된다.

* BookRequest, Book, BookResponse의 역할 : BookRequest는 사용자가 보낸 데이터를 받는 requestDTO 이고 BookResponse는 사용자에게 돌려주는 ResponseDTO이다. id가 repository에서 생성되기 때문에 받을 때는 id없이 줄때는 id와 함께 반환된다는 특징이 있다.

* 새 데이터의 ID가 생성되는 위치 : repository에서 생성되고 있다. Sequence 변수를 통해 새로 생성될 때마다 1씩 증가하며 들어오는 데이터에 새로운 id를 Key 값으로 생성하고 map에 저장한다.

* 존재하지 않는 ID에 대해 404가 반환되는 과정 : service에서는 service에서만 사용하기 위해 private으로 선언된 메서드 findBook과 toResponse가 있다. repository에서 id 에 일치하는 book 객체를 가져오는데, 만약 해당 id에 대한 객체 값이 없다면 빈 optional이 되므로 예외를 발생시켜 controller에서 404 Not Found 응답으로 바꿔 출력한다.

* Domain 객체를 Response DTO로 변환하는 과정 : toResponse 메서드에서 객체에 대해 getter로 값을 하나씩 다 꺼낸다음 생성자를 호출 후 반환한다.
