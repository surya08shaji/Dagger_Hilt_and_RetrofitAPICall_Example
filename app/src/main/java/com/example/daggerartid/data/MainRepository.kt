package com.example.daggerartid.data

import com.example.daggerartid.data.model.DataModelItem
import com.example.daggerartid.data.model.ShowModelItem
import retrofit2.Response

interface MainRepository {
    suspend fun getList(): Response<ArrayList<DataModelItem>>

    suspend fun getDetails(id: Int): Response<ShowModelItem>
}