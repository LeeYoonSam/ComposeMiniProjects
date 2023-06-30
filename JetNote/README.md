# JetNote App

## [Room](https://developer.android.com/jetpack/androidx/releases/room?hl=en#groovy)
- 추상 계층을 구현해서 쉽게 테이터베이스를 사용

## DI
**Understanding DI and Its Advantages**
- [Kotlin PlayGround](https://pl.kotl.in/JqiG12TTO)

## Dependency Injection with Hilt
**Manual DI vs Hilt**

### Manual DI
- 수동 DI도 잘 작동하지만 앱이 확장됨에 따라 종속성을 관리하기가 매우 어려울 수 있습니다.

### Hilt 장점
1. 코드 재사용성
2. 리팩토링의 용이성
3. 테스트 용이성

## Version Catalog 를 통한 버전 관리
gradle 7.0으로 업그레이드 되면서 추가된 version catalog를 통해 더욱 간편하게 버전 관리를 할 수 있게 되었습니다.

- libs.versions.toml 파일에서 모든 버전을 관리 
  - plugin, dependencies 모두 관리
```toml
[versions]

[libraries]

[plugins]
```
- `[versions]` 종속성 및 플러그인의 버전을 저장하는 변수를 정의합니다. 이러한 변수는 후속 블록(버전 및 플러그인 블록)에서 사용됩니다.
- `[libraries]` 종속성을 정의합니다.
- `[plugins]` 플러그인을 정의합니다.

## Dagger 플러그인 및 kapt 추가
```toml
# libs.versions.toml

[versions]
...
hilt="2.46.1"

[libraries]
...
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }

[plugins]
...
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
```

```kotlin
// project/build.gradle

plugins {
  ...
  alias(libs.plugins.hilt) apply false
  id("kotlin-kapt")
}
```

```kotlin
// app/build.gradle

plugins {
  ...
  alias(libs.plugins.hilt)
  alias(libs.plugins.kotlinKsp)
}

dependencies { 
    ...
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}

// Allow references to generated code
kapt {
  correctErrorTypes = true
}
```

### 참고
- [kapt에서 KSP로 이전](https://developer.android.com/studio/build/migrate-to-ksp?hl=ko#groovy)
- [Hilt를 사용한 종속 항목 삽입](https://developer.android.com/training/dependency-injection/hilt-android?hl=ko)


## Coroutine

### conflate
통합 채널을 통해 흐름 배출을 통합하고 별도의 코루틴에서 수집기를 실행합니다. 

그 결과 수집기가 느려져 이미터가 일시 중단되는 일은 없지만 수집기는 항상 가장 최근의 값을 가져옵니다.

**함수 정의**
```kotlin
public fun <T> Flow<T>.conflate(): Flow<T> = buffer(CONFLATED)
```
- 함수 정의를 보아하니 conflate 는 flow 에서 사용하는 버퍼전략인것 같습니다.

```kotlin
/**
 * Requests a channel with an unlimited capacity buffer in the `Channel(...)` factory function.
 */
public const val UNLIMITED: Int = Int.MAX_VALUE

/**
 * Requests a rendezvous channel in the `Channel(...)` factory function &mdash; a channel that does not have a buffer.
 */
public const val RENDEZVOUS: Int = 0

/**
 * Requests a conflated channel in the `Channel(...)` factory function. This is a shortcut to creating
 * a channel with [`onBufferOverflow = DROP_OLDEST`][BufferOverflow.DROP_OLDEST].
 */
public const val CONFLATED: Int = -1

/**
 * Requests a buffered channel with the default buffer capacity in the `Channel(...)` factory function.
 * The default capacity for a channel that [suspends][BufferOverflow.SUSPEND] on overflow
 * is 64 and can be overridden by setting [DEFAULT_BUFFER_PROPERTY_NAME] on JVM.
 * For non-suspending channels, a buffer of capacity 1 is used.
 */
public const val BUFFERED: Int = -2

// only for internal use, cannot be used with Channel(...)
internal const val OPTIONAL_CHANNEL = -3
```
- 사용하는 버퍼의 종류는 위와 같습니다.
- `CONFLATED` 는 오버플로우가 생기면 오래된것들은 제거하는 전략이라 항상 최근 값을 가져오게 되는것 같습니다.

### 참고
- [Flow - conflate](com/ys/jetnote/repository/NoteRepository.kt:31)


## Dagger Hilt

### HiltViewModel
구조체 주입을 위한 `androidx.lifecycle.ViewModel`을 식별합니다.
`HiltViewModel`로 어노테이션된 ViewModel은 `dagger.hilt.android.lifecycle.HiltViewModelFactory`에서 생성할 수 있으며, 
기본적으로 `dagger.hilt.android.AndroidEntryPoint`로 어노테이션된 `Activity` 또는 `Fragment`에서 검색할 수 있습니다. 

`javax.inject.Inject`로 어노테이션된 생성자가 포함된 `HiltViewModel`은 `Dagger의 Hilt`에 의해 주입된 생성자 매개변수에 정의된 종속성을 갖게 됩니다.

```kotlin
@HiltViewModel
public class DonutViewModel extends ViewModel {
    @Inject
    public DonutViewModel(SavedStateHandle handle, RecipeRepository repository) {
        // ...
    }
}

@AndroidEntryPoint
public class CookingActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        DonutViewModel vm = new ViewModelProvider(this).get(DonutViewModel.class);
    }
}
```

뷰모델의 생성자 중 정확히 하나의 생성자에는 Inject로 주석을 달아야 합니다.
dagger.hilt.android.components.ViewModelComponent에서 사용 가능한 종속성만 뷰모델에 주입할 수 있습니다.

## Room

### Room - Entity 에서 데이터 타입을 인식하지 못해서 컴파일 에러 발생

@TypeConverter 를 만들어서 선언
```kotlin
class DateConverter {
    @TypeConverter
    fun timeStampFromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun dateFromTimestamp(timestamp: Long): Date? {
        return Date(timestamp)
    }
}
```


```kotlin
@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}
```