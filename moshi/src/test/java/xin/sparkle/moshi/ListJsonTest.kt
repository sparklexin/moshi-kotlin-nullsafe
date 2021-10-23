package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class ListJson(val list: List<String>, val listNullable: List<String>?)

class ListJsonTest {
    @Test
    fun testListJson() {
        run {
            val json = """
                {}
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ListJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ListJson(
                    list = listOf(),
                    listNullable = null
                )
            )
        }
        run {
            val json = """
                {
                  "list": ["hello"]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ListJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ListJson(
                    list = listOf("hello"),
                    listNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "listNullable": ["hello"]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ListJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ListJson(
                    list = listOf(),
                    listNullable = listOf("hello")
                )
            )
        }
        run {
            val json = """
                {
                   "list": ["hello", "world"],
                   "listNullable": ["hello"]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ListJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ListJson(
                    list = listOf("hello", "world"),
                    listNullable = listOf("hello")
                )
            )
        }

        run {
            val json = """
                {
                   "list": null,
                   "listNullable": ["hello"]
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ListJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ListJson(
                    list = listOf(),
                    listNullable = listOf("hello")
                )
            )
        }
    }
}