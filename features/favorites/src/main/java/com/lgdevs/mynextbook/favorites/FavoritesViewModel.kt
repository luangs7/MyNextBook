package com.lgdevs.mynextbook.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.GetFavoriteBooksUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteUseCase
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteBooks: GetFavoriteBooksUseCase,
    private val removeBookFromFavorite: RemoveBookFromFavoriteUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun getFavoriteItems(): Flow<ViewState<List<Book>>> = flow<ViewState<List<Book>>> {
        getFavoriteBooks()
            .collect {
                val result = when (it) {
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> ViewState.Error(it.error)
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> it.data?.let { list -> ViewState.Success(list) }
                        ?: ViewState.Empty
                }
                emit(result)
            }
    }.catch { emit(ViewState.Error(it)) }.flowOn(dispatcher)

    suspend fun removeItem(book: Book): Flow<ApiResult<Unit>> = flow {
        removeBookFromFavorite(book).collect { emit(it) }
    }.catch { emit(ApiResult.Error(it)) }.flowOn(dispatcher)
}