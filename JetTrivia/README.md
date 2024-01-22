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

## PathEffect
- 드로잉 프리미티브의 지오메트리에 적용되는 효과입니다. 
- 예를 들어 도형을 점선 또는 도형 패턴으로 그리거나 선분 교차점 주위에 처리를 적용하는 데 사용할 수 있습니다.

```kotlin
fun cornerPathEffect(radius: Float): PathEffect = actualCornerPathEffect(radius)
 
fun dashPathEffect(intervals: FloatArray, phase: Float = 0f): PathEffect =
    actualDashPathEffect(intervals, phase)

fun chainPathEffect(outer: PathEffect, inner: PathEffect): PathEffect =
    actualChainPathEffect(outer, inner)

fun stampedPathEffect(
    shape: Path,
    advance: Float,
    phase: Float,
    style: StampedPathEffectStyle
): PathEffect = actualStampedPathEffect(shape, advance, phase, style)
```
### cornerPathEffect
- 선분 사이의 날카로운 각도를 지정된 반경의 둥근 각도로 바꿉니다.

Params: 
  - radius - 그려진 도형의 각 각도에 적용할 둥근 모서리 반경입니다.

### dashPathEffect
- 지정된 간격과 오프셋을 가진 일련의 대시로 도형을 지정된 간격 배열로 그립니다. 간격에는 짝수 개의 항목이 포함되어야 합니다(>=2). 
- 짝수 인덱스는 "켜짐" 간격을 지정하고 홀수 인덱스는 "꺼짐" 간격을 나타냅니다. 위상 매개변수는 간격 배열에 대한 픽셀 오프셋입니다(모든 간격의 합을 곱한 값).
- 예를 들어, intervals[] = {10, 20}이고 phase = 25이면 다음과 같이 점선 경로가 설정됩니다: 5픽셀 오프 10픽셀 오프 20픽셀 오프 10픽셀 오프 20픽셀 오프
- 위상 매개변수는 간격 배열에 대한 오프셋입니다. 간격 배열은 대시의 길이를 제어합니다. 이는 획을 그은 도형(예: PaintingStyle.Stroke)에만 적용되며 채워진 도형(예: PaintingStyle.Fill)에는 무시됩니다.

Params:
intervals - 점선 세그먼트에 대한 "켜기" 및 "끄기" 거리의 배열입니다.
phase - 간격 배열에 대한 픽셀 오프셋

### chainPathEffect
- 경로에 내부 효과를 적용한 다음 내부 효과의 결과에 외부 효과를 적용하는 PathEffect를 만듭니다. (예: outer(inner(path)).

### stampedPathEffect
- 경로로 표시된 지정된 도형으로 그려진 경로에 스탬프를 찍어 대시합니다. 이 효과는 획 모양에만 적용되며 채워진 모양에서는 무시됩니다. 이 경로 효과에 사용된 획 너비도 무시됩니다.

Params:
shape - 스탬프할 경로
advance - 스탬프된 각 도형 사이의 간격
phase - 첫 번째 도형이 스탬프되기 전에 오프셋할 양
style - 스탬프가 찍힐 때 각 위치에서 도형을 변형하는 방법