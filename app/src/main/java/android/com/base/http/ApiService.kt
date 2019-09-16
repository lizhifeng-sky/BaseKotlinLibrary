package android.com.base.http

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    fun getPostsAsync(): Deferred<Response<List<String>>>
}