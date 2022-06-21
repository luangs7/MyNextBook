package com.lgdevs.mynextbook.finder.preview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetPreferences
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetRandomBook
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreviewViewModel(
    private val getPreferences: GetPreferences,
    private val getRandomBook: GetRandomBook,
    private val addFavoriteBook: AddFavoriteBook
) : ViewModel() {

    private val _addFavoriteBookState = MutableStateFlow<ViewState<Unit?>>(ViewState.Loading)
    val addFavoriteBookState: StateFlow<ViewState<Unit?>>
        get() = _addFavoriteBookState

    private val _snackbarState = MutableStateFlow(String())
    val snackbarState: StateFlow<String>
        get() = _snackbarState

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

    fun addFavoriteBook(book: Book) {
        viewModelScope.launch {
            addFavoriteBook.execute(book).collect {
                val result = when (it) {
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> ViewState.Error(it.error)
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> ViewState.Success(it.data)
                }
                _addFavoriteBookState.value = result
            }
        }
    }

    fun showSnackbar(message: String){
        _snackbarState.value = message
    }
}