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

### 참고
- [McCompose](https://github.com/hitanshu-dhawan/McCompose/tree/main)