package com.lgdevs.mynextbook.finder.preview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetPreferences
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetRandomBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.RemoveBookFromFavorite
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class PreviewViewModel(
    private val getPreferences: GetPreferences,
    private val getRandomBook: GetRandomBook,
    private val addFavoriteBook: AddFavoriteBook,
    private val removeBookFromFavorite: RemoveBookFromFavorite
) : ViewModel() {

    private val itemFavoriteSharedFlow: MutableSharedFlow<Book> = MutableSharedFlow(replay = 1)

    val bookFavoriteFlow = itemFavoriteSharedFlow.flatMapMerge {
        if(it.isFavorited){
            addFavoriteBook.execute(it).flatMapMerge { apiResult ->
                afterAddFavoriteBook(apiResult)
            }
        } else {
            removeBookFromFavorite.execute(it).flatMapMerge { apiResult ->
                afterRemoveFavoriteBook(apiResult)
            }
        }
    }

    fun getRandomBook(): Flow<ViewState<Book>> = flow {
        getPreferences.execute(Unit).transform { preferences ->
            getRandomBook.execute(preferences).collect { emit(it) }
        }.collect {
            this.emit(afterGetRandomBook(it))
        }
    }

    private fun afterGetRandomBook(apiResult: ApiResult<Book>): ViewState<Book> {
        return when (apiResult) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(apiResult.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> apiResult.data?.let { book -> ViewState.Success(book) }
                ?: ViewState.Empty
        }
    }

    fun itemFavoriteBook(book: Book) = itemFavoriteSharedFlow.tryEmit(book)

    private fun afterAddFavoriteBook(result: ApiResult<Unit>) = flow {
        val viewResult = when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> ViewState.Success(result.data)
        }
        emit(viewResult)
    }

    private fun afterRemoveFavoriteBook(result: ApiResult<Unit>) = flow {
        val viewResult = when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> ViewState.Success(result.data)
        }
        emit(viewResult)
    }
}