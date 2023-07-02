# JetTrivia
퀴즈 앱

## 배울 내용
### Clean Architecture
### [Retrofit](https://square.github.io/retrofit/) 

**Converter**
기본적으로 Retrofit은 HTTP 본문을 OkHttp의 ResponseBody 유형으로만 역직렬화할 수 있으며 @Body에 대한 RequestBody 유형만 허용할 수 있습니다.

다른 유형을 지원하도록 Converter를 추가할 수 있습니다. 

6개의 형제 모듈은 사용자의 편의를 위해 널리 사용되는 직렬화 라이브러리를 조정합니다.
- Gson: com.squareup.retrofit2:converter-gson
- Jackson: com.squareup.retrofit2:converter-jackson
- Moshi: com.squareup.retrofit2:converter-moshi
- Protobuf: com.squareup.retrofit2:converter-protobuf
- Wire: com.squareup.retrofit2:converter-wire
- Simple XML: com.squareup.retrofit2:converter-simplexml
- JAXB: com.squareup.retrofit2:converter-jaxb
- Scalars (primitives, boxed, and String): com.squareup.retrofit2:converter-scalars

다음은 역직렬화에 Gson을 사용하는 GitHubService 인터페이스의 구현을 생성하기 위해 GsonConverterFactory 클래스를 사용하는 예제입니다.

```kotlin
val retrofit: Retrofit = Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service: GitHubService = retrofit.create(GitHubService::class.java)
```

## 참고
- [Json dummy](https://raw.githubusercontent.com/itmmckernan/triviaJSON/master/world.json)