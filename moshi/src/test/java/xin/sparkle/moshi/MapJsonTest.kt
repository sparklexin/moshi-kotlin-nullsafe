package xin.sparkle.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert
import org.junit.Test

data class MapJson(val map: Map<String, String?>, val mapNullable: Map<String, String?>?)

class MapJsonTest {
    @Test
    fun testMapJson() {
        run {
            val json = "{}"
            val model = nullSafeMoshi.adapter(MapJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, MapJson(
                    map = mapOf(),
                    mapNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "map": {
                      "key1": "value1",
                      "key2": null
                   }
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(MapJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, MapJson(
                    map = mapOf(
                        "key1" to "value1",
                        "key2" to null
                    ),
                    mapNullable = null
                )
            )
        }
        run {
            val json = """
                {
                   "mapNullable": {
                      "key1": "value1",
                      "key2": null
                   }
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(MapJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, MapJson(
                    map = mapOf(),
                    mapNullable = mapOf(
                        "key1" to "value1",
                        "key2" to null
                    )
                )
            )
        }
        run {
            val json = """
                {
                 "map": {
                      "key1": "value1",
                      "key2": null
                   },
                   "mapNullable": {
                      "key1": "value12",
                      "key2": null
                   }
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(MapJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, MapJson(
                    map = mapOf(
                        "key1" to "value1",
                        "key2" to null
                    ),
                    mapNullable = mapOf(
                        "key1" to "value12",
                        "key2" to null
                    )
                )
            )
        }

        run {
            val json = """
                {
                   "map": null,
                   "mapNullable": {
                      "key1": "value12",
                      "key2": null
                   }
                }
            """.trimIndent()
            val model = nullSafeMoshi.adapter(MapJson::class.java).fromJson(json)!!
            Assert.assertEquals(
                model, MapJson(
                    map = mapOf(),
                    mapNullable = mapOf(
                        "key1" to "value12",
                        "key2" to null
                    )
                )
            )
        }
    }

    @Test
    fun squareupMoshiMapTest() {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        run {
            val json = """
                {
                   "key1": "value1",
                   "key2": null
                }
            """.trimIndent()
            val mapType =
                Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
            val model = moshi.adapter<Map<String, String>>(mapType).fromJson(json)!!

            val map: Map<String, String?> = mapOf(
                "key1" to "value1",
                "key2" to null
            )
            Assert.assertEquals(
                model, map
            )
        }
    }
}