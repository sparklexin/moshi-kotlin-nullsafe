package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class StringJson(val string: String, val stringNullable: String?)

class StringJsonTest {

    @Test
    fun testStringJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(StringJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, StringJson(
                    string = "",
                    stringNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "string": "hello"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(StringJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, StringJson(
                    string = "hello",
                    stringNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "stringNullable": "word"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(StringJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, StringJson(
                    string = "",
                    stringNullable = "word"
                )
            )
        }
        run {
            val json = """
                {
                   "string": "hello",
                   "stringNullable": "word"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(StringJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, StringJson(
                    string = "hello",
                    stringNullable = "word"
                )
            )
        }

        run {
            val json = """
                {
                   "string": null,
                   "stringNullable": "word"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(StringJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, StringJson(
                    string = "",
                    stringNullable = "word"
                )
            )
        }
    }
}