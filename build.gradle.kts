import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.5.0" apply false
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
    `maven-publish`
}

allprojects {
    group = "org.nobledev.arkane"
    version = "1.0.3-B"

    repositories {
        mavenCentral()
        maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    }

}

subprojects {
    apply(plugin="kotlin")
    apply(plugin="maven-publish")

    publishing {
        repositories {
            maven {
                credentials {
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                    url = URI("http://nexus.nobledev.org:8081/repository/maven-releases/")
                }
            }
        }
        publications {
            create<MavenPublication>(project.name) {
                artifactId = project.name.toLowerCase()
                from(components["java"])
            }
        }
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

