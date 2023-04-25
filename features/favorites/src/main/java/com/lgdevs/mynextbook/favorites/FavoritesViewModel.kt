package com.lgdevs.mynextbook.favorites

import androidx.lifecycle.ViewModel
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.dispatcher.CoroutineDispatcherProvider
import com.lgdevs.mynextbook.domain.interactor.implementation.GetFavoriteBooksUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteUseCase
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.extensions.collectIfSuccess
import kotlinx.coroutines.flow.*

class FavoritesViewModel(
    private val getFavoriteBooks: GetFavoriteBooksUseCase,
    private val removeBookFromFavorite: RemoveBookFromFavoriteUseCase,
    private val getCurrentUser: GetUserUseCase,
    private val dispatcher: CoroutineDispatcherProvider,
) : ViewModel() {

    fun getFavoriteItems(): Flow<ViewState<List<Book>>> = flow<ViewState<List<Book>>> {
        getCurrentUser().collectIfSuccess { user ->
            getFavoriteBooks(user.uuid)
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
        }
    }.catch { emit(ViewState.Error(it)) }.flowOn(dispatcher.invoke())

    fun removeItem(book: Book): Flow<ApiResult<Unit>> = flow {
        removeBookFromFavorite(book).collect { emit(it) }
    }.catch { emit(ApiResult.Error(it)) }.flowOn(dispatcher.invoke())
}
