package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class BooleanJson(val boolean: Boolean, val booleanNullable: Boolean?)

class BooleanJsonTest {
    @Test
    fun testBooleanJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(BooleanJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, BooleanJson(
                    boolean = false,
                    booleanNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "boolean": true
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(BooleanJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, BooleanJson(
                    boolean = true,
                    booleanNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "booleanNullable": true
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(BooleanJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, BooleanJson(
                    boolean = false,
                    booleanNullable = true
                )
            )
        }
        run {
            val json = """
                {
                   "boolean": true,
                   "booleanNullable": true
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(BooleanJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, BooleanJson(
                    boolean = true,
                    booleanNullable = true
                )
            )
        }

        run {
            val json = """
                {
                   "boolean": null,
                   "booleanNullable": true
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(BooleanJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, BooleanJson(
                    boolean = false,
                    booleanNullable = true
                )
            )
        }
    }
}