package xin.sparkle.moshi.demo

import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import xin.sparkle.moshi.NullSafeKotlinJsonAdapterFactory
import xin.sparkle.moshi.NullSafeStandardJsonAdapters
import java.io.IOException

object RetrofitSimpleServiceDemo {
    private const val API_URL = "https://api.github.com"

    // Github api response not contains "testBoolean" field, it will be init with false.
    data class Contributor(val login: String, val contributions: Int, val testBoolean: Boolean)
    interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        fun contributors(
            @Path("owner") owner: String?,
            @Path("repo") repo: String?
        ): Call<List<Contributor>>
    }

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val nullSafeMoshi = Moshi.Builder()
            .add(NullSafeStandardJsonAdapters.FACTORY)
            .add(NullSafeKotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(
                // Using moshi with NullSafeKotlinJsonAdapterFactory
                MoshiConverterFactory.create(nullSafeMoshi)
            )
            .build()


        val github = retrofit.create(GitHub::class.java)
        val call = github.contributors("square", "retrofit")
        val contributors = call.execute().body()!!
        for (contributor in contributors) {
            println(contributor)
        }
    }
}