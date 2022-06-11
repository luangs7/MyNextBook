package com.lgdevs.mynextbook.domain.di

import com.lgdevs.mynextbook.domain.interactor.abstraction.*
import com.lgdevs.mynextbook.domain.interactor.implementation.*
import org.koin.dsl.module

val domainModule = module {
    factory<AddFavoriteBook> { AddFavoriteBookImpl(get()) }
    factory<GetFavoriteBooks> { GetFavoriteBooksImpl(get()) }
    factory<GetPreferences> { GetPreferencesImpl(get()) }
    factory<GetRandomBook> { GetRandomBookImpl(get()) }
    factory<RemoveBookFromFavorite> { RemoveBookFromFavoriteImpl(get()) }
    factory<UpdatePreferences> { UpdatePreferencesImpl(get()) }
}