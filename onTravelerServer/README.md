## 서버 사용법

### 서버 실행 방법
* config.js에서 mongo db가 설치되어 있는 url과 db를 지정해준다.
* config.js에서 이 서버가 돌아가는 url을 지정해준다.


### 서버 동작 명령어
* 서버 실행 -> npm start
* 서버 중지 -> npm stop
* 서버 동작 확인 -> npm run-script status
* 서버 log 실시간 확인 -> npm run-script log


### rest api 사용법

* **server의 ip:3000/path**<br />
* **path = routes 디렉토리에 있는 파일명 + 각 파일에서 원하는 추가 path**.
<br /><br />
**예**
routes에 buddies.js에 가보면 여러 router.get, router.post 같은 함수가 있다.
이 함수에 보면 추가 path들이 있다. 

여기서 모든 buddy들의 정보를 보고자 한다면
파일명인 buddies(.js라는 확장자명은 뺀다) 에다가 추가 path인 '/'를 더해서
get server_ip:3000/buddies/ 를 하면 된다. (맨 마지막 /는 생략가능)



### rest api의 return 값
* **return_form.js에 각 부분에 맞는 format이 있다.**
<br /><br />
**예**
위에서 했던 get server_ip:3000/buddies/로 해보자
만약 찾았다면 success는 1이고 size는 찾은 버디들의 수, datas에 배열로 버디들의 정보가 들어간다.
만약 찾지못했다면(buddy가 0명이거나) error가 뜨면 success 0이고 size는 0이 된다.
실제로 error가 날 가능성이 낮기 때문에 일반적으로 찾지 못했다고 보면 된다.(아예 0명이어도 성공으로 할까 고민중...)


### 데이터베이스 model
* **각 collection의 schema = models디렉토리의 각 파일**
<br /><br />
required는 필수로 있어야 하는 정보이다.
