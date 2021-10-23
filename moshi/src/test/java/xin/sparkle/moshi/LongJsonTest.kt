package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class LongJson(val long: Long, val longNullable: Long?)
class LongJsonTest {

    @Test
    fun testLongJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(LongJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, LongJson(
                    long = 0,
                    longNullable = null
                )
            )
        }
        run {
            // Integer.MAX_VALUE = 2147483647
            val json = """
                {
                   "long": 2147483648
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(LongJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, LongJson(
                    long = 2147483648,
                    longNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "longNullable": 2147483648
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(LongJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, LongJson(
                    long = 0,
                    longNullable = 2147483648
                )
            )
        }
        run {
            val json = """
                {
                   "long": 2147483648,
                   "longNullable": 2147483648
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(LongJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, LongJson(
                    long = 2147483648,
                    longNullable = 2147483648
                )
            )
        }
        run {
            val json = """
                {
                   "long": null,
                   "longNullable": 2147483648
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(LongJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, LongJson(
                    long = 0L,
                    longNullable = 2147483648
                )
            )
        }
    }
}