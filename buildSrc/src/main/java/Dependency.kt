import Versions.NAV_VERSION

object Versions {
    const val NAV_VERSION = "2.5.3"

}
object KTX {
    const val CORE = "androidx.core:core-ktx:1.7.21"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.4.1"
}

object AndroidX {
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0"
    const val LIFECYCLE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
}

object Navigation {
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$NAV_VERSION"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$NAV_VERSION"

}


object DaggerHilt {
    const val DAGGER_HILT = "com.google.dagger:hilt-android:2.42"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:2.42"
    const val DAGGER_HILT_VIEW_MODEL = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    const val DAGGER_HILT_ANDROIDX_COMPILER = "androidx.hilt:hilt-compiler:1.0.0"
}

object Retrofit {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val CONVERTER_JAXB = "com.squareup.retrofit2:converter-jaxb:2.9.0"
}

object OkHttp {
    const val OKHTTP = "com.squareup.okhttp3:okhttp:4.3.1"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:4.3.1"
}

object Coroutines {
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
}

object Glide{
    const val GLIDE = "com.github.bumptech.glide:glide:4.11.0"
}