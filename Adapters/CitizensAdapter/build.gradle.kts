repositories {
    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    maven { url= uri("https://repo.citizensnpcs.co/")}
}
dependencies {
    api(project(":architecture"))
    compileOnly("net.citizensnpcs:citizens-main:2.0.28-SNAPSHOT")
    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")
}