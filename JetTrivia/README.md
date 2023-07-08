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


## Text - buildAnnotatedString

```kotlin
Text(text = buildAnnotatedString {
    withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
        withStyle(
            style = SpanStyle(
                color = AppColors.mLightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp
            )
        ) {
            append("Question $counter/")
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                )
            ) {
                append("$outOf")
            }
        }
    }
})
```

### ParagraphStyle
- 단락에 대한 단락 스타일 구성입니다. 
- 스팬 스타일과 단락 스타일의 차이점은 단락 스타일은 전체 단락에 적용할 수 있는 반면 스팬 스타일은 문자 수준에서 적용할 수 있다는 점입니다. 
- 텍스트의 일부가 단락 스타일로 표시되면 해당 부분은 줄 바꿈 문자를 추가한 것처럼 나머지 부분과 분리됩니다.

```kotlin
withStyle(style = ParagraphStyle(textIndent = TextIndent(10.dp, 5.dp)))
```
- TextIndent 를 설정하면 단락 스타일이 적용되어 있는 범위에서 줄바꿈 단위로 문자열 시작시 들여쓰기 등을 할수 있습니다.
- `append("\n텍스트")` 이런식으로 줄바꿈을 해야 단락 스타일이 적용되는것을 볼 수 있습니다.

### SpanStyle
- 텍스트 스팬에 대한 스타일 구성입니다. 
- 이 구성은 문자 수준 스타일링만 허용하며 줄 높이 또는 텍스트 정렬과 같은 단락 수준 스타일링을 설정하려면 단락 스타일을 참조하세요.