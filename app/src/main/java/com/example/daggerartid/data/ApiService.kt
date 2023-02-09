package com.example.daggerartid.data

import com.example.daggerartid.data.model.DataModelItem
import com.example.daggerartid.data.model.ShowModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("cache/article/list")
    suspend fun getList(
        @Query("postperpage") postperpage: Int?,
        @Query("arg") arg: String?,
        @Query("page") page: Int?
    ): Response<ArrayList<DataModelItem>>

    @GET("cache/article")
    suspend fun getDetails(
        @Query("article_id") article_id: Int?
    ): Response<ShowModelItem>
}