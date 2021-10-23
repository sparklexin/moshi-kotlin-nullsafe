package xin.sparkle.moshi

import org.junit.Assert.assertEquals
import org.junit.Test

data class UserList(
    val code: Int,
    val data: List<User>,
    var hasNext: Boolean
)

data class User(
    val name: String,
    val age: Int,
    val company: String?,
    val members: Map<String, User> = mapOf()
)

class MixedTest {
    @Test
    fun test() {
        val json = """
            {
               "code": 200,
               "hasNext": false,
               "data": [
                  {},
                  {
                     "name": "Tom",
                     "age": 20
                  },
                  {
                     "name": null,
                     "age": 20
                  },
                  {
                     "name": "Tom",
                     "age": 20,
                     "company": "Abb",
                     "members": {
                         "son": {
                            "name": "Cat",
                            "age": 2,
                            "company": null
                         }
                     }
                  }
               ]
            }
        """.trimIndent()
        val model = nullSafeMoshi.adapter(UserList::class.java).fromJson(json)
        val expect = UserList(
            code = 200,
            hasNext = false,
            data = listOf(
                User(
                    name = "",
                    age = 0,
                    company = null
                ),
                User(
                    name = "Tom",
                    age = 20,
                    company = null
                ),
                User(
                    name = "",
                    age = 20,
                    company = null
                ),
                User(
                    name = "Tom",
                    age = 20,
                    company = "Abb",
                    members = mapOf(
                        "son" to User(
                            name = "Cat",
                            age = 2,
                            company = null
                        )
                    )
                )
            )
        )
        assertEquals(expect, model)
    }
}