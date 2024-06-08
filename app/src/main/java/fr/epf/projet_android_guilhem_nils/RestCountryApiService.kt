package fr.epf.projet_android_guilhem_nils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RestCountriesApiService {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY

    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(45,TimeUnit.SECONDS)
        .readTimeout(45,TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()

    val api: RestCountryApi by lazy {
        retrofit.create(RestCountryApi::class.java)
    }
}