# Macdonald Clone Compose

## Note

### updateTransition
- 트랜지션이 설정되고 targetState가 제공한 타깃으로 업데이트됩니다. 
- targetState가 변경되면 Transition은 모든 자식 애니메이션을 새 targetState에 지정된 목표 값으로 실행합니다. 
- 자식 애니메이션은 Transition.animateFloat, androidx.compose.animation.animateColor, Transition.animateValue 등을 사용하여 동적으로 추가할 수 있습니다.
- label 은 안드로이드 스튜디오에서 다양한 트랜지션을 구분하는 데 사용됩니다.

```kotlin
val transition = updateTransition(targetState = if (quantity == 0) QuantityToggleState.Zero else QuantityToggleState.NonZero,
    label = ""
)

val backgroundColor by transition.animateColor(label = "") { state ->
    when (state) {
        QuantityToggleState.Zero -> MaterialTheme.colorScheme.background
        QuantityToggleState.NonZero -> MaterialTheme.colorScheme.secondary
    }
}

val contentColor by transition.animateColor(label = "") { state ->
    when (state) {
        QuantityToggleState.Zero -> MaterialTheme.colorScheme.secondary
        QuantityToggleState.NonZero -> LocalContentColor.current
    }
}

val iconSize by transition.animateDp(label = "") { state ->
    when (state) {
        QuantityToggleState.Zero -> 0.dp
        QuantityToggleState.NonZero -> 18.dp
    }
}
```

### Layout
- 레이아웃은 레이아웃의 주요 핵심 구성 요소입니다. 
- 0개 이상의 레이아웃 자식을 측정하고 위치를 지정하는 데 사용할 수 있습니다.
- 이 레이아웃의 측정, 레이아웃 및 내재적 측정 동작은 MeasurePolicy 인스턴스에 의해 정의됩니다. 

```kotlin
Box(modifier = Modifier
    .fillMaxWidth()
    .aspectRatio(1.00f))
{
    Layout(
        content = {
            NetworkImage(
                imageUrl = imageUrl,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                previewPlaceholder = R.drawable.category_happy_meals
            )
        }
    ) { measurables, constraints ->
        val imagePlaceable = measurables[0].measure(constraints)

        layout(
            width = constraints.maxWidth,
            height = constraints.minHeight
        ) {
            imagePlaceable.place(
                // 이미지의 x 값을 0.75 만큼 들여쓰기 하는 효과
                x = (constraints.maxWidth - imagePlaceable.width * 0.75).toInt(),
                y = 0
            )
        }
    }
}
```

### UI - ScrollableTabRow
- 가로 스크롤 가능한 탭 구현시 사용
- 가로 스크롤을 하지 않고 고정으로 사용하려면 `TabRow` 를 사용

```kotlin
ScrollableTabRow(
    selectedTabIndex = categories.indexOf(selectedCategory),
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.onSurface,
    edgePadding = 8.dp,
) {
    categories.forEach { category ->
        CategoryTab(
            category = category,
            selected = category == selectedCategory,
            onClick = { onCategorySelected(category) },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }
}
```

### viewModel()

**dependency**
```groovy
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
```

**사옹법**
```kotlin
@ExperimentalAnimationApi
@Composable
fun MenuScreen(
    onBackClick: () -> Unit,
    onMenuItemClick: (Long) -> Unit
) {
    val menuViewModel: MenuViewModel = viewModel()
}
```
- viewModel() 로 LocalViewModelStoreOwner 의 스토어를 사용하는 뷰모델 생성 및 반환

###

**dependency**
```groovy
implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
```

**사옹법**
```kotlin
val data by viewModel.data.observeAsState(Menu(emptyList(), emptyList()))
```
- MutableState 값을 할당하고 캐시되도록 remember 사용
- 라이프사이클오너를 이용해 LiveData의 옵저버를 스스로 등록, 옵저버에 값이 변경되면 state 값을 변경
- 내부적으로 DisposableEffect 를 사용해서 dispose 될때 옵저버 제거

### Scaffold 사용시
```kotlin
Scaffold(
    topBar = {
        ...
    }
) { paddingValues ->
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

    }
}
```
- Scaffold 를 사용할때는 .padding(paddingValues)

### 참고
- [McCompose](https://github.com/hitanshu-dhawan/McCompose/tree/main)