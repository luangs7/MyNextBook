package com.lgdevs.mynextbook.navigation

object Destinations {
    const val Welcome = "welcome"
    const val Finder = "finder"
    const val Preview = "preview"
    const val Favorites = "favorites"
    const val Details = "favorites/details/{${DestinationArgs.BookId}}"
}

object DestinationArgs {
    const val BookId = "bookid"
}

object DestinationDeepLink {
    fun passBookId(bookId: String) = "favorites/details/$bookId"
}


sealed class NavigationItem(var route: String) {
    object Welcome : NavigationItem(Destinations.Welcome)
    object Finder : NavigationItem(Destinations.Finder)
    object Preview : NavigationItem(Destinations.Preview)
    object Favorites : NavigationItem(Destinations.Favorites)
    object Details : NavigationItem(Destinations.Details)
}