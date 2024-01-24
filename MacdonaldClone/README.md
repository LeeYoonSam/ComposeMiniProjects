# Macdonald Clone Compose

## Compose Navigation

### Dependency 추가
```kotlin
dependencies {
    val nav_version = "2.7.6"

    implementation("androidx.navigation:navigation-compose:$nav_version")
}
```

### NavController 
- NavController는 Navigation 구성요소의 중심 API로, 스테이트풀(Stateful)이며 앱의 화면과 각 화면 상태를 구성하는 컴포저블의 백 스택을 추적합니다.
- 컴포저블에서 rememberNavController() 메서드를 사용하여 NavController를 만들 수 있습니다.
```kotlin
val navController = rememberNavController()
```
- 컴포저블 계층 구조에서 NavController를 만드는 위치는 이를 참조해야 하는 모든 컴포저블이 액세스할 수 있는 곳이어야 합니다. 이는 상태 호이스팅의 원칙을 준수하며, 이렇게 하면 NavController와 상태를 사용할 수 있습니다. 
- 상태는 currentBackStackEntryAsState()를 통해 제공되며 화면 외부에서 컴포저블을 업데이트하기 위한 정보 소스로 사용됩니다.

### NavHost 만들기
- 각 NavController를 단일 NavHost 컴포저블과 연결해야 합니다. 
- NavHost는 Composable 대상을 지정하는 탐색 그래프와 NavController를 연결합니다. 
- Composable 대상은 컴포저블 간에 이동할 수 있어야 합니다. 
- 여러 컴포저블 간에 이동하는 과정에서 NavHost의 콘텐츠가 자동으로 재구성됩니다. 
- 탐색 그래프의 Composable 대상은 각각 경로와 연결됩니다.

**`주요 용어`: 경로는 컴포저블의 경로를 정의하는 String입니다. 특정 대상으로 연결되는 암시적 딥 링크라고 생각하면 됩니다. 각 대상에는 고유 경로가 있어야 합니다.**

```kotlin
NavHost(navController = navController, startDestination = "profile") {
    composable("profile") { Profile(/*...*/) }
    composable("friendslist") { FriendsList(/*...*/) }
    /*...*/
}
```
- NavHost를 만들려면 이전에 rememberNavController()를 통해 만든 NavController뿐만 아니라 그래프의 시작 대상 경로도 필요합니다.
- NavHost를 만드는 데는 탐색 그래프를 생성하는 Navigation Kotlin DSL의 람다 구문이 사용됩니다.
- `composable()` 메서드를 사용하여 탐색 구조에 추가할 수 있습니다. 이 메서드를 사용하려면 `경로뿐만 아니라 대상에 연결해야 할 컴포저블도 제공`해야 합니다.

**`참고`: Navigation 구성요소를 사용할 때는 Navigation 원리를 따르고 고정된 시작 대상을 사용해야 합니다. startDestination 경로에 Composable 값을 사용해서는 안 됩니다.**

### 컴포저블로 이동
- 탐색 그래프에서 Composable 대상으로 이동하려면 navigate 메서드를 사용해야 합니다. 
- navigate는 대상의 경로를 나타내는 단일 String 매개변수를 사용합니다. 
- 탐색 그래프 내의 컴포저블에서 이동하려면 navigate를 호출하세요.

```kotlin
navController.navigate("friendslist")
```

기본적으로 navigate는 새 대상을 백 스택에 추가합니다. 추가 탐색 옵션을 navigate() 호출에 연결하여 navigate 동작을 수정할 수 있습니다.
```kotlin
// Pop everything up to the "home" destination off the back stack before
// navigating to the "friendslist" destination
navController.navigate("friendslist") {
    popUpTo("home")
}

// Pop everything up to and including the "home" destination off
// the back stack before navigating to the "friendslist" destination
navController.navigate("friendslist") {
    popUpTo("home") { inclusive = true }
}

// Navigate to the "search” destination only if we’re not already on
// the "search" destination, avoiding multiple copies on the top of the
// back stack
navController.navigate("search") {
    launchSingleTop = true
}
```

**다른 Composable 함수로 트리거된 호출 탐색**
- NavController의 navigate 함수는 NavController의 내부 상태를 수정합니다. 
- 단일 정보 소스 원칙을 최대한 준수하려면 NavController 인스턴스를 끌어올리는 Composable 함수나 상태 홀더 및 NavController를 매개변수로 사용하는 Composable 함수만 탐색 호출을 해야 합니다. 
- UI 계층 구조 아래쪽에 있는 다른 Composable 함수에서 트리거된 탐색 이벤트는 함수를 사용하여 이러한 이벤트를 호출자에게 적절히 노출해야 합니다.

다음 예에서는 MyAppNavHost Composable 함수를 NavController 인스턴스의 단일 정보 소스로 보여줍니다. 
- ProfileScreen은 사용자가 버튼을 탭할 때 호출되는 함수로 이벤트를 노출합니다. 
- 앱의 여러 화면으로 이동하는 MyAppNavHost는 ProfileScreen을 호출할 때 올바른 대상으로 탐색을 호출합니다.

```kotlin
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "profile"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("profile") {
            ProfileScreen(
                onNavigateToFriends = { navController.navigate("friendsList") },
                /*...*/
            )
        }
        composable("friendslist") { FriendsListScreen(/*...*/) }
    }
}

@Composable
fun ProfileScreen(
    onNavigateToFriends: () -> Unit,
    /*...*/
) {
    /*...*/
    Button(onClick = onNavigateToFriends) {
        Text(text = "See friends list")
    }
}
```
- navigate()는 컴포저블 자체의 일부가 아닌 콜백의 일부로만 호출하여 모든 재구성에서 navigate()를 호출하지 않도록 해야 합니다.

### 인수를 통해 이동

Navigation Compose는 구성 가능한 대상 간의 인수 전달도 지원합니다. 이렇게 하려면 기본 탐색 라이브러리를 사용할 때 딥 링크에 인수를 추가하는 방법과 유사한 방법으로 인수 자리표시자를 경로에 추가해야 합니다.
```kotlin
NavHost(startDestination = "profile/{userId}") {
    ...
    composable("profile/{userId}") {...}
}
```

기본적으로 모든 인수는 문자열로 파싱됩니다. composable()의 arguments 매개변수는 NamedNavArgument 목록을 허용합니다. navArgument 메서드를 사용하여 신속하게 NamedNavArgument를 만든 다음 정확한 type을 지정할 수 있습니다.
```kotlin
NavHost(startDestination = "profile/{userId}") {
    ...
    composable(
        "profile/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {...}
}
```

composable() 함수의 람다에서 사용할 수 있는 NavBackStackEntry에서 인수를 추출해야 합니다.
```kotlin
composable("profile/{userId}") { backStackEntry ->
    Profile(navController, backStackEntry.arguments?.getString("userId"))
}
```

인수를 대상에 전달하려면 navigate 호출 시 경로에 추가해야 합니다.
```kotlin
navController.navigate("profile/user1234")
```

**탐색 시 복잡한 데이터 검색**
탐색할 때는 복잡한 데이터 객체를 전달하지 않고 탐색 작업을 실행할 때 고유 식별자 또는 다른 형식의 ID와 같은 필요한 최소 정보를 인수로 전달하는 것이 좋습니다.
```kotlin
// Pass only the user ID when navigating to a new destination as argument
navController.navigate("profile/user1234")
```

복잡한 객체는 단일 정보 소스(예: 데이터 영역)에 데이터로 저장해야 합니다. 탐색 후 대상에 도달하면 전달된 ID를 사용하여 단일 정보 소스에서 필요한 정보를 로드할 수 있습니다.
데이터 영역 액세스를 담당하는 ViewModel의 인수를 검색하려면 ViewModel’s `SavedStateHandle`을 사용하면 됩니다.
```kotlin
class UserViewModel(
    savedStateHandle: SavedStateHandle,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    private val userId: String = checkNotNull(savedStateHandle["userId"])

    // Fetch the relevant user information from the data layer,
    // ie. userInfoRepository, based on the passed userId argument
    private val userInfo: Flow<UserInfo> = userInfoRepository.getUserInfo(userId)

// …

}
```
- 이 접근 방식은 구성 변경 중의 데이터 손실 및 해당 객체가 업데이트되거나 변형될 때 발생하는 불일치를 방지하는 데 도움이 됩니다. 
- 복잡한 데이터를 인수로 전달하지 않아야 하는 이유와 지원되는 인수 유형 목록에 대한 더 자세한 설명은 대상 간 데이터 전달을 참고하세요.

**선택적 인수 추가**
Navigation Compose는 선택적 탐색 인수도 지원합니다. 선택적 인수는 다음과 같은 두 가지 측면에서 필수 인수와 다릅니다.
- 쿼리 매개변수 문법("?argName={argName}")을 사용하여 포함해야 합니다.
- defaultValue가 설정되어 있거나 nullability = true(암시적으로 기본값을 null로 설정함)가 있어야 합니다.

즉, 모든 선택적 인수는 composable() 함수에 목록 형태로 명확하게 추가해야 합니다.
```kotlin
composable(
    "profile?userId={userId}",
    arguments = listOf(navArgument("userId") { defaultValue = "user1234" })
) { backStackEntry ->
    Profile(navController, backStackEntry.arguments?.getString("userId"))
}
```
- 이제 대상에 전달되는 인수가 없더라도 'user1234' defaultValue가 대신 사용됩니다. 
- 경로를 통해 인수를 처리하는 구조에서는 컴포저블이 Navigation과 완전히 독립적으로 유지되며 테스트 가능성이 훨씬 더 높습니다.

### 딥 링크
Navigation Compose는 composable() 함수의 일부로 정의할 수 있는 암시적 딥 링크를 지원합니다. deepLinks 매개변수는 navDeepLink 메서드를 사용하여 빠르게 만들 수 있는 NavDeepLink 목록을 허용합니다.

```kotlin
val uri = "https://www.example.com"

composable(
    "profile?id={id}",
    deepLinks = listOf(navDeepLink { uriPattern = "$uri/{id}" })
) { backStackEntry ->
    Profile(navController, backStackEntry.arguments?.getString("id"))
}
```
- 이러한 딥 링크를 사용하면 특정 URL, 작업 또는 MIME 유형을 컴포저블과 연결할 수 있습니다. 기본적으로 이러한 딥 링크는 외부 앱에 노출되지 않습니다.

딥 링크를 외부에서 사용할 수 있도록 하려면 적절한 <intent-filter> 요소를 앱의 manifest.xml 파일에 추가해야 합니다. 위의 딥 링크를 사용 설정하려면 매니페스트의 <activity> 요소 내부에 다음을 추가해야 합니다.
```kotlin
<activity …>
  <intent-filter>
    ...
    <data android:scheme="https" android:host="www.example.com" />
  </intent-filter>
</activity>
```
- 다른 앱에서 딥 링크를 트리거하면 Navigation이 딥 링크로 컴포저블과 자동 연결됩니다.

동일한 딥 링크를 사용하면 컴포저블에서 적절한 딥 링크를 통해 PendingIntent를 빌드할 수도 있습니다.
```kotlin
val id = "exampleId"
val context = LocalContext.current
val deepLinkIntent = Intent(
    Intent.ACTION_VIEW,
    "https://www.example.com/$id".toUri(),
    context,
    MyActivity::class.java
)

val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
    addNextIntentWithParentStack(deepLinkIntent)
    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
}
```
- 그런 다음 deepLinkPendingIntent를 다른 PendingIntent처럼 사용하여 딥 링크 대상에서 앱을 열면 됩니다.

### Reference
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation?hl=ko#create-navhost)

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