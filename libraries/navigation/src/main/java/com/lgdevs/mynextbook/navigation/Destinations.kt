package com.lgdevs.mynextbook.navigation

object Destinations {
    const val Login = "login"
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


sealed class NavigationItem(var route: String, var name: String) {
    object Login : NavigationItem(Destinations.Login, "Login")
    object Welcome : NavigationItem(Destinations.Welcome, "Home")
    object Finder : NavigationItem(Destinations.Finder, "Finder")
    object Preview : NavigationItem(Destinations.Preview, "Preview")
    object Favorites : NavigationItem(Destinations.Favorites, "Favorites")
    object Details : NavigationItem(Destinations.Details, "Details")
}