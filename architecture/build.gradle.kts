dependencies {
    api(libs.koin.base)
    api(libs.koin.ext)
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", libs.versions.kotlinV.get())
    implementation(libs.paper)
}

publishing {
    repositories {

    }
    publications {
        create<MavenPublication>(project.name) {
            artifactId = project.name.toLowerCase()
            from(components["java"])
        }
    }
}