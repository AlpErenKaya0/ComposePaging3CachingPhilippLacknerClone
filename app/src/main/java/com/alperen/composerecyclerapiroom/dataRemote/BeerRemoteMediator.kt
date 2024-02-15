package com.alperen.composerecyclerapiroom.dataRemote

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alperen.composerecyclerapiroom.dataRemote.mappers.toBeerEntity
import com.alperen.composerecyclerapiroom.local.BeerDatabase
import com.alperen.composerecyclerapiroom.local.BeerEntity
import kotlinx.coroutines.delay
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDb : BeerDatabase,
    private val beerApi: BeerAPI
):RemoteMediator<Int, BeerEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
val loadKey = when(loadType) {
        LoadType.REFRESH -> 1
        LoadType.PREPEND -> return MediatorResult.Success(
            endOfPaginationReached = true
        )
        LoadType.APPEND -> {
            val lastItem = state.lastItemOrNull()
            if (lastItem == null) {
                1
            } else {
                (lastItem.id / state.config.pageSize)+1
            }
        }
}
            delay(2000L)
            val beers = beerApi.getBeers(
                page = loadKey,
                pageCount = state.config.pageSize
            )
            beerDb.withTransaction {
                if (loadType == LoadType.REFRESH){
                 beerDb.dao.clearAll()
                }
                val beerEntities = beers.map {it.toBeerEntity()}
                beerDb.dao.upsertAll(beerEntities)
            }
            MediatorResult.Success (
                endOfPaginationReached = beers.isEmpty()
                    )
        }
        catch (e:IOException){
          MediatorResult.Error(e)
        }
        //galiba retrofit
        catch (e:retrofit2.HttpException) {
            MediatorResult.Error(e)
        }
    }
}