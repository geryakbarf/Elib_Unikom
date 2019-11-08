package xyz.geryakbarf.android.elibunikom.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import xyz.geryakbarf.android.elibunikom.models.DetailModels

interface GetDetailApi {
    @FormUrlEncoded
    @POST("getDetail.php")
    fun getDetail(
        @Field("id") id: String
    ): Call<DetailModels>
}