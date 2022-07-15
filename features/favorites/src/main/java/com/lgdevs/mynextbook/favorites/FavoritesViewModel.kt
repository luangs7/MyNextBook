package com.lgdevs.mynextbook.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetFavoriteBooks
import com.lgdevs.mynextbook.domain.interactor.abstraction.RemoveBookFromFavorite
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteBooks: GetFavoriteBooks,
    private val removeBookFromFavorite: RemoveBookFromFavorite
): ViewModel() {

    fun getFavoriteItems(): Flow<ViewState<List<Book>>> = flow {
        getFavoriteBooks.execute(Unit)
            .catch { emit(ViewState.Error(it)) }
            .collect {
                val result = when(it){
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> ViewState.Error(it.error)
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> it.data?.let { list -> ViewState.Success(list) } ?: ViewState.Empty
                }
                emit(result)
            }
    }

    fun removeItem(book: Book) {
        viewModelScope.launch {
            removeBookFromFavorite.execute(book)
                .collect {
                    Log.d("RemoveBookFromFavorite::", it.toString())
                }
        }
    }
}