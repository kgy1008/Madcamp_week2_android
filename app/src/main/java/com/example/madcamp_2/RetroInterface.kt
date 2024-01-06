package com.example.madcamp_2

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetroInterface{
    @POST("/register")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun register(
        @Body jsonparams: RegisterModel
    ) : Call<RegisterResult>

    @POST("/login")
    fun login(
        @Body jsonparams: LoginModel
    ) : Call<LoginResult>

    @POST("/login/idcert")
    fun idCert(
        @Body jsonparams: IdCertification
    ) : Call<IdCertificationResult>

    @GET("/boardclass")
    fun getBoardClass(): Call<ArrayList<BoardClassModel>>

    @POST("/board")
    fun getBoard(
        @Body jsonparams: BoardName
    ) : Call<ArrayList<BoardModel>>

    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = "http://192.249.29.52:4000" //

        fun create(): RetroInterface {
            val gson : Gson =   GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetroInterface::class.java)
        }
    }
}