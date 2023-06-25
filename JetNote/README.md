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