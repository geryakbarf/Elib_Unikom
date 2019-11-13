package xyz.geryakbarf.android.elibunikom.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import xyz.geryakbarf.android.elibunikom.models.BaseModels

interface LoginApi {
    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("password") password: String,
        @Field("username") username: String
    ): Call<BaseModels>
}