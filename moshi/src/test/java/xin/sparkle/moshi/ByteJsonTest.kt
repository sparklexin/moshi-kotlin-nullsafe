package xin.sparkle.moshi

import org.junit.Assert
import org.junit.Test

data class ByteJson(val byte: Byte, val byteNullable: Byte?)

class ByteJsonTest {

    @Test
    fun testByteJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(ByteJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ByteJson(
                    byte = 0,
                    byteNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "byte": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ByteJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ByteJson(
                    byte = 8,
                    byteNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "byteNullable": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ByteJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ByteJson(
                    byte = 0,
                    byteNullable = 8
                )
            )
        }
        run {
            val json = """
                {
                   "byte": 7,
                   "byteNullable": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ByteJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ByteJson(
                    byte = 7,
                    byteNullable = 8
                )
            )
        }
        run {
            val json = """
                {
                   "byte": null,
                   "byteNullable": 8
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(ByteJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, ByteJson(
                    byte = 0,
                    byteNullable = 8
                )
            )
        }
    }
}