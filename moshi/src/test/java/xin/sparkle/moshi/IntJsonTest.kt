package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class IntJson(val int: Int, val intNullable: Int?)

class IntJsonTest {

    @Test
    fun testIntJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(IntJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, IntJson(
                    int = 0,
                    intNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "int": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(IntJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, IntJson(
                    int = 8,
                    intNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "intNullable": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(IntJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, IntJson(
                    int = 0,
                    intNullable = 8
                )
            )
        }
        run {
            val json = """
                {
                   "int": 7,
                   "intNullable": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(IntJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, IntJson(
                    int = 7,
                    intNullable = 8
                )
            )
        }
        run {
            val json = """
                {
                   "int": null,
                   "intNullable": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(IntJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, IntJson(
                    int = 0,
                    intNullable = 8
                )
            )
        }
    }
}