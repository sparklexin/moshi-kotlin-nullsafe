package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class CharJson(val char: Char, val charNullable: Char?)

class CharJsonTest {
    @Test
    fun testCharJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(CharJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, CharJson(
                    char = Char.MIN_VALUE,
                    charNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "char": "x"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(CharJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, CharJson(
                    char = 'x',
                    charNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "charNullable": "y"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(CharJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, CharJson(
                    char = Char.MIN_VALUE,
                    charNullable = 'y'
                )
            )
        }
        run {
            val json = """
                {  
                   "char": "x",
                   "charNullable": "y"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(CharJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, CharJson(
                    char = 'x',
                    charNullable = 'y'
                )
            )
        }
        run {
            val json = """
                {  
                   "char": null,
                   "charNullable": "y"
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(CharJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, CharJson(
                    char = Char.MIN_VALUE,
                    charNullable = 'y'
                )
            )
        }
    }
}