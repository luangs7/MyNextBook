package com.lgdevs.mynextbook.finder.preview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.dispatcher.CoroutineDispatcherProvider
import com.lgdevs.mynextbook.domain.interactor.implementation.AddFavoriteBookUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetPreferencesUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetRandomBookUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteUseCase
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.extensions.collectIfSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class PreviewViewModel(
    private val getPreferences: GetPreferencesUseCase,
    private val getRandomBook: GetRandomBookUseCase,
    private val addFavoriteBook: AddFavoriteBookUseCase,
    private val removeBookFromFavorite: RemoveBookFromFavoriteUseCase,
    private val getCurrentUser: GetUserUseCase,
    private val dispatcher: CoroutineDispatcherProvider,
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
        getCurrentUser().collectIfSuccess { user ->
            getPreferences(user.uuid).transform { preferences ->
                getRandomBook(preferences).collect { emit(it) }
            }.collect {
                this.emit(afterGetRandomBook(it))
            }
        }
    }.flowOn(dispatcher.invoke())

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
        getCurrentUser().collectIfSuccess { response ->
            if (item.isFavorited) {
                removeBookFromFavorite(item).collect {
                    emit(afterRemoveFavoriteBook(it))
                }
            } else {
                addFavoriteBook(item, response).collect {
                    emit(afterAddFavoriteBook(it))
                }
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
