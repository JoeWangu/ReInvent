package com.saddict.reinvent.products.data.manager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saddict.reinvent.products.data.local.ReInventDatabase
import com.saddict.reinvent.products.data.remote.remository.ApiRepository
import com.saddict.reinvent.products.model.local.ProductEntity
import com.saddict.reinvent.utils.Constants.INITIAL_PAGE
import com.saddict.reinvent.utils.DataMapper.Companion.mapToEntity
import javax.inject.Inject

class CustomPagingSource @Inject constructor(
    private val productApi: ApiRepository,
    private val appDatabase: ReInventDatabase
): PagingSource<Int, ProductEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = productApi.getProducts(page).results
            val entities = response.map { it.mapToEntity() }
            appDatabase.reInventDao().upsertAllProducts(entities)
            val dataLoaded = appDatabase.reInventDao().getAllPaged()
            LoadResult.Page(
                data = dataLoaded,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}