package xin.sparkle.moshi

import com.squareup.moshi.Moshi

val nullSafeMoshi = Moshi.Builder()
    .add(NullSafeStandardJsonAdapters.FACTORY)
    .add(NullSafeKotlinJsonAdapterFactory())
    .build()!!
