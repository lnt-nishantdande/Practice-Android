package api

import com.example.practice.BuildConfig
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


/**
 * UserApi is service interface which deals with user list data
 */
interface UserApi {
    /**
     * Function returns list of user list items asynchronously using 'Deferred'
     */
    @GET("users")
    fun fetchUsersList() : Deferred<List<UsersListItem?>?>
}

/**
 * Singleton network module class provide retrofit instance and services
 */
object NetworkModule {

    /**
     * Function will return UserApi service using retrofit
     * @param : retrofit instance
     * @return : User Api service interface
     */
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    /**
     * Function will provide retrofit instance
     * @param baseUrl : provide base url in string
     * @return : retrofit instance
     */
    fun provideRetrofit(baseUrl : String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(OkHttpClient().newBuilder().apply {
                    // add logging interceptor last to view others interceptors
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor().also {
                            it.level = HttpLoggingInterceptor.Level.BODY
                        }
                        this.addInterceptor(logging)
                    }
                }.build())
            .build()
    }
}