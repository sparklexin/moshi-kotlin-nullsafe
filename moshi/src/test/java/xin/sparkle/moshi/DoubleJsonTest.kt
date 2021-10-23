package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class DoubleJson(val double: Double, val doubleNullable: Double?)
class DoubleJsonTest {
    @Test
    fun testDoubleJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(DoubleJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, DoubleJson(
                    double = 0.0,
                    doubleNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "double": 8.23
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(DoubleJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, DoubleJson(
                    double = 8.23,
                    doubleNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "doubleNullable": 8.375
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(DoubleJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, DoubleJson(
                    double = 0.0,
                    doubleNullable = 8.375
                )
            )
        }
        run {
            val json = """
                {
                   "double": 7.123,
                   "doubleNullable": 8.375
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(DoubleJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, DoubleJson(
                    double = 7.123,
                    doubleNullable = 8.375
                )
            )
        }
        run {
            val json = """
                {
                   "double": null,
                   "doubleNullable": 8.375
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(DoubleJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, DoubleJson(
                    double = 0.0,
                    doubleNullable = 8.375
                )
            )
        }
    }
}