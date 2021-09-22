repositories {
    maven { url = uri("https://jitpack.io")}
    maven { url = uri("https://repo.codemc.org/repository/maven-public/")}
    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
}
dependencies {
    api(project(":architecture"))
    api("dev.jorel.CommandAPI:commandapi-shade:6.3.1")
    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")
}