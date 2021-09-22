pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":architecture",
    ":Adapters:CommandAPIAdapter",
    ":Adapters:CitizensAdapter"
)
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlinV", "1.5.1")
            version("adventure", "4.9.1")
            version("koin", "3.0.2")
            version("mc", "1.16.5-R0.1-SNAPSHOT")
            alias("adventure-api").to("net.kyori", "adventure-api").versionRef("adventure")
            alias("adventure-platform").to("net.kyori", "adventure-platform-bukkit").version("4.0.0")
            alias("adventure-minimessage").to("net.kyori", "adventure-text-minimessage").version("4.1.0")
            alias("adventure-serializer-legacy").to("net.kyori", "adventure-text-serializer-legacy").versionRef("adventure")
            alias("adventure-serializer-gson").to("net.kyori", "adventure-text-serializer-gson").versionRef("adventure")
            alias("adventure-serializer-plain").to("net.kyori", "adventure-text-serializer-plain").versionRef("adventure")
            alias("koin-base").to("io.insert-koin", "koin-core").versionRef("koin")
            alias("koin-ext").to("io.insert-koin", "koin-core-ext").versionRef("koin")
            alias("paper").to("com.destroystokyo.paper", "paper-api").versionRef("mc")
        }
    }
}