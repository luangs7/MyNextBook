
object ConfigData {
    const val compileSdkVersion = 32
    const val buildToolsVersion = "32.0.0"
    const val minSdkVersion = 24
    const val targetSdkVersion = 32
    const val versionCode = 1
    const val versionName = "1.0"
    const val minifyEnable = true
    const val applicationId = "com.luan.teste.git"
}

object Modules{
    const val app = ":app"
    const val common = ":core:common"
    const val local = ":data:remote"
    const val remote = ":data:local"
    const val domain = ":domain"
    const val repository = ":data:repository"
    const val designsystem = ":core:designsystem"
    const val navigation = ":library:navigation"
    const val tests = ":library:tests"
}

object Features {
    const val favorites = ":features:favorites"
    const val filter = ":features:favorites"
    const val swipe = ":features:favorites"
    const val home = ":features:favorites"
    const val finder = ":features:finder"
}