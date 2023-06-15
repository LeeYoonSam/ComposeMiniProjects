# JetMovie

## Jetpack Navigation
Jetpack Compose Navigation은 Android 앱 개발용 Jetpack Compose 도구 키트의 구성요소로, 

앱 내에서 화면 또는 대상 간 탐색을 처리하는 선언적이고 형식이 안전한 방법을 제공합니다. 

개발자가 탐색 경로를 정의하고 탐색 작업을 쉽게 처리할 수 있도록 하여 탐색 프로세스를 단순화합니다.

### Navigation Components
- Navigation Graph: 
  - 탐색 그래프는 목적지와 작업으로 구성된 앱의 탐색 흐름을 시각적으로 표현한 것입니다. 
  - 사용 가능한 화면과 화면 간의 연결을 정의합니다.
- Destinations: 
  - 목적지는 앱 내의 개별 화면 또는 컴포저블을 나타냅니다. 
  - 목적지는 경로 또는 고유 ID와 같은 목적지별 식별자를 사용하여 탐색 그래프에서 정의됩니다.
- Actions: 
  - 액션은 목적지 간의 가능한 전환 또는 탐색 경로를 정의합니다. 
  - 버튼 클릭과 같은 사용자 상호 작용이나 프로그래밍 이벤트에 의해 트리거될 수 있습니다.
- NavHost: 
  - NavHost는 내비게이션 그래프를 기반으로 현재 목적지를 표시하는 컨테이너 역할을 하는 컴포저블 함수입니다. 
  - 내비게이션 상태를 관리하고 그에 따라 UI를 업데이트합니다.
- Safe Args: 
  - Safe Args는 대상 간에 인수를 유형 안전하게 전달할 수 있는 기능입니다. 
  - 탐색 그래프에 정의된 인수를 나타내는 클래스를 생성하여 잘못된 인수 유형 또는 누락된 인수와 관련된 런타임 오류의 위험을 제거합니다.
- Navigation Composables: 
  - Jetpack Compose는 내비게이션 처리 및 UI와의 통합을 용이하게 하기 위해 내비게이션 관련 컴포저블 세트(예: NavController, NavHost, rememberNavController, 내비게이션 관련 수정자)를 제공합니다.

개발자는 Jetpack Compose Navigation을 사용하여 내비게이션 흐름 구현을 간소화하고, 복잡한 내비게이션 시나리오를 처리하며, 유형 안전 내비게이션 그래프와 Safe Args가 제공하는 컴파일 타임 안전성의 이점을 누릴 수 있습니다.

한 가지 주목할 점은 젯팩 컴포즈 내비게이션은 젯팩 컴포즈의 일부이며 다른 젯팩 컴포즈 컴포넌트 및 라이브러리와 조화롭게 작동하도록 설계되어 최신 안드로이드 앱을 빌드할 때 전반적인 개발 경험을 향상시킨다는 점입니다.

### NavHost 
```kotlin
@Composable
public fun NavHost(
  navController: NavHostController,
  startDestination: String,
  modifier: Modifier = Modifier,
  route: String? = null,
  builder: NavGraphBuilder.() -> Unit
) {
  NavHost(
    navController,
    remember(route, startDestination, builder) {
      navController.createGraph(startDestination, route, builder)
    },
    modifier
  )
}
```
- NavHost 코드

```kotlin
NavHost(
    navController = navController,
    startDestination = MovieScreens.HomeScreen.name
) {
    composable(MovieScreens.HomeScreen.name) {
        // here we pass where this should lead us to
         HomeScreen(navController = navController)
    }

    composable(MovieScreens.DetailsScreen.name) {
        DetailScreen(navController = navController)
    }
}
```
- builder: NavGraphBuilder 에서 실제 네비게이션 그래프를 구성
- - `MovieScreens.HomeScreen`, `MovieScreens.DetailsScreen` 2개 경로에 대한 화면 연결

## 참고
- [Navigation](https://developer.android.com/jetpack/compose/navigation?hl=ko)