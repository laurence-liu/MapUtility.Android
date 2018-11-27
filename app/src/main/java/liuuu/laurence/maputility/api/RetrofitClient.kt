package liuuu.laurence.maputility.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitClient {
    private const val GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/"

    fun googleMethods(): GoogleMethods {
        val retrofit = Retrofit.Builder()
                .baseUrl(RetrofitClient.GOOGLE_BASE_URL)
                .client(OkHttpClient().newBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(GoogleMethods::class.java)
    }
}