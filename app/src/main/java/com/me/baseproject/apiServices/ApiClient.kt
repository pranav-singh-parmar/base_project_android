package com.livevalue.customer.apiServices

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.squareup.moshi.JsonAdapter
import com.me.baseproject.R
import com.me.baseproject.utils.AppUrls
import com.me.baseproject.utils.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class ApiClient {

    private var api: ApiInterface
    private  val TAG = "ApiClient"

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val retrofit = Retrofit.Builder()
            .baseUrl(AppUrls.apiURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
            )
            .build()

        api = retrofit.create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("{path}")
        fun getApi(
            @Path("path", encoded = true) path: String,
            @HeaderMap headerMap: Map<String, String>?,
        ): Call<Map<String, Any>>

        @POST("{path}")
        fun postApi(
            @Path("path", encoded = true) path: String,
            @HeaderMap headerMap: Map<String, String>?,
            @Body login: Map<String, @JvmSuppressWildcards Any>?
        ): Call<Map<String, Any>>
    }

    private fun getHeaders(isAuth: Boolean): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["device"] = "android"
//        if (isAuth) {
//            val token = Singleton.sharedPrefs.getString(SharedPrefsKeys.authToken)
//            if (token.isNotEmpty()) {
//                headerMap["Authorization"] = token
//            }
//        }
        return headerMap
    }

    //region api functions
    fun <T: Any> getApi(
        context: Context,
        endPoint: String,
        isAuth: Boolean,
        dataClass: Class<T>,
        outputBlockForSuccess: (model: T?, json: Map<String, Any>?) -> Unit,
        outputBlockForInternetNotConnected: () -> Unit) {

        val headers = getHeaders(isAuth)

        api.getApi(endPoint, headers).enqueue(object : Callback<Map<String, Any>> {

            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                onResponse(context, dataClass, response, outputBlockForSuccess)
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                onFailure(context, call, outputBlockForInternetNotConnected)
            }

        })
    }

    fun <T: Any> postApi(
        context: Context,
        endPoint: String,
        isAuth: Boolean,
        parameters: Map<String, Any> = mapOf<String, Any>(),
        dataClass: Class<T>,
        outputBlockForSuccess: (model: T?, json: Map<String, Any>?) -> Unit,
        outputBlockForInternetNotConnected: () -> Unit) {

        val headers = getHeaders(isAuth)

        api.postApi(endPoint, headers, parameters).enqueue(object : Callback<Map<String, Any>> {

            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                onResponse(context, dataClass, response, outputBlockForSuccess)
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                onFailure(context, call, outputBlockForInternetNotConnected)
            }

        })
    }
    //endregion

    //region on retrofit callbacks
    private fun <T: Any> onResponse(context: Context,
                                           dataClass: Class<T>,
                                           response: Response<Map<String, Any>>,
                                           outputBlockForSuccess: (model: T?, json: Map<String, Any>?) -> Unit) {
        // val adapter: JsonAdapter<T> = moshi.adapter(dataClass) as JsonAdapter<T>
        val adapter: JsonAdapter<T> = Singleton.moshiBuilder.adapter(dataClass)
        Log.e(TAG, "in onResponse ${response.body().toString()}")
        if (response.code() == 200) {
            response.body()?.let { response ->
                val status = response["status"]
                val statusAsInt =
                    if (status is Double) status.toInt() else if (status is String) status.toInt() else (status as Int)
                if (statusAsInt != 200 && response["message"] is String) {
                    Singleton.generalFunctions.showToast(
                        context,
                        response["message"] as String,
                        Toast.LENGTH_LONG
                    )
                }
                val movie = adapter.fromJsonValue(response)
                outputBlockForSuccess(movie, response)
                return
            } ?: kotlin.run {
                Log.e(TAG, "model error in class $dataClass")
            }
        } else if (response.code() == 403) {
            //handle response if token has expired
            //Singleton.sharedPrefs.clearAllData()
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.apply {
                setTitle(context.resources.getString(R.string.alert))
                setMessage(context.resources.getString(R.string.seems_like_your_session_has_expired_please_login_again))
                setPositiveButton(context.resources.getString(R.string.ok_caps)) { _, _ ->
//                    val intent = Intent(context, SplashActivity::class.java)
//                    context.startActivity(intent)
//                    if (context is Activity) {
//                        context.finish()
//                    }
                }
                setCancelable(false)
            }
            alertDialog.show()
        } else {
            Log.e(TAG, "body ${response.body().toString()}")
            Log.e(TAG, "errorBody ${response.errorBody().toString()}")
            response.errorBody()?.let {
//                val errorResponse =
//                    BaseResponseJsonAdapter(Singleton.moshiBuilder).fromJson(it.source())
//                // outputBlockForSuccess(errorResponse, response.body())
//                Singleton.generalFunctions.showToast(context, errorResponse?.message?:"", Toast.LENGTH_LONG)
            }

        }
        outputBlockForSuccess(null, null)
    }

    private fun onFailure(context: Context,
                          call: Call<Map<String, Any>>,
                          outputBlockForInternetNotConnected: () -> Unit) {
        Log.e(TAG, "on Failure $call" )

        outputBlockForInternetNotConnected()
    }
    //endregion
}