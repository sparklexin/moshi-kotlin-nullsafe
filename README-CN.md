[TOC]

# Moshi-kotlin-nullsafe [ENGLISH](./README.md) 

## 1. moshi-kotlin

[moshi-kotlin](https://github.com/square/moshi/tree/master/kotlin)支持kotlin类型安全校验

json解析时，对于声明为非空类型的字段，可能会抛出异常

1. 字段缺失：`JsonDataException("Non-null value '%s' was null at %s")`
2. 值为null：`JsonDataException("Required value '%s' missing at %s")`

例子：

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

`moshi-kotlin-nullsafe`旨在解决上述问题，当声明的非空字段缺失或为空时，会自动填充默认值，保证解析正常。

目前支持的类型：

| 类型    | 默认值                 |
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

### 2.1 接入

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

### 2.2 使用

#### 常规使用

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

### 2.3 定制

`NullSafeKotlinJsonAdapterFactory`默认使用`BuildInDefaultValueProvider`来提供缺省值：

```kotlin
// 内置的DefaultValueProvider
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

使用者可以自定义`DefaultValueProvider`以支持更多的类型。

```kotlin
class NullSafeKotlinJsonAdapterFactory(
    private val providers: List<DefaultValueProvider> = listOf(),
    private val useBuildInProviders: Boolean = true
)
```

