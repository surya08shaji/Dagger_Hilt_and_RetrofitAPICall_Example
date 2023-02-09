package com.example.daggerartid.data

import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiService: ApiService) : MainRepository {
    override suspend fun getList() = apiService.getList(10, "latest", 1)

    override suspend fun getDetails(id: Int) = apiService.getDetails(id)
}
