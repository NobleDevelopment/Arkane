import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0" apply false
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
    `maven-publish`
}

allprojects {
    group = "org.nobledev.arkane"
    version = "1.0.2-B"

    repositories {
        mavenCentral()
        maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    }

}

subprojects {
    apply(plugin="kotlin")
    apply(plugin="maven-publish")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

