dependencies {
    api(libs.koin.base)
    api(libs.koin.ext)
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", libs.versions.kotlinV.get())
    compileOnly(libs.paper)
}

