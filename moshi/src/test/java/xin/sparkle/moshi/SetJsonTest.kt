package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class SetJson(val set: Set<Int>, val setNullable: Set<Int>?)
class SetJsonTest {
    @Test
    fun testSetJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(SetJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, SetJson(
                    set = setOf(),
                    setNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "set": [1,2,3,4,5,5,5,6]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(SetJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, SetJson(
                    set = setOf(1, 2, 3, 4, 5, 6),
                    setNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "setNullable": [1,2,3,4,5,5,6,6]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(SetJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, SetJson(
                    set = setOf(),
                    setNullable = setOf(1, 2, 3, 4, 5, 6)
                )
            )
        }
        run {
            val json = """
                {
                   "set": [1,2,3,4,5,5,5,6],
                   "setNullable": [1,2,3,2]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(SetJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, SetJson(
                    set = setOf(1, 2, 3, 4, 5, 6),
                    setNullable = setOf(1, 2, 3)
                )
            )
        }

        run {
            val json = """
                {
                   "set": null,
                   "setNullable": [1,2,3,2]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(SetJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, SetJson(
                    set = setOf(),
                    setNullable = setOf(1, 2, 3)
                )
            )
        }
    }
}