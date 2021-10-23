package xin.sparkle.moshi

import java.util.*
import kotlin.reflect.KType

interface DefaultValueProvider {
    fun provideDefaultValue(kType: KType): Any?
}

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
