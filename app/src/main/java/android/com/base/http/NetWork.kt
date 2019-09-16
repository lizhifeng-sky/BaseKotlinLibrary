package android.com.base.http

import kotlinx.coroutines.*
import retrofit2.Response


fun getApiService(): ApiService {
    return RetrofitFactory.makeRetrofitService()
}

fun <T> Deferred<Response<T>>.getData(scope: CoroutineScope, success: T.() -> Unit, error: () -> Unit) {
    scope.launch(Dispatchers.IO) {
        try {
            this@getData.await().body()?.success()
        } catch (e: Exception) {
            error()
        }
    }
}

fun <T> Deferred<Response<T>>.getData(scope: CoroutineScope, success: T.() -> Unit) {
    scope.launch(Dispatchers.IO) {
        try {
            this@getData.await().body()?.success()
        } catch (e: Exception) {
        }
    }
}
