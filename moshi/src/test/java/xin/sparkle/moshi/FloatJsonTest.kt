package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class FloatJson(val float: Float, val floatNullable: Float?)

class FloatJsonTest {
    @Test
    fun testFloatJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(FloatJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, FloatJson(
                    float = 0f,
                    floatNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "float": 8.2
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(FloatJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, FloatJson(
                    float = 8.2f,
                    floatNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "floatNullable": 8.4
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(FloatJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, FloatJson(
                    float = 0f,
                    floatNullable = 8.4f
                )
            )
        }
        run {
            val json = """
                {
                   "float": 7.2,
                   "floatNullable": 8.7
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(FloatJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, FloatJson(
                    float = 7.2f,
                    floatNullable = 8.7f
                )
            )
        }
        run {
            val json = """
                {
                   "float": null,
                   "floatNullable": 8.7
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(FloatJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, FloatJson(
                    float = 0f,
                    floatNullable = 8.7f
                )
            )
        }
    }
}