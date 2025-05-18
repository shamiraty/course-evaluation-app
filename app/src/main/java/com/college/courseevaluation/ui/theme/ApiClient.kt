package com.college.courseevaluation.ui.theme
import com.college.courseevaluation.ui.theme.data.Course
import com.college.courseevaluation.ui.theme.data.CourseEvaluationRequest
import com.college.courseevaluation.ui.theme.data.DashboardData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.IOException
const val BASE_URL = "http://swahiliict.scienceontheweb.net/api/"
const val API_KEY = "LwhY1KKdmvQE8ca3OdmjKVGg8FlvRXfPFV0aeyrA"

interface ApiService {
    //POST METHOD  HERE
    @POST("course-evaluations")
    fun createCourseEvaluation(@Body request: CourseEvaluationRequest): Call<Map<String, String>>

    // GET METHOD TO FETCH COURSES
    @GET("courses")
    fun getCourses(): Call<List<Course>>

    // GET METHOD TO FETCH DASHBOARD DATA
    @GET("dashboard-data")
    fun getDashboardData(): Call<DashboardData>
}

object ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiKeyInterceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("X-API-KEY", API_KEY)
                .build()
            return chain.proceed(newRequest)
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(apiKeyInterceptor) // Add the API key interceptor
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}