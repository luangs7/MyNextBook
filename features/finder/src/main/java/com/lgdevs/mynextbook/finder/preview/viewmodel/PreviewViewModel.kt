package com.lgdevs.mynextbook.finder.preview.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.AddFavoriteBookUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetPreferencesUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetRandomBookUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteUseCase
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class PreviewViewModel(
    private val getPreferences: GetPreferencesUseCase,
    private val getRandomBook: GetRandomBookUseCase,
    private val addFavoriteBook: AddFavoriteBookUseCase,
    private val removeBookFromFavorite: RemoveBookFromFavoriteUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val itemFavoriteSharedFlow: MutableSharedFlow<Book> = MutableSharedFlow(replay = 1)
    private val itemRandomSharedFlow: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    val bookFavoriteFlow = itemFavoriteSharedFlow.flatMapLatest {
        handleBook(it).catch { throwable -> emit(ViewState.Error(throwable)) }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val randomBookFlow = itemRandomSharedFlow.flatMapLatest {
        getRandomBook().catch { emit(ViewState.Error(it)) }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)


    fun randomBook() = itemRandomSharedFlow.tryEmit(Unit)

    private fun getRandomBook(): Flow<ViewState<Book>> = flow {
        getPreferences().transform { preferences ->
            getRandomBook(preferences).collect { emit(it) }
        }.collect {
            this.emit(afterGetRandomBook(it))
        }
    }.flowOn(dispatcher)

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

    private fun handleBook(item: Book) = flow {
        if (item.isFavorited) {
            removeBookFromFavorite(item).collect {
                emit(afterRemoveFavoriteBook(it))
            }
        } else {
            addFavoriteBook(item).collect {
                emit(afterAddFavoriteBook(it))
            }
        }
    }

    private fun afterAddFavoriteBook(result: ApiResult<Unit>): ViewState<Unit> {
        return when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> ViewState.Success(Unit)
        }
    }

    private fun afterRemoveFavoriteBook(result: ApiResult<Unit>): ViewState<Unit> {
        return when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> ViewState.Success(Unit)
        }
    }
}