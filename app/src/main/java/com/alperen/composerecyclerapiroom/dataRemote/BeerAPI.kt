package com.alperen.composerecyclerapiroom.dataRemote

import retrofit2.http.GET
import retrofit2.http.Query

interface BeerAPI {
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") pageCount:Int
    ): List<BeerDto>
    companion object {
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }
}