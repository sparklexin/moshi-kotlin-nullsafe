[TOC]

# Moshi-kotlin-nullsafe [中文版](./README-CN.md) 

## 1. moshi-kotlin

`moshi-kotlin` support kotlin type safe check.

When parsing json, fields declared as non-empty types may throw JsonDataException

1. Missing property：`JsonDataException("Non-null value '%s' was null at %s")`
2. Unexpected null value：`JsonDataException("Required value '%s' missing at %s")`

For example: 

```kotlin
data class User(
  val name: String,
  val age: Int, 
  val address: String
)

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
val json = """
    {
     "name": "Tom",
     "age": 10
   }
""".trimIndent()
// com.squareup.moshi.JsonDataException: Required value 'address' missing at $
moshi.adapter(User::class.java).fromJson(json)
```

## 2. moshi-kotlin-nullsafe
`moshi-kotlin-nullsafe` aims to solve the above problem. 

When the declared non-null field is missing or null, the default value will be automatically filled.

| Type    | Default value          |
| ------- | ---------------------- |
| Int     | 0                      |
| Char    | 0.toChar()             |
| Byte    | 0.toByte()             |
| Float   | 0.toFloat()            |
| Double  | 0.toDouble()           |
| Long    | 0.toLong()             |
| String  | ""                     |
| Boolean | false                  |
| List    | Collections.EMPTY_LIST |
| Map     | Collections.EMPTY_MAP  |
| Set     | Collections.EMPTY_SET  |

### 2.1 How to?

**Step 1.** Add the JitPack repository to your build file

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the dependency

```groovy
dependencies {
    implementation 'com.github.sparklexin:moshi-kotlin-nullsafe:${latest_version}'
}
```

### 2.2 Usage

```kotlin
data class User(
  val name: String,
  val age: Int, 
  val address: String
)

val moshi = Moshi.Builder()
    .add(NullSafeStandardJsonAdapters.FACTORY)
    .add(NullSafeKotlinJsonAdapterFactory())
    .build()
val json = """
    {
     "name": "Tom",
     "age": 10
   }
""".trimIndent()
val user = moshi.adapter(User::class.java).fromJson(json)

// User(name=Tom, age=10, address=)
println(user)
```

#### retrofit2 converter-moshi

```kotlin
val nullSafeMoshi = Moshi.Builder()
    .add(NullSafeStandardJsonAdapters.FACTORY)
    .add(NullSafeKotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(API_URL)
    .addConverterFactory(MoshiConverterFactory.create(nullSafeMoshi))
    .build()
```

### 2.3 Customize

`NullSafeKotlinJsonAdapterFactory` uses `BuildInDefaultValueProvider` by default to provide default values:

```kotlin
internal class BuildInDefaultValueProvider : DefaultValueProvider {
    override fun provideDefaultValue(kType: KType): Any? {
        return when (kType.classifier) {
            Int::class -> 0
            Char::class -> 0.toChar()
            Byte::class -> 0.toByte()
            Float::class -> 0.toFloat()
            Double::class -> 0.toDouble()
            Long::class -> 0.toLong()
            String::class -> ""
            Boolean::class -> false
            List::class -> Collections.EMPTY_LIST
            Map::class -> Collections.EMPTY_MAP
            Set::class -> Collections.EMPTY_SET
            else -> null
        }
    }
}
```

Developers can use your own `DefaultValueProvider`

```kotlin
class NullSafeKotlinJsonAdapterFactory(
    private val providers: List<DefaultValueProvider> = listOf(),
    private val useBuildInProviders: Boolean = true
)
```

