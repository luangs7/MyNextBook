package com.lgdevs.mynextbook.domain.di

import com.lgdevs.mynextbook.domain.interactor.implementation.*
import com.lgdevs.mynextbook.domain.repositories.BookRepository
import com.lgdevs.mynextbook.domain.repositories.PreferencesRepository
import com.lgdevs.mynextbook.domain.repositories.UserDataRepository
import org.koin.dsl.module

val domainModule = module {
    factory { GetFavoriteBooksUseCase(get<BookRepository>()::getFavorites) }
    factory { GetPreferencesUseCase(get<PreferencesRepository>()::loadPreferences) }
    factory { GetRandomBookUseCase(get<BookRepository>()::getRandomBook) }
    factory { RemoveBookFromFavoriteUseCase(get<BookRepository>()::removeFavorite) }
    factory { UpdatePreferencesUseCase(get<PreferencesRepository>()::updatePreferences) }
    factory { GetEmailLoginUseCase(get<UserDataRepository>()::loadPreferences) }
    factory { DoLoginUseCase(get<UserDataRepository>()::doLogin) }
    factory { DoLoginWithTokenUseCase(get<UserDataRepository>()::doLoginWithToken) }
    factory { GetUserUseCase(get<UserDataRepository>()::getCurrentUser) }
    factory { SetEmailLoginUseCase(get<UserDataRepository>()::updatePreferences) }

    factory {
        AddFavoriteBookUseCase { book, currentUser ->
            favoriteBookFactory(book, currentUser, get<BookRepository>())
        }
    }
}
