# Arkane ![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/NobleDevelopment/Arkane?label=Latest&logo=GitHub&style=for-the-badge)
A robust architecture for writing Spigot plugins.

## Usage
Arkane is devided into several parts, the main one being the architecture itself. Adapters are also provided for common features.

### Components of the framework.
- **ArkanePlugin:** This is the most fundamental component, and serves as an entry point to install features. It also serves as a place to declare dependency modules for koin.

- **Feature:** This is the bread and butter of Arkane. Features are systems that should only ever be active or inactive and shouldn't change during a plugin's lifetime.

- **Component:** Components are just systems that can be repeatedly enabled and disabled, and are state aware.

### Gradle
#### Repository:
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    maven {  url = uri("https://jitpack.io")}
}
```

#### Dependency:
```kotlin
dependencies {
    implementation("com.github.NobleDevelopment:Arkane:1.0.2-B")
}
```