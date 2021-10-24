package xin.sparkle.moshi.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import xin.sparkle.moshi.NullSafeKotlinJsonAdapterFactory
import xin.sparkle.moshi.NullSafeStandardJsonAdapters

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    data class User(
        val name: String,
        val age: Int,
        val address: String,
        val company: String
    )

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val moshi = Moshi.Builder()
                .add(NullSafeStandardJsonAdapters.FACTORY)
                .add(NullSafeKotlinJsonAdapterFactory())
                .build()
            val json = """
             {
                "name": "Tom",
                "age": 10,
                "company": null
             }
            """.trimIndent()
            val user = moshi.adapter(User::class.java).fromJson(json)
            println(user)
        }
    }
}